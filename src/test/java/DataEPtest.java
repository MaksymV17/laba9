package org.example;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MainTest {

    private static final String API_BASE_URL = "http://localhost:8080";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = API_BASE_URL;
    }

    @Test
    public void testApiCallsAndExcelConversion() {
        String[] endpoints = {
                "/countries",
                "/cities",
         };

        for (String endpoint : endpoints) {
            Response response = given().when().get(endpoint);
            response.then().statusCode(200);

            String jsonResponse = response.body().asString();
            assertNotNull(jsonResponse);

            try {
                Excel.Convert(jsonResponse, "sheet", "key", "sheetName");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
