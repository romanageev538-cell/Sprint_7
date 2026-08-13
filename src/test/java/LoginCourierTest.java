import io.restassured.response.Response;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import model.CourierModel;


import static data.CourierData.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.CourierSteps.loginCourierRaw;


public class LoginCourierTest extends BaseApiTest {
    @Test
    @DisplayName("Successful courier login")
    @Description("Verify that a courier can log in with valid credentials, receiving status 200 and a non-null id")
    public void testLoginCourierSuccess() {
        // Используем данные существующего курьера (создан в @Before)
        CourierModel loginData = new CourierModel(LOGIN, PASSWORD,null);

        Response response = loginCourierRaw(loginData);
        response.then()
                .log().all()
                .statusCode(SC_OK)
                .body("id", notNullValue()); // id должен быть числом
    }

    @Test
    @DisplayName("Login without login field returns error")
    @Description("Verify that sending a login request without the 'login' field returns status 400 with an appropriate error message")
    public void testLoginCourierMissingLogin() {
        CourierModel loginData = new CourierModel("", PASSWORD,null);

        Response response = loginCourierRaw(loginData);
        response.then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login without password field returns error")
    @Description("Verify that sending a login request without the 'password' field returns status 400 with an appropriate error message")
    public void testLoginCourierMissingPassword() {
        CourierModel loginData = new CourierModel(LOGIN, "",null);

        Response response = loginCourierRaw(loginData);
        response.then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login with wrong login returns error")
    @Description("Verify that attempting to log in with an incorrect login returns status 404 with the message: Учетная запись не найдена")
    public void testLoginCourierWrongLogin() {

        CourierModel loginData = new CourierModel(WRONG_LOGIN, PASSWORD,null);

        Response response = loginCourierRaw(loginData);
        response.then()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login with wrong password returns error")
    @Description("Verify that attempting to log in with an incorrect login returns status 404 with the message: Учетная запись не найдена")
    public void testLoginCourierWrongPassword() {

        CourierModel loginData = new CourierModel(LOGIN, WRONG_PASSWORD,null);

        Response response = loginCourierRaw(loginData);
        response.then()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login with non-existent user returns error")
    @Description("Verify that attempting to log in with credentials that do not exist in the system returns status 404 with the message:Учетная запись не найдена")
    public void testLoginCourierNonExistent() {

        CourierModel loginData = new CourierModel(FAKE_LOGIN, FAKE_PASSWORD,null);

        Response response = loginCourierRaw(loginData);
        response.then()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}

