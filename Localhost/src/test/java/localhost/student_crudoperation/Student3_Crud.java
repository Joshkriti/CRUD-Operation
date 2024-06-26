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
import static localhost.student_crudoperation.Student1_Crud.studentId;

public class Student3_Crud {

    String firstName = "Josh";
    String lastName = "White";
    String email = TestUtils.getRandomValue() + "@gmail.com";
    String programme = "Criminology";
    int studentID;
    @BeforeTest
    public void inIt(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/student";
    }

    @Test
    public void test001_GetAllStudent(){
        given()
                .when()
                .get("/list")
                .then().statusCode(200);
    }

    @Test
    public void test002_GetStudentByID(){
        Response response = given()
                .pathParam("id",10)
                .when()
                .get("/{id}");
        response.then().statusCode(200);
    }

    @Test
    public void test003_CreateNewStudent(){

        List<String> courses = new ArrayList<>();
        courses.add("Criminal Mind");
        courses.add("Gender, Crime and Justice ");

        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName(firstName);
        studentPojo.setLastName(lastName);
        studentPojo.setEmail(email);
        studentPojo.setProgramme(programme);
        studentPojo.setCourses(courses);

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(studentPojo)
                .post();
        response.then().log().all().statusCode(201);
    }

    @Test
    public void test004_ExtractStudentID(){
        HashMap<String, ? > data = given()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .extract().path("findAll{it.firstName == '"+firstName+"'}.get(1)");
        studentId = (int) data.get("id");
        System.out.println(studentId);
    }

    @Test
    public void test005_ExtractStudentWithEndName(){
        List<String> name = given()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .extract().path("findAll{it.lastName.endsWith('te')}");
        System.out.println(name);
    }

    @Test
    public void test006_DeleteStudent(){

        Response response = given()
                .pathParam("id",studentID)
                .when()
                .delete("/{id}");
        response.then().statusCode(204);
    }

    @Test
    public void test007_ConfirmDeleteStudent(){

        Response response = given()
                .pathParam("id", studentID)
                .when()
                .get("/{id}");
        response.then().statusCode(404);
    }
}
