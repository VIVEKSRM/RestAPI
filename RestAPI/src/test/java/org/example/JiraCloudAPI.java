package org.example;

import dataFile.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import reUsableMethods.commonMethod;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class JiraCloudAPI {

    HashMap<String,String> Place_Id=new HashMap<String,String>();


    @Test(enabled = true,priority=1)
    public void createBug()
    {
        //given - All Inputs
        // When - Submit the API - resource and http methods
        // Then - validate the response

        RestAssured.baseURI="https://vivekranjan-learning.atlassian.net";
        String Responce= given()
                .log().all()
               // .queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .header("Authorization","Basic dml2ZWsucmFuamFuLnNybUBnbWFpbC5jb206QVRBVFQzeEZmR0YwOVkzam1DOEtpT3FOcW9aZGd1emhlMVlFUlRSS3J5VUtsY0hoN0RNd1FEak9fMXBraS1tMTNQWUVGRWJWa09Pd3MzN3ZBQnFpaGJGcTRlX2RkRk5ZaFUzTEM2ekcwY2NGajE1bTI1OUNlUFdPUEVvZGZWRDJUbHJlanVwdFg1SzVHR3hEZ2NUZUN3REFMTFBGVEY2YVhBbGlWVzFac2dHcU1TYWZCRHhvcllnPTcxODRFRTgz")
                .body(PayLoad.createBug("SCRUM","Test Button Not Working", "Bug"))
                .when().post("/rest/api/3/issue")
                .then()
                //.log().all() //Log is not needed as now we are saving the responce in string
                .assertThat().statusCode(201)
              //  .body("key",equalTo("SCRUM-4"))
                .header("Server","AtlassianEdge")
                .extract().response().asString();
        System.out.println("Responce: "+Responce);
        JsonPath js = commonMethod.getJson(Responce);
        String bugID=js.getString("id");
        Place_Id.put("bugID1",bugID);
        System.out.println("bugID1: "+bugID);

    }

    @Test(enabled = true,priority=2)
    public void addAttachment()
    {
        //given - All Inputs
        // When - Submit the API - resource and http methods
        // Then - validate the response

        RestAssured.baseURI="https://vivekranjan-learning.atlassian.net";
        String Responce= given()
                .log().all()
                // .queryParam("key","qaclick123")
                .header("X-Atlassian-Token","no-check")
                .pathParams("Key",Place_Id.get("id"))
                .header("Authorization","Basic dml2ZWsucmFuamFuLnNybUBnbWFpbC5jb206QVRBVFQzeEZmR0YwOVkzam1DOEtpT3FOcW9aZGd1emhlMVlFUlRSS3J5VUtsY0hoN0RNd1FEak9fMXBraS1tMTNQWUVGRWJWa09Pd3MzN3ZBQnFpaGJGcTRlX2RkRk5ZaFUzTEM2ekcwY2NGajE1bTI1OUNlUFdPUEVvZGZWRDJUbHJlanVwdFg1SzVHR3hEZ2NUZUN3REFMTFBGVEY2YVhBbGlWVzFac2dHcU1TYWZCRHhvcllnPTcxODRFRTgz")
                .multiPart("file",new File("C:\\Users\\Vivek Ranjan\\OneDrive\\Pictures\\133660070265357947.jpg"))
               // .body(PayLoad.createBug("SCRUM","Test Button Not Working", "Bug"))
                .when().post("/rest/api/3/issue/{Key}/attachments")
                .then()
                //.log().all() //Log is not needed as now we are saving the responce in string
                .assertThat().statusCode(200)
                //  .body("key",equalTo("SCRUM-4"))
                .header("Server","AtlassianEdge")
                .extract().response().asString();
        System.out.println("Responce: "+Responce);
        JsonPath js = commonMethod.getJson(Responce);
        String attachmentFileName=js.getString("filename");
        Place_Id.put("attachmentFileName",attachmentFileName);
        System.out.println("attachmentFileName: "+attachmentFileName);

    }
}
