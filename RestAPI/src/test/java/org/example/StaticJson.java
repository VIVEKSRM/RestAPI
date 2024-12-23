package org.example;

import dataFile.PayLoad;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StaticJson {

    @Test
    public void staticjsontest() throws IOException {
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String Responce= given()
                .log().all()
                .queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body(new String(Files.readAllBytes(Paths.get("C:\\Users\\Vivek Ranjan\\OneDrive\\Documents\\addPlace.json"))))
                .when().post("maps/api/place/add/json")
                .then()
                //.log().all() //Log is not needed as now we are saving the responce in string
                .assertThat().statusCode(200)
                .body("scope",equalTo("APP"))
                .header("Server","Apache/2.4.52 (Ubuntu)")
                .extract().response().asString();
        System.out.println("Responce: "+Responce);
    }
}
