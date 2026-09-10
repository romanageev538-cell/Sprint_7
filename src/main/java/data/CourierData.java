package data;

import com.github.javafaker.Faker;


public class CourierData {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    static Faker user = new Faker();

    public static final String LOGIN = "courierLogin_" + System.currentTimeMillis();//используется для дубликата
    public static final String NEW_LOGIN = "newCourier_" + System.currentTimeMillis();//используется для создания нового курьера
    public static final String PASSWORD = user.regexify("[0-9]{4}");
    public static final String FIRSTNAME = user.regexify("[0-9]{4}");
    public static final String COURIER_CREATE_PATH = "/api/v1/courier";
    public static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";
    public static final String COURIER_DELETE_PATH = "/api/v1/courier/:id";
    public static final String WRONG_LOGIN = "wrong_login_" + System.currentTimeMillis();
    public static final String WRONG_PASSWORD = "wrong_password"+ System.currentTimeMillis();
    public static final String FAKE_LOGIN = "fakeLogin_" + System.currentTimeMillis();
    public static final String FAKE_PASSWORD = user.regexify("[0-9]{5}");

}

