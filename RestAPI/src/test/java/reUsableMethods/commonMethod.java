package reUsableMethods;

import io.restassured.path.json.JsonPath;

public class commonMethod {
    public static JsonPath getJson(String response)
    {
        JsonPath js = new JsonPath(response);
        return js;
    }
}
