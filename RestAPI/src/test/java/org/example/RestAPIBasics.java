package org.example;

import dataFile.PayLoad;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class RestAPIBasics {

    @Test(enabled = true)
    public void createRecord()
    {
        //given - All Inputs
        // When - Submit the API - resource and http methods
        // Then - validate the response

        RestAssured.baseURI="https://rahulshettyacademy.com";
        given().log().all().queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body(PayLoad.AddPlace())
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200)
                .body("scope",equalTo("APP"))
                .header("Server","Apache/2.4.52 (Ubuntu)");

    }

    //Add place -> Update place with new address -> Get place to validate if new address is present.


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
