import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;



import io.restassured.response.Response;
import jdk.jfr.Description;
import model.CourierModel;
import org.junit.Test;

import static data.CourierData.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.CourierSteps.createCourier;
import static steps.CourierSteps.loginCourier;

public class CreateCourierTest extends BaseApiTest {

    // Создание курьера
    @Test
    @DisplayName("Create courier successfully") // имя теста
    @Description("Verify that a new courier can be created with valid credentials and the response returns 201 and ok:true") // описание теста
    public void testCreateCourierSuccess() {
        CourierModel courier = new CourierModel(NEW_LOGIN, PASSWORD, FIRSTNAME);
        Response createResponse = createCourier(courier);
        createdCourierId = loginCourier(courier);//Логинимся и получаем id курьера (токен для удаления) ДО проверки ответа

        createResponse.then()
                .log().all()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));



    }
    // Создание курьера с повторяющимся логином
    @Test
    @DisplayName("Cannot create courier with duplicate login") // имя теста
    @Description("Verify that attempting to create a courier with an already existing login returns 409 with appropriate error message: Этот логин уже используется") // описание теста
    @Issue("BUG-1")
    public void testCannotCreateDuplicateCourier() {

        CourierModel duplicateCourier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(duplicateCourier)
                .then()
                .log().all()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }
    //передаЧА в ручку НЕ всеХ обязательныХ полЕЙ
    @Test
    @DisplayName("Cannot create courier without login") // имя теста
    @Description("Verify that creating a courier with missing login field returns 400 with error message:Недостаточно данных для создания учетной записи") // описание теста
    public void testCreateCourierNotLogin() {
        CourierModel courier = new CourierModel(null, PASSWORD, FIRSTNAME);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Cannot create courier without password") // имя теста
    @Description("Verify that creating a courier with missing login field returns 400 with error message:Недостаточно данных для создания учетной записи") // описание теста
    public void testCreateCourierNotPassword() {
        CourierModel courier = new CourierModel(LOGIN, null, FIRSTNAME);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}
