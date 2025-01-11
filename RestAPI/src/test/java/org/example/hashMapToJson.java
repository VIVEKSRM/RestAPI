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
        HashMap<String, Object> hm=new HashMap<>();
        hm.put("name","Learn Api");
        hm.put("isbn","btd");
        hm.put("aisle",250);
        hm.put("author","jhon fee");
/*in case of nested array we can put map into another map
* {
    "name":"Learn Api"
    "isbn":"bcd"
    "aisle":"227"
    "location":{
     "lat": "-3453"
     "lng": "33.456322"
        }
        * */
/*
 //in above case do like below
   HashMap<String, Object> hm2=new HashMap<>();
     hm2.put("lat", "-3453");
     hm2.put("lng", "33.456322");
 //in main Hash map add it like below
    hm.put("location", hm2);
*/
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
