package org.example;

import dataFile.DPClass;
import dataFile.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import reUsableMethods.commonMethod;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test(dataProvider = "dp1", dataProviderClass = DPClass.class)
    public void addBook(String aisle, String isbn)
    {
        RestAssured.baseURI="http://216.10.245.166";
        String Responce= given().header("Content-Type","application/json").
                body(PayLoad.addBook(aisle,isbn)).
                when()
                .post("Library/Addbook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js=commonMethod.getJson(Responce);
        String id= js.get("ID");
        System.out.println(id);

    }
}
