package localhost.restfullbooker_crudoperation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import localhost.resfull_booker_model.RestFullPojo;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class RestFullBooker_Crud {
    int idNumber;
    String firstName = "Komal";
    String lastName = "Kanji";
    int totalPrice = 111;
    Boolean depositPaid = true;
    String checkIn = "2018-01-01";
    String checkOut = "2018-01-01" + "}";
    String additionalNeeds = "Breakfast";

    @BeforeTest
    public void inIt(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.basePath = "/booking";
    }

    @Test (priority = 1)
    public void getAllBooking(){
        given()
                .when()
                .get()
                .then().log().all()
                .statusCode(200);
    }

    @Test (priority = 2)
    public void getBookingByID(){
        Response response = given()
                .pathParam("id",1880)
                .when()
                .get("/{id}");
        response.then().statusCode(200);
    }

    @Test (priority = 3)
    public void createNewBooking(){

        List<String > checkInAndOut = new ArrayList<>();
        checkInAndOut.add("2018-01-01");
        checkInAndOut.add("2019-01-01");

        RestFullPojo restFullPojo = new RestFullPojo();
        restFullPojo.setFirstname(firstName);
        restFullPojo.setLastname(lastName);
        restFullPojo.setTotalprice(totalPrice);
        restFullPojo.setDepositpaid(depositPaid);
        restFullPojo.setBookingdates(checkInAndOut);
        restFullPojo.setAdditionalneeds(additionalNeeds);

       Response response = given().log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(restFullPojo)
                .post();
        response.then().statusCode(201);
    }


}
