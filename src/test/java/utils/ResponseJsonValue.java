package utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ResponseJsonValue {

    public static String getResponseJsonValue(Response response, String key) {
        String resp = response.asString();
        JsonPath jsonPath = new JsonPath(resp);
        return jsonPath.get(key).toString();
    }
}
