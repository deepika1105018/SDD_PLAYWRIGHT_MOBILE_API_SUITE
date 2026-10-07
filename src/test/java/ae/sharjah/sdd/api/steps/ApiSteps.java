package ae.sharjah.sdd.api.steps;
import io.cucumber.java.en.*;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.*;
import java.util.*;
public class ApiSteps {
    private Response get,post;
    private String chainedName;
    @Given("ReqRes API is available") public void base() {
        RestAssured.baseURI="https://reqres.in";
    }
    @When("I get users from page 2") public void getUsers() {
        get=RestAssured.given().header("Accept","application/json").when().get("/api/users?page=2");
        Allure.addAttachment("GET response","application/json",get.asPrettyString(),"json");
    }
    @Then("status should be 200 and user 10 first name should be Byron") public void validateGet() {
        assertEquals(get.statusCode(),200);
        List<Map<String,Object>> data=get.jsonPath().getList("data");
        Map<String,Object> user=data.stream().filter(x->Integer.valueOf(10).equals(((Number)x.get("id")).intValue())).findFirst().orElseThrow();
        assertEquals(user.get("first_name"),"Byron");
        chainedName=(String)user.get("first_name");
    }
    @When("I create a user using chained GET data") public void create() {
        if(chainedName==null) {
            getUsers();
            validateGet();
        }
        Map<String,Object> body=new LinkedHashMap<>();
        body.put("name",chainedName);
        body.put("job","BA");
        post=RestAssured.given().contentType("application/json").body(body).when().post("/api/users");
        Allure.addAttachment("POST response","application/json",post.asPrettyString(),"json");
    }
    @Then("status should be 201 with generated id and valid schema") public void validatePost() {
        assertEquals(post.statusCode(),201);
        assertNotNull(post.jsonPath().get("id"));
        post.then().body(matchesJsonSchemaInClasspath("schemas/create-user-schema.json"));
    }
}
