package demo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import reUsableMethods.commonMethod;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilderTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI="https://rahulshettyacademy.com";

		AddPlace p =new AddPlace();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("https://rahulshettyacademy.com");
		p.setName("Frontline house");
		List<String> myList =new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");

		p.setTypes(myList);
		Location l =new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);

		RequestSpecification req =new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON)
				.build();

		ResponseSpecification resspec =new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON)
				.build();

		RequestSpecification res=given().spec(req)
				.body(p);

		Response response =res.when().post("/maps/api/place/add/json").
				then().spec(resspec).extract().response();

		//Below is the direct Example
/*		Response response =given().spec(req).body(p)
				.when().post("/maps/api/place/add/json")
				.then().spec(resspec)
				.extract().response();*/

		String responseString=response.asString();
		System.out.println(responseString);
		JsonPath js = commonMethod.getJson(responseString);
		String placeID=js.getString("place_id");

		RequestSpecification res1=given().queryParam("place_id", placeID);
		Response responce1=res1.get();
		System.out.println("responce1 :-" +responce1.asString());

/*		String expectedNewAddress="101 Summer walk USA";
		String getPlace=given().log().all().queryParam("key","qaclick123")
				.queryParam("place_id", Place_Id.get("IdOne"))
				.when().get("maps/api/place/get/json")
				.then().log().all()
				.assertThat().statusCode(200)
				//verification of New address from RestAPI method
				.body("address",equalTo("101 Summer walk USA")).extract().response().asString();*/
	}

}
