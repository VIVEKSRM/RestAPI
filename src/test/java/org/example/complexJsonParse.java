package org.example;

import dataFile.PayLoad;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class complexJsonParse {
    @Test
    public void jsonPath()
    {
        JsonPath js = new JsonPath(PayLoad.coursePrice());

        //Print Size of the Array (here course is an array inside Jason it contains data inside it.
        int count = js.getInt("courses.size()");
        System.out.println("count:- "+count);

        //Print Purchase Amount
       // int totalAmount=js.getInt("dashboard.purchaseAmount");
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
            int totalPurchaseAmount=0;
            for (i=0; i<count;i++) {
               int totalCopies= js.getInt("courses[" + i + "].copies");
               int totalPrice=js.getInt("courses[" + i + "].price");
               if(arr.isEmpty())
                   arr.add(totalCopies*totalPrice);
               else {
                   arr.add(0,arr.get(0) + (totalCopies * totalPrice));
               }
                totalPurchaseAmount= js.getInt("dashboard.purchaseAmount");
               if (arr.get(0).equals(totalPurchaseAmount)) {
                   System.out.println("Test Passed- Total Purchase Amount Matched with multiplication of Copies and Price:- " + totalPurchaseAmount);
               }
            }
            Assert.assertEquals(arr.get(0), totalPurchaseAmount, "Assertion Failed");

        }

        // Other examples :
        JsonPath jp=new JsonPath(PayLoad.coursePrice());

        // get the size of the array
        int counts=jp.getInt("courses.size()");

        // get the title of first course
        String coursesTitle=jp.getString("courses[0].title");

        // get the price of third course
        int coursesPrice=jp.getInt("courses[2].price");

        // Print the values
        System.out.println("coursesTitle :-"+coursesTitle +"   coursesPrice:- "+coursesPrice);


        //Get all the courses titles and store them in the List
        List<String> strList= jp.getList("courses.title");

        //Get all the courses copies and store them in the List
        List<Integer> intList=jp.getList("courses.copies");


        System.out.println(" Courses Titles: ");
        for (int i=0; i<counts; i++)
        {
            System.out.println((i+1) + jp.getString("courses["+i+"].title"));

        }


        // Using Java Collections to print the values from the List
        System.out.println(" Courses Titles using forEach from Java Collections : ");

        // forEach will also directly iterate the values of the list and print it. Here we are using Only printing hence we can do this.
        strList.forEach(n-> System.out.println(n));

        // Another way to do the same using stream
        System.out.println(" Courses Copies using Stream API: ");
        intList.stream().forEach(n-> System.out.print(n));

        // Another way to do the same using stream
        System.out.println(" Courses Copies using Stream API Filter Option: ");
        intList.stream()
                .filter(n->n>7)
                .forEach(n-> System.out.print(n));

        // Assertion Example
        Assert.assertTrue(coursesPrice>0, "coursesPrice should be greater than zero");

    }
}
