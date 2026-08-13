import io.restassured.RestAssured;

import model.CourierModel;
import org.junit.After;
import org.junit.Before;


import static data.CourierData.*;
import static steps.CourierSteps.*;

public class BaseApiTest {

    protected int createdCourierId;
    protected int existingCourierId;

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        CourierModel existingCourier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);// создаем курьера, для теста с дубликатом
        createCourier(existingCourier);
        existingCourierId = loginCourier(existingCourier);
    }

    @After
    public void tearDown() {
        // Удаляем курьера, созданного в @Before
        if (existingCourierId > 0) {
            deleteCourier(existingCourierId);

            // Удаляем курьера, созданного в тесте (если есть)
            if (createdCourierId > 0) {
                deleteCourier(createdCourierId);

            }


        }

    }

}