package stepDefinition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.AddPlace;
import pojo.Location;
import utils.APIResources;
import utils.JsonUtils;
import utils.RequestBuilding;
import utils.ResponseJsonValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class LocationAPIAT extends RequestBuilding {

    private AddPlace place;
    private String jsonObject;
    String place_id;
    Response response;

    public static RequestSpecification requestSpecification;

    @Given("building the request for API")
    public void buildingRequestSpecObject() throws IOException {
        requestSpecification = given().spec(buildRequest());
    }
    @Given("creating body with following JSON objects :")
    public void addingBodyToRequest(DataTable dataTable) {
        Map<String, String> locationData = new HashMap<String, String>();
        Map<String, String> placeData = new HashMap<String, String>();

        dataTable.asMaps(String.class, String.class).forEach(
                row -> {
                    String key = row.get("key");
                    String value = row.get("value");
                    if(value.startsWith("#") && value.contains("."))
                    {
                        value = JsonUtils.getJsonData(value);
                    }
                    if(key.equals("lat")||key.equals("lng"))
                    {
                        locationData.put(key, value);
                    }
                    else
                    {
                        placeData.put(key, value);
                    }
                }
        );
        Location location = new Location();
        location.setLat(Double.parseDouble(locationData.get("lat")));
        location.setLat(Double.parseDouble(locationData.get("lat")));
        place = new AddPlace();
        place.setLocation(location);
        place.setAccuracy(Integer.parseInt(placeData.get("accuracy")));
        place.setName(placeData.get("name"));
        place.setPhone_number(placeData.get("phone_number"));
        place.setAddress(placeData.get("address"));
        place.setWebsite(placeData.get("website"));
        place.setLanguage(placeData.get("language"));
    }
    @Given("I have  following types :")
    public void addingAdditionalRequestToJson(DataTable dataTable) {
        List<String> types = dataTable.asList(String.class);
        place.setTypes(types);
    }
    @When("convert the data into JSON object and add body to the request")
    public void convertDataToJsonObject() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        jsonObject = objectMapper.writeValueAsString(place);
        requestSpecification.body(jsonObject).log().all();
    }

    @When("user calls {string} method with the resource {string}")
    public void userCallsAPI(String method, String resource) {
        APIResources apiResources = APIResources.valueOf(resource);
        if(method.equalsIgnoreCase("POST")) {
            response = requestSpecification.when().post(apiResources.getResource());
        } else if (method.equalsIgnoreCase("GET")) {
            response = requestSpecification.queryParam("place_id",place_id).when().get(apiResources.getResource());
        } else if (method.equalsIgnoreCase("DELETE")) {
            response = requestSpecification.queryParam("place_id",place_id).when().delete(apiResources.getResource());
        }
    }

    @Then("API call got success and status is {int}")
    public void validateStatusCode(Integer status) {
        response.then().log().all();
        int i = status;
        assertEquals(response.getStatusCode(),Integer.parseInt(String.valueOf(i)));
    }

    @Then("verify {string} in response body is {string}")
    public void verifyResponseBody(String responseData, String testData) {
        if(testData.startsWith("#") && testData.contains("."))
        {
            testData = JsonUtils.getJsonData(testData);
        }
        assertEquals(ResponseJsonValue.getResponseJsonValue(response,responseData),testData);
    }

    @Then("user stores response value {string}")
    public void storeResponseValue(String responseKey) {
        place_id = ResponseJsonValue.getResponseJsonValue(response,responseKey);
    }
}