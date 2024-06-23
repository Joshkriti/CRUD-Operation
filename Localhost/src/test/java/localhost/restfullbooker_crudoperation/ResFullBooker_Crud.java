package localhost.restfullbooker_crudoperation;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ResFullBooker_Crud {

    @BeforeTest
    public void inIt(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.basePath = "/booking";
    }

    @Test
    public void getAllBooking(){
        given()
                .when()
                .get()
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void getAllBookingByID(){
        Response response = given()
                .when()
                .pathParam("id",720)
                .get("/{id}");
        response.then().statusCode(200);
    }

    @Test
    public void createStudentData(){

    }
}
