package dataFile;
import org.testng.annotations.DataProvider;

public class DPClass {

    @DataProvider(name="dp1")
    public Object[][] getData()
    {
        return new Object[][]{
                {"test1","test2"},
                {"test3", "test4"},
                {"test5","Test6"}
    };
    }
}
