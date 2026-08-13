package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierModel;

import static data.CourierData.*;
import static io.restassured.RestAssured.given;


public class CourierSteps {

    @Step("Запрос на создание курьера")
    public static Response createCourier (CourierModel courier){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER_CREATE_PATH )
                .then()
                .extract().response();
    }
    // Логин – возвращает id курьера
    @Step("Логин курьера и получение id")
    public static int loginCourier(CourierModel courier) {
        // Для логина нужны только логин и пароль
        CourierModel loginData = new CourierModel(courier.getLogin(), courier.getPassword(),null);
        Response response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(loginData)
                .when()
                .post(COURIER_LOGIN_PATH)
                .then()
                .statusCode(200) // ожидаем успешный логин
                .extract().response();
        return response.jsonPath().getInt("id");
    }
    // Удаление по id
    @Step("Удаление курьера по id")
    public static Response deleteCourier(int id) {
        return given()
                .log().all()
                .when()
                .delete(COURIER_DELETE_PATH.replace(":id", String.valueOf(id)))
                .then()
                .extract().response();
    }
    @Step("Запрос на логин курьера (без проверки статуса)")
    public static Response loginCourierRaw(CourierModel loginData) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(loginData)
                .when()
                .post(COURIER_LOGIN_PATH)
                .then()
                .extract().response();
    }
}
