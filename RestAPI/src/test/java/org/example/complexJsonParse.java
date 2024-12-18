package org.example;

import dataFile.PayLoad;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class complexJsonParse {
    @Test
    public void jsonPath()
    {
        JsonPath js = new JsonPath(PayLoad.coursePrice());
        
        //Print Size of the Array (here course is an array inside Jason it contains data inside it.
        int count = js.getInt("courses.size()");
        System.out.println("count:- "+count);

        //Print Purchase Amount
        int totalAmount=js.getInt("dashboard.purchaseAmount");
        System.out.println("totalAmount:- "+totalAmount);

        //Print Title of the first Course
        String titleFirstCourse= js.get("courses[0].title");
        System.out.println("Print Title of the first Course:- "+titleFirstCourse);

        //Print copies of the second Course
        int copiesSecondCourse= js.get("courses[1].copies");
        // Below is the example of integer converted to String
        // String copiesSecondCourse= js.get("courses[1].copies").toString();
        System.out.println("Print copies of the second Course:- "+copiesSecondCourse);
    }
}
