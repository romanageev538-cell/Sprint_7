import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static data.OrderData.BASE_URL;
import static data.OrderData.ORDERS_LIST_PATH;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Get orders list")
    @Description("Verify that GET /api/v1/orders returns status 200 and the response body contains a non-null 'orders' array (List)")
    public void testGetOrdersReturnsList() {
        given()
                .log().all()
                .when()
                .get(ORDERS_LIST_PATH)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("orders", notNullValue())
                .body("orders", instanceOf(List.class));
    }
}