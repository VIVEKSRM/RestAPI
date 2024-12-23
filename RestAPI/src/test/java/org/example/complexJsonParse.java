package org.example;

import dataFile.PayLoad;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;

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


        //Print title of All courses
        HashMap<Integer,String> totalCourses=new HashMap<Integer, String>();
        for (int i=0;i<count;i++)
        {
            totalCourses.put(i,js.get("courses["+i+"].title"));
        }
        System.out.println("totalCourses Names :- "+totalCourses);

        //Print all the courses title and their respective Prices
        HashMap<String, Integer> coursesTitlePrice =new HashMap<>();
        for (int i=0; i<count;i++)
        {
            coursesTitlePrice.put(js.get("courses["+i+"].title"), js.get("courses["+i+"].price"));
        }
        System.out.println("All the courses title and their respective Prices :- "+coursesTitlePrice);


        //Print number of copies sold by RPA - RPA may shuffle in the Json - position /index is not fixed.
        for (int i=0;i<count;i++)        {
            if(js.get("courses["+i+"].title").equals("RPA"))
            {
                System.out.println("Number of copies sold by RPA :- "+js.get("courses["+i+"].copies"));
                break;
            }

          // Another Example
            ArrayList<Integer> arr=new ArrayList<>();
            for (i=0; i<count;i++) {
               int totalCopies= js.get("courses[" + i + "].copies");
               int totalPrice=js.get("courses[" + i + "].price");
               if(arr.isEmpty())
                   arr.add(totalCopies*totalPrice);
               else {
                   arr.add(0,arr.get(0) + (totalCopies * totalPrice));
               }
                int totalPurchaseAmount=js.getInt("dashboard.purchaseAmount");
               if (arr.get(0).equals(totalPurchaseAmount))
                System.out.println("Test Passed- Total Purchase Amount Matched with multiplication of Copies and Price:- "+totalPurchaseAmount);
            }
        }


    }
}
