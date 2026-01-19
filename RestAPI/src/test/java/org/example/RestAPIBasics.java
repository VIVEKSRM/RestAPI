package org.example;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class RestAPIBasics {

    @Test(enabled = false)
    public void createRecord()
    {
        //given - All Inputs
        // When - Submit the API - resource and http methods
        // Then - validate the response

        RestAssured.baseURI="http://rahulshettyacademy.com";
        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body("{\r\n" +
                        "  \"location\": {\r\n" +
                        "    \"lat\": -38.383494,\r\n" +
                        "    \"lng\": 33.427362\r\n" +
                        "  },\n" +
                        "  \"accuracy\": 50,\r\n" +
                        "  \"name\": \"Frontline house\",\r\n" +
                        "  \"phone_number\": \"(+91) 983 893 3937\",\r\n" +
                        "  \"address\": \"29, side layout, cohen 09\",\n" +
                        "  \"types\": [\n" +
                        "    \"shoe park\",\n" +
                        "    \"shop\"\n" +
                        "  ],\n" +
                        "  \"website\": \"https://rahulshettyacademy.com\",\n" +
                        "  \"language\": \"French-IN\"\n" +
                        "}")
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200);


    }

    @Test(enabled = true)
    public void GetDetails2() {
// Specify the base URL to the RESTful web service
        RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books";
        // Get the RequestSpecification of the request to be sent to the server.
        RequestSpecification httpRequest = RestAssured.given();
        // specify the method type (GET) and the parameters if any.
        //In this case the request does not take any parameters
        Response response = httpRequest.request(Method.GET, "");
        // Print the status and message body of the response received from the server
        System.out.println("Status received => " + response.getStatusLine());
        System.out.println("Response=>" + response.prettyPrint());
    }
}
