import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.OrderModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static data.OrderData.BASE_URL;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static steps.OrderSteps.createOrder;
import static steps.OrderSteps.cancelOrder;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final List<String> colors;
    private int track; // сохраняем трек для отмены

    // Конструктор для параметров
    public CreateOrderTest(List<String> colors) {
        this.colors = colors;
    }


    // Параметры: список цветов (null, пустой список, один цвет, два цвета)
    @Parameterized.Parameters(name = "Colors: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {null},                          // без цвета (поле color не передаётся)
                {Arrays.asList("BLACK")},        // только BLACK
                {Arrays.asList("GREY")},         // только GREY
                {Arrays.asList("BLACK", "GREY")},// оба цвета
                {Arrays.asList()}                // пустой список (поле color передаётся, но массив пуст)
        });
    }
    @Before
    public void setUp() {
        // Устанавливаем базовый URI для всех запросов
        RestAssured.baseURI = BASE_URL;

    }

    @After
    public void tearDown() {
        if (track > 0) {

            cancelOrder(track);
        }
    }



    @Test
    @DisplayName("Create order with various color combinations")
    @Description("Verify that order can be created with different color options: null, BLACK, GREY, both, empty list. The response should contain a non-null track number.")
    public void testCreateOrderWithColors() {
        // Создаём заказ с фиксированными данными и заданными цветами
        OrderModel order = new OrderModel(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                4,
                "+7 800 355 35 35",
                5,
                "2026-06-06",
                "Saske, come back to Konoha",
                colors
        );

        // Отправляем запрос
        Response response = createOrder(order);

        // Проверяем статус 201 и наличие track
        response.then()
                .log().all()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());

        // Сохраняем track для отмены в @After
        track = response.jsonPath().getInt("track");
        assertThat(track, notNullValue());
    }
}
