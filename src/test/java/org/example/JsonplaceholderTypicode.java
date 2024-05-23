package org.example;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class JsonplaceholderTypicode {

    private static final Logger LOGGER = LogManager.getLogger(JsonplaceholderTypicode.class);

    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void getTest() {
        given().log().all()
                .when().get("/posts/1")
                .then().log().all().statusCode(200);
    }

    @Test
    public void getFirstItemTest() {
        Response response = given().log().all()
                .when().get("/posts/1")
                .then().log().all().extract().response();
        LOGGER.info(response.statusCode());
        assertEquals(response.statusCode(), 200);
        LOGGER.info(response.getHeader("Server"));
        assertEquals(response.getHeader("Server"), "cloudflare");
        LOGGER.info(response.jsonPath().getString("body"));
        assertEquals(response.jsonPath().getString("title"), "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");

    }

    @Test
    public void postTest() {
        File file = new File("src/test/java/resources/body.json");
        given().log().all().contentType(ContentType.JSON).body(file)
                .when().put("/posts/1")
                .then().log().all().statusCode(200);
    }

    @Test
    public void putTest() {
        Map<String,String> bodyInfo = new HashMap<String, String>();
        bodyInfo.put("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        bodyInfo.put("text", "Quid quid latine dictum sit, altum viditur");
        given().log().all().contentType(ContentType.JSON).body(bodyInfo)
                .when().put("/posts/1")
                .then().log().all().statusCode(200);
    }

    @Test
    public void deleteTest() {
        given().log().all()
                .when().delete("/posts/1")
                .then().log().all().statusCode(200);
    }

}
