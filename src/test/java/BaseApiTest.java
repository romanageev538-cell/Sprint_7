import io.restassured.RestAssured;
import model.CourierModel;
import org.junit.After;
import org.junit.Before;

import static data.CourierData.BASE_URL;
import static data.CourierData.LOGIN;
import static data.CourierData.PASSWORD;
import static data.CourierData.FIRSTNAME;
import static steps.CourierSteps.createCourier;
import static steps.CourierSteps.loginCourier;
import static steps.CourierSteps.deleteCourier;

public class BaseApiTest {

    protected int createdCourierId;
    protected int existingCourierId;

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;

        CourierModel existingCourier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(existingCourier);
        existingCourierId = loginCourier(existingCourier);
    }

    @After
    public void tearDown() {
        if (existingCourierId > 0) {
            deleteCourier(existingCourierId);
        }

        if (createdCourierId > 0) {
            deleteCourier(createdCourierId);
        }
    }
}