package localhost.restfullbooker_crudoperation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import localhost.resfull_booker_model.RestFullPojo;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestFullBooker_Crud {
    int idNumber;
    String firstName = "Komal";
    String lastName = "Kanji";
    int totalPrice = 111;
    Boolean depositPaid = true;
    String bookingDate = "";
    String checkIn = "2018-01-01";
    String checkOut = "2018-01-01";
    String additionalNeeds = "Breakfast";

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
    public void getBookingByID(){
        Response response = given()
                .pathParam("id",1880)
                .when()
                .get("/{id}");
        response.then().statusCode(200);
    }

    @Test
    public void createNewBooking(){

        RestFullPojo restFullPojo = new RestFullPojo();
        restFullPojo.setFirstname(firstName);
        restFullPojo.setLastname(lastName);
        restFullPojo.setTotalprice(totalPrice);
        restFullPojo.setDepositpaid(depositPaid);
        //restFullPojo.setBookingdates();
        restFullPojo.setCheckin(checkIn);
        restFullPojo.setCheckout(checkOut);
        restFullPojo.setAdditionalneeds(additionalNeeds);
        System.out.println(restFullPojo);

        Response response = given().log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(restFullPojo)
                .post();
        response.then().statusCode(201);
       // int idNumber = response.then().extract().path("id");


    }
}
