package org.example;

import dataFile.PayLoad;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import reUsableMethods.commonMethod;

import java.util.HashMap;

public class RestAPIBasics {

    HashMap<String,String> Place_Id=new HashMap<String,String>();

    @Test
    public void createRecord()
    {
        //given - All Inputs
        // When - Submit the API - resource and http methods
        // Then - validate the response

        RestAssured.baseURI="https://rahulshettyacademy.com";
        String Responce= given()
                .log().all()
                .queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body(PayLoad.AddPlace())
                .when().post("maps/api/place/add/json")
                .then()
                //.log().all() //Log is not needed as now we are saving the responce in string
                .assertThat().statusCode(200)
                .body("scope",equalTo("APP"))
                .header("Server","Apache/2.4.52 (Ubuntu)")
                .extract().response().asString();
        System.out.println("Responce: "+Responce);

        //    JsonPath js = new JsonPath(Responce);
        //    String placeID=js.getString("place_id");
        //    String address=js.getString("address");
        // At place of above three lines we can create a common methods and call it every time
        //    String placeID=commonMethod.getJson(Responce).getString("placeID");
        //    String address=commonMethod.getJson(Responce).getString("address");
//or like below
        JsonPath js =commonMethod.getJson(Responce);
        String placeID=js.getString("place_id");
        String address=js.getString("address");

        Place_Id.put("IdOne",placeID);
        Place_Id.put("address",address);
        System.out.println("placeID: "+placeID);

    }

    //Add place -> Update place with new address -> Get place to validate if new address is present.
    @Test(priority=2)
    public void updateAddress()
    {
        System.out.println(Place_Id.get("IdOne"));
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String updateAddressResponce=given().log().all().queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body(PayLoad.UpdatePlace().replace("placeID",Place_Id.get("IdOne")))
                .when().put("maps/api/place/update/json")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("msg",equalTo("Address successfully updated"))
                .extract().response().asString();
        // when we are updating the records, only sucess message will generate as part of response
//        JsonPath js1 = new JsonPath(updateAddressResponce);
//
//        String placeID=js1.getString("place_id");
//        System.out.println("*******placeID :- "+placeID);
//        System.out.println("*******Responce of JS:- "+js1.toString());
//        String newAddress=js1.getString("address");
//        Place_Id.put("newAddress",newAddress);
//        System.out.println("*******Responce :- "+newAddress);
//        System.out.println("*******Responce :- "+Place_Id.get("newAddress"));
    }

    @Test(priority=3)
    public void GetPlace()
    {
        String expectedNewAddress="101 Summer walk USA";
        String getPlace=given().log().all().queryParam("key","qaclick123")
                .queryParam("place_id", Place_Id.get("IdOne"))
                .when().get("maps/api/place/get/json")
                .then().log().all()
                .assertThat().statusCode(200)
                //verification of New address from RestAPI method
                .body("address",equalTo("101 Summer walk USA")).extract().response().asString();
        //Verification of New address from TestNG Assert
        String actualAddress=commonMethod.getJson(getPlace).getString("address");
        //System.out.println("*******actualAddress :- "+actualAddress);
        Place_Id.put("actualAddress",actualAddress);
        Assert.assertEquals(expectedNewAddress,Place_Id.get("actualAddress"));


    }
    @Test(enabled = false)
    public void GetDetails2() {
// Specify the base URL to the RESTful web service
        RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books";
        // Get the RequestSpecification of the request to be sent to the server.
        RequestSpecification httpRequest = RestAssured.given();
        // specify the method type (GET) and the parameters if any.
        //In this case the request does not take any parameters
        Response response = httpRequest.request(Method.GET, "");
        // Print the status and message body of the response received from the server
        // Basic assertions instead of prints
        System.out.println("Status received => " + response.getStatusLine());
        System.out.println("Response=>" + response.prettyPrint());
        org.testng.Assert.assertEquals(response.getStatusCode(), 200, "Expected HTTP status 200");
    }
    @Test
    public void Request1() {
        RequestSpecification request = given();
        // Setting Base URI
        request.baseUri("https://restful-booker.herokuapp.com");
        // Setting Base Path
        request.basePath("/booking");
    //    request.contentType();  // in case if we want to add content type
     //   request.queryParam();  // in case we want to add query parameter
        Response response = request.get();
        System.out.println(response.asString());
    }

}
