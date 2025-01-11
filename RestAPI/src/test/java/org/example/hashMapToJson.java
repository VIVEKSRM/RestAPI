package org.example;

import dataFile.PayLoad;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class hashMapToJson {

    @Test
    public void hashMaptoJson(){
/* Json like below
{
    "name":"Learn Api"
    "isbn":"bcd"
    "aisle":"227"
    "author":"jhon fee"
 }
    */
        HashMap<String, String> hm=new HashMap<>();
        hm.put("name","Learn Api");
        hm.put("isbn","btd");
        hm.put("aisle","220");
        hm.put("author","jhon fee");

        RestAssured.baseURI="http://216.10.245.166";
        String Responce= given()
                .header("Content-Type","application/json")
                .body(hm)
                .when().post("/Library/Addbook.php")
                .then()
                //.log().all() //Log is not needed as now we are saving the responce in string
                .assertThat().statusCode(200)
                .extract().response().asString();

        System.out.println("Responce: "+Responce);


    }
}
