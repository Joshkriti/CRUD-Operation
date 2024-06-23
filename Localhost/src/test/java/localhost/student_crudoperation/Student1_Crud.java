package localhost.student_crudoperation;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;

public class Student1_Crud {

    @BeforeTest
    public void inIt(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3030;
        RestAssured.basePath = "/student";
    }
}
