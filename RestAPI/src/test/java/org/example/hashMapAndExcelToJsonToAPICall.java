package org.example;

import io.restassured.RestAssured;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static io.restassured.RestAssured.given;

public class hashMapAndExcelToJsonToAPICall {

    @Test
    public void hashMaptoJson() throws IOException {
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

        // read data from excel
        ArrayList data=getDataFromExcel("TestCase1");
        hm.put("name",data.get(1));
        hm.put("isbn",data.get(2));
        hm.put("aisle",data.get(3));
        hm.put("author",data.get(4));
       // System.out.println(hm.put("author",data.get(4)));
       // System.out.println(hm);
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


    public ArrayList<String> getDataFromExcel(String testcaseName) throws IOException
    {
        //fileInputStream arguments
        ArrayList<String> a=new ArrayList<String>();

        FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\Project_Planing.xlsx");
        System.out.println("File Path: -"+System.getProperty("user.dir")+"\\src\\test\\resources\\Project_Planing.xlsx");
        XSSFWorkbook workbook=new XSSFWorkbook(fis);

        int sheets=workbook.getNumberOfSheets();
        for(int i=0;i<sheets;i++)
        {
            if(workbook.getSheetName(i).equalsIgnoreCase("exampleReference"))
            {
                XSSFSheet sheet=workbook.getSheetAt(i);
                //Identify Testcases coloum by scanning the entire 1st row
                Iterator<Row> rows= sheet.iterator();// sheet is collection of rows
                Row firstrow= rows.next();
                Iterator<Cell> ce=firstrow.cellIterator();//row is collection of cells
                int k=0;
                int coloumn = 0;
                while(ce.hasNext())
                {
                    Cell value=ce.next();
                    if(value.getStringCellValue().equalsIgnoreCase("HeaderCol1"))
                    {
                        coloumn=k;
                    }
                    k++;
                }
                System.out.println(coloumn);
                ////once coloumn is identified then scan entire testcase coloum to identify purcjhase testcase row
                while(rows.hasNext())
                {
                    Row r=rows.next();
                    if(r.getCell(coloumn).getStringCellValue().equalsIgnoreCase(testcaseName))
                    {
                        ////after you grab purchase testcase row = pull all the data of that row and feed into test
                        Iterator<Cell> cv=r.cellIterator();
                        while(cv.hasNext())
                        {
                            Cell c=	cv.next();
                            if(c.getCellType()== CellType.STRING)
                            {
                                a.add(c.getStringCellValue());
                            }
                            else{
                                a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
                            }
                        }
                    }
                }
            }
        }
        return a;
    }

}
