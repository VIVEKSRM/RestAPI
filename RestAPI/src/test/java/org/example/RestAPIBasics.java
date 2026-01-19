package org.example;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class RestAPIBasics extends TestBase {

    @Test(enabled = false, groups = {"integration"})
    public void createRecord()
    {
        RestAssured.baseURI = System.getProperty("base.uri.rahulshetty", config.get("base.uri.rahulshetty"));
        String apiKey = System.getProperty("api.key", config.get("api.key"));
        String payload = TestUtils.resourceAsString("payloads/add_place.json");

        given().log().all().queryParam("key", apiKey).header("Content-Type","application/json")
                .body(payload)
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200);

    }

    @Test(groups = {"smoke"})
    public void getDetails() {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = System.getProperty("base.uri.demoqa", config.get("base.uri.demoqa"));
        // Get the RequestSpecification of the request to be sent to the server.
        RequestSpecification httpRequest = RestAssured.given();
        // specify the method type (GET) and the parameters if any.
        //In this case the request does not take any parameters
        Response response = httpRequest.request(Method.GET, "");
        // Basic assertions instead of prints
        System.out.println("Status received => " + response.getStatusLine());
        System.out.println("Response=>" + response.prettyPrint());
        org.testng.Assert.assertEquals(response.getStatusCode(), 200, "Expected HTTP status 200");
    }
}
