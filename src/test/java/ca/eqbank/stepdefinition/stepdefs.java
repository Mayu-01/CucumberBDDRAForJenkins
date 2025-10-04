package ca.eqbank.stepdefinition;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.JsonConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.example.GlobalVariables;
import org.example.Utilities;
import org.junit.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.example.GlobalVariables.*;

public class stepdefs {

    RequestSpecification requestSpecification;
    Response response;

    @Given("user points to {}")
    public void user_points_to_product_service(String service) throws IOException , FileNotFoundException {
        GlobalVariables.fetchServiceDetails(service);
        System.out.println("DetailsMap :"+detailsMap);
        System.out.println("URI" + newMap.get("servicename"));
        RestAssured.baseURI = (String) newMap.get("servicename");
        RestAssured.useRelaxedHTTPSValidation();
        requestSpecification = RestAssured.given().log().all().config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig()
                        .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .config(RestAssuredConfig.config().jsonConfig(new JsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL)));
    }

    @When("user provides the below headers")
    public void user_provides_the_below_headers(DataTable table) {
        Map<String, String> headers = table.asMap(String.class, String.class);
        Set<Map.Entry<String, String>> entry = headers.entrySet();
        for (Map.Entry<String, String> nodes : entry) {
            String headerKey = nodes.getKey();
            String headerValue = nodes.getValue();
            requestSpecification.header(headerKey, headerValue);
        }
        System.out.println(entry);
    }

    @When("user provides basic authentication")
    public void user_provides_basic_authentication() {
        System.out.println("inside basic auth");
    }

    @When("user provides following body")
    public void user_provides_following_body(DataTable table) throws IOException {
        List<String> jsonList = table.asList(String.class);
        String reqJsonPath;
        String reqJsonValue;
        String jsonFileName = jsonList.get(0);
        String randomName;
        if (jsonList.size() > 1) {
            String dynamicValue = jsonList.get(1);
            String[] arr = dynamicValue.split("=");
            reqJsonPath = arr[0];
            reqJsonValue = arr[1];
            if(reqJsonValue.startsWith("eq_")){
                System.out.println(reqJsonValue);
                  randomName=Utilities.name();
                reqJsonValue=randomName;
                DocumentContext document= JsonPath.parse(Utilities.readDataFromJsonFile(jsonFileName));
                System.out.println("document is: "+document);
                System.out.println("document read: "+document);
                document.set(reqJsonPath,reqJsonValue);
                String documentBody = document.jsonString();
                System.out.println(documentBody);
                requestSpecification.body(documentBody);
                            }
            else{
                reqJsonValue = arr[1];
            DocumentContext document= JsonPath.parse(Utilities.readDataFromJsonFile(jsonFileName));
            System.out.println("document is: "+document);
            System.out.println("document read: "+document);
            document.set(reqJsonPath,reqJsonValue);
            String documentBody = document.jsonString();
            System.out.println(documentBody);
            requestSpecification.body(documentBody);}
        } else {
            System.out.println("No parameterized payload values");
            String jsonBody = Utilities.readDataFromJsonFile(jsonFileName);
            requestSpecification.body(jsonBody);
        }
        String jsonBody = Utilities.readDataFromJsonFile(jsonFileName);
    }

    @When("user issues {} request to {}")
    public void user_issues_POST_request_to_endpoint(String httpmethod,String endpoint) {
        switch(httpmethod.toLowerCase()) {
            case "post":
                response = requestSpecification.post(endpoint);
                break;
            case "get":
                response = requestSpecification.get(endpoint);
                break;
            default:
                System.out.println("no http method");
        }
        System.out.println("Response is: "+response.asPrettyString());
    }

    @Then("status code should be 200")
    public void status_code_should_be_200() {
        System.out.println("inside then");
        int actualStatuscode = response.getStatusCode();
        System.out.println("Recieved status code is: "+actualStatuscode);
        Assert.assertEquals(200, actualStatuscode);
    }

    @When("user provides below query params")
    public void user_provides_below_query_params(DataTable table){
     Map<String,String> nodes=table.asMap(String.class,String.class);
     for(Map.Entry<String,String> eachNode:nodes.entrySet()){
         String qParamKey=eachNode.getKey();
         String qParamValue=eachNode.getValue();
         if(qParamValue.startsWith("CUSTOM_")){
             qParamValue=globalDataMap.get(qParamKey);
         }
         System.out.println("Query param key-value: "+qParamKey+" : "+qParamValue);
         requestSpecification.when().queryParam(qParamKey,qParamValue);
              }
    }

    @Then("store {} in {}")
    public void store_data_in_CUSTOM_name(String reqJsonPath,String key){
        String globalDataValue=response.getBody().jsonPath().getString(reqJsonPath);
        String globalDataKey=key.substring(7);
        if(key.startsWith("CUSTOM_")){
            GlobalVariables.globalDataMap.put(globalDataKey,globalDataValue);
        }else{
            GlobalVariables.globalDataMap.put(key,globalDataValue);
        }
        System.out.println("Global data map is: "+globalDataMap);
    }

}
