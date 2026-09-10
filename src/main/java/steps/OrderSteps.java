package steps;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;

import static data.OrderData.ORDER_CANCEL_PATH;
import static data.OrderData.ORDER_CREATE_PATH;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создание заказа")
    public static Response createOrder(OrderModel order) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(ORDER_CREATE_PATH)
                .then()
                .extract().response();
    }

    @Step("Отмена заказа по треку")
    public static Response cancelOrder(int track) {
        String body = "{\"track\": " + track + "}";
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put(ORDER_CANCEL_PATH)
                .then()
                .extract().response();
    }
}