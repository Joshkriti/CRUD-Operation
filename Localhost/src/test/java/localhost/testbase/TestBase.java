package localhost.testbase;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;

public class TestBase {

    @BeforeTest
    public void inIt(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3030;
        RestAssured.basePath = "/products";
    }

}
