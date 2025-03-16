package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

import org.json.JSONObject;
import java.util.Arrays;

public class apiTestDefinitions {

    private Response response;
    private String baseUrl = "https://petstore.swagger.io/v2";
    private JSONObject userData;
    private JSONObject petData;

    ///////////////////////////////Positive POST create User//////////////////
    @Given("I have the user data for creating a new user")
    public void validRequestPostuser() {
        // Membuat JSON object dengan data user yang ingin dikirim
        userData = new JSONObject();
        userData.put("id", 1);
        userData.put("username", "rizki");
        userData.put("firstName", "rizki");
        userData.put("lastName", "haw");
        userData.put("email", "rizkihaw@gmail.com");
        userData.put("password", "rizki123");
        userData.put("phone", "089339756742");
        userData.put("userStatus", 1);
    }

    @When("I send a POST request to {string} with the user data")
    public void POSTrequest(String endpoint) {
        // Mengirimkan request POST ke endpoint /user dengan data JSON yang sudah disiapkan
        response = given()
                       .baseUri(baseUrl)
                       .header("Content-Type", "application/json")
                       .body(userData.toString()) // Menambahkan data JSON sebagai request body
                   .when()
                       .post(endpoint);
    }

    @Then("I should get a {int} status code")
    public void StatusCode(int statusCode) {
        // Memastikan status code yang diterima adalah sesuai (200 OK)
        assertEquals(statusCode, response.jsonPath().getInt("code"));
    }
    
    @Then("the response should contain the new user information")
    public void ResponseBody() {
        // Memeriksa apakah kode status yang diterima adalah 200
        assertEquals(200, response.jsonPath().getInt("code"));
    
        // Memastikan bahwa response body berisi informasi yang benar
        assertEquals("unknown", response.jsonPath().getString("type"));
        assertEquals("1", response.jsonPath().getString("message"));
    }
    
    /////////////////Negative POST create User/////////////////
    @Given("I have the user data with invalid userStatus format")
    public void invalidRequestPostuser() {
    // Membuat data user dengan userStatus yang tidak valid (harusnya integer, tetapi menggunakan string)
    userData = new JSONObject();
    userData.put("id", 1);
    userData.put("username", "rizki");
    userData.put("firstName", "rizki");
    userData.put("lastName", "haw");
    userData.put("email", "rizkihaw@gmail.com");
    userData.put("password", "rizki123");
    userData.put("phone", "089339756742");
    userData.put("userStatus", "active");  // userStatus seharusnya integer, tetapi diubah jadi string
    }

    @Then("the response should indicate a server error")
    public void responseErrorPostuser() {
        // Memastikan bahwa response mengandung error terkait server
        assertEquals("unknown", response.jsonPath().getString("type"));
    }

    @Then("the response message should be {string}")
    public void ResponseMessagePostuser(String expectedMessage) {
        // Memastikan bahwa response mengandung pesan error yang benar
        assertEquals(expectedMessage, response.jsonPath().getString("message"));
    }

    ///////////////////////////////GET Login User//////////////////
    @Given("I have valid login credentials with username {string} and password {string}")
    public void ValidloginParam(String username, String password) {
        // Menggunakan parameter username dan password untuk login
        response = given()
                       .baseUri(baseUrl)
                       .when()
                       .get("/user/login?username=" + username + "&password=" + password);
    }

    @When("I send a GET request to {string} with the credentials")
    public void sendRequestvalidLogin(String endpoint) {
        // Mengirimkan GET request dengan credentials yang sudah diberikan
        response = given()
                       .baseUri(baseUrl)
                       .when()
                       .get(endpoint);
    }

    @Then("the response should contain the login details")
    public void bodyResponsevalidLogin() {
        // Memastikan bahwa response mengandung informasi login
        String message = response.jsonPath().getString("message");
        assertNotNull(message);  // Memastikan bahwa message ada
        assertTrue(message.contains("logged in user session:"));
    }
    
    ///////////////////////////////// GET logout User//////////////////
    @Given("I am logged in with valid account")
    public void ValidlogoutParam() {

    }

    @When("I send a GET request to {string}")
    public void sendRequestvalidLogout(String endpoint) {
        // Mengirimkan GET request ke endpoint logout
        response = given()
                       .baseUri(baseUrl)
                       .when()
                       .get("/user/logout");
    }

    @Then("the response should indicate the logout is successfully")
    public void bodyResponsevalidLogout() {
        // Memastikan bahwa response mengandung informasi logout
        assertEquals("ok", response.jsonPath().getString("message"));
    }

    //////////////////////////////Positive POST added a new PET//////////////////
    @Given("I have data for added a new pet to the store")
    public void validRequestPostPet() {
        // Membuat JSON object dengan data pet yang ingin ditambahkan
        petData = new JSONObject();
        petData.put("id", 0);

        // Category
        JSONObject category = new JSONObject();
        category.put("id", 0);
        category.put("name", "string");
        petData.put("category", category);

        // Pet details
        petData.put("name", "doggie");
        petData.put("photoUrls", Arrays.asList("string"));

        // Tags
        JSONObject tag = new JSONObject();
        tag.put("id", 0);
        tag.put("name", "string");
        petData.put("tags", Arrays.asList(tag));

        petData.put("status", "available");
    }

    @When("I send a POST request to {string} with the pet data")
    public void POSTrequestPet(String endpoint) {
        // Mengirimkan request POST ke endpoint /pet dengan data JSON yang sudah disiapkan
        response = given()
                       .baseUri(baseUrl)
                       .header("Content-Type", "application/json")
                       .body(petData.toString()) // Menambahkan data JSON sebagai request body
                   .when()
                       .post("/pet");
    }

    @Then("I should get {int} status code")
    public void StatusCodePet(int statusCode) {
        // Memastikan status code yang diterima adalah sesuai (200 OK)
        assertEquals(statusCode, response.getStatusCode());
    }

    @Then("the response should contains the new pet information")
    public void ResponseBodyPet() {
        // Memastikan bahwa response mengandung informasi pet baru
        assertNotNull("Response ID should not be null", response.jsonPath().getLong("id"));
        assertEquals("doggie", response.jsonPath().getString("name"));
        assertEquals("available", response.jsonPath().getString("status"));

        // Memeriksa category dan tags
        assertEquals("string", response.jsonPath().getString("category.name"));
        assertEquals("string", response.jsonPath().getString("tags[0].name"));
    }

    //////////////////////////////Negative POST added a new PET//////////////////
    @Given("I have data for added a new pet to the store with invalid response")
    public void invalidRequestPostPet() {
        // Membuat JSON object dengan data pet yang ingin ditambahkan
        petData = new JSONObject();
        petData.put("id", "ABC");

        // Category
        JSONObject category = new JSONObject();
        category.put("id", 0);
        category.put("name", "string");
        petData.put("category", category);

        // Pet details
        petData.put("name", "doggie");
        petData.put("photoUrls", Arrays.asList("string"));

        // Tags
        JSONObject tag = new JSONObject();
        tag.put("id", 0);
        tag.put("name", "string");
        petData.put("tags", Arrays.asList(tag));

        petData.put("status", "available");
    }

    ///////////////////////////////Positive Get list pet//////////////////
    @Given("I have valid pet id")
    public void validRequestGetPet() {
        // Membuat JSON object dengan data pet yang ingin ditambahkan
        response = given()
                       .baseUri(baseUrl)
                       .when()
                       .get("/pet/01");
    }

    ///////////////////////////////Negative Get list pet//////////////////
    @Given("I have invalid pet id")
    public void invalidRequestGetPet() {
        // Membuat JSON object dengan data pet yang ingin ditambahkan
        response = given()
                       .baseUri(baseUrl)
                       .when()
                       .get("/pet/003" );
    }
}

