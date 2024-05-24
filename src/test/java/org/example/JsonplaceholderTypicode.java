package org.example;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class JsonplaceholderTypicode {

    @BeforeMethod
    public void url() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void getTest() {
        given().log().all()
                .when().get("/posts")
                .then().log().all().statusCode(200);
    }

    @Test
    public void getFirstItemTest() {
        Response response = given().log().all()
                .when().get("/posts/1")
                .then().log().all().extract().response();
        assertEquals(response.statusCode(), 200);
        assertEquals(response.getHeader("Server"), "cloudflare");
        assertEquals(response.jsonPath().getString("title"), "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
    }

    @Test
    public void postTest() {
        File file = new File("src/test/java/resources/body.json");
        Response response = given().log().all().contentType(ContentType.JSON).body(file)
                .when().put("/posts/1")
                .then().log().all().extract().response();
        assertEquals(response.statusCode(), 200);
    }

    @Test
    public void putTest() {
        Map<String, String> bodyInfo = new HashMap<>();
        bodyInfo.put("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        bodyInfo.put("text", "Quid quid latine dictum sit, altum viditur");
        Response response = given().log().all().contentType(ContentType.JSON).body(bodyInfo)
                .when().put("/posts/1")
                .then().log().all().extract().response();
        assertEquals(response.statusCode(), 200);
    }

    @Test
    public void deleteTest() {
        Response response = given().log().all()
                .when().delete("/posts/1")
                .then().log().all().extract().response();
        assertEquals(response.statusCode(), 200);
    }

    @Test
    public void getJsonSchemaValidationTest() {
        given().log().all()
                .when().get("/posts/7")
                .then().log().all().statusCode(200)
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("json/jsonschema.json"));
    }

}
