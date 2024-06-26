package localhost.student_crudoperation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import localhost.student_model.StudentPojo;
import localhost.utils.TestUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Student1_Crud {

    @BeforeTest
    public void inIt(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/student";
    }

    static String firstName = "Komal";
    static String lastName = "Kanji";
    static String email = TestUtils.getRandomValue() + "@gmail.com";
    static String programme = "Financial Analysis";
    static int studentId;

    @Test
    public void test1_GetAllStudent(){
        given()
                .when()
                .get("/list")
                .then().log().all()
                .statusCode(200);
    }
    @Test
    public void test2_GetStudentByID(){
        Response response = given()
                .pathParam("id", "7")
                .when()
                .get("/{id}");
        response.then().statusCode(200);
    }

    @Test
    public void test3_CreateNewStudent(){

        List<String>courseList = new ArrayList<>();
        courseList.add("Accounting");
        courseList.add("Statistics");

        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName(firstName);
        studentPojo.setLastName(lastName);
        studentPojo.setEmail(email);
        studentPojo.setProgramme(programme);
        studentPojo.setCourses(courseList);

        Response response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(studentPojo)
                .post();
        response.then().statusCode(201);
    }

    @Test
    public void test4_ExtractStudentDataByID(){
        HashMap<String,?> studentData = given()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .extract().path("findAll{it.firstName=='"+firstName+"'}.get(0)");

        studentId= (int) studentData.get("id");
        System.out.println(studentId);
    }

    @Test
    public void test5_ExtractStudentDataByHalfName(){
        List<String> studentData = given()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .extract().path("findAll{it.firstName.startsWith('Kom')}");
        System.out.println(studentData);
    }

    @Test
    public void test6_UpdateStudentData(){

        List<String>courseList = new ArrayList<>();
        courseList.add("Accounting");
        courseList.add("Statistics");

        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName(firstName);
        studentPojo.setLastName(lastName);
        studentPojo.setEmail(email);
        studentPojo.setProgramme("Business Analyst");
        studentPojo.setCourses(courseList);

        Response response = given()
                .log().all()
                .header("Content-Type", "application/json")
                .pathParam("id",studentId)
                .when()
                .body(studentPojo)
                .patch("/{id}");
        response.then().statusCode(200);
    }

    @Test
    public void test7_DeleteStudent(){
        Response response = given()
                .pathParam("id",studentId)
                .when()
                .delete("/{id}");
        response.then().log().all().statusCode(204);
    }

    @Test
    public void test8_ConfirmDelete(){

        Response response=given()
                .log().all()
                .pathParam("id", studentId)
                .when()
                .get("/{id}");
        response.then().statusCode(404);

    }










}
