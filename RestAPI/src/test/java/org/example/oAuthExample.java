package org.example;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static io.restassured.RestAssured.*;

public class oAuthExample {
    @Test
    public void oAuthTest() throws InterruptedException {
    String[] expectedWebAutomationTitles={"Selenium Webdriver Java","Cypress","Protractor"};
        String response = given()
                .formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type", "client_credentials")
                .formParams("scope", "trust")
                .when().log().all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
        System.out.println(response);
        JsonPath jsonPath = new JsonPath(response);
        String accessToken = jsonPath.getString("access_token");
        System.out.println(accessToken);

        //Below code is for direct printing the response
        /*String r2= given()
                .queryParams("access_token", accessToken)
                .when()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .asString();
        System.out.println(r2);*/

        //Convert response 2 (r2) to Json object - deserialization
        GetCourse getCourse= given()
                .queryParams("access_token", accessToken)
                .when().log().all()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .as(GetCourse.class);
        System.out.println("getLinkedIn: -"+getCourse.getLinkedIn());
        System.out.println("getInstructor :-"+getCourse.getInstructor());
        System.out.println("getCourseTitle from 2nd index API :-"+getCourse.getCourses().getApi().get(1).getCourseTitle());

        // More examples
        List<Api> api=getCourse.getCourses().getApi();
        for(int i=0; i<=api.size()-1; i++)
        {
            if (api.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
            {
                System.out.println("Data Present :-"+api.get(i).getCourseTitle().toString());
            }
        }

        // share all the Course Title in WedAutomation
        List<pojo.WebAutomation> webAutomationActualTitles= getCourse.getCourses().getWebAutomation();
        ArrayList<String> arrWebAutomationActualTitles= new ArrayList<>();
        for(int i=0; i<=webAutomationActualTitles.size()-1; i++)
        {
            arrWebAutomationActualTitles.add(webAutomationActualTitles.get(i).getCourseTitle().toString());
        }
        List<String> ExpectedList=Arrays.asList(expectedWebAutomationTitles);
        if(ExpectedList.equals(arrWebAutomationActualTitles))
        {
            System.out.println(" All the Values Matches");
        }
        else {
            System.out.println(" Values are not Matched");
        }
        Assert.assertEquals(ExpectedList,arrWebAutomationActualTitles);
    }
}
