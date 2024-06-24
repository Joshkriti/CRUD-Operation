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

public class Student2_Crud {

    static String firstName = "Jack";
    static String lastName = "Brown";
    static String email = TestUtils.getRandomValue() + "@gmail.com";
    static String programme = "Medicine";
    static int studentId;
    @BeforeTest
    public void inIt(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/student";
    }

    @Test
    public void test01_GetAllStudent(){
        given()
                .when()
                .get("/list")
                .then().statusCode(200);
    }

    @Test
    public void test02_GetAllStudentByID(){
        Response response = given()
                .pathParam("id",5)
                .when()
                .get("/{id}");
        response.then().log().all().statusCode(200);
    }

    @Test
    public void test03_CreateNewStudent(){

        List<String> courseName = new ArrayList<>();
        courseName.add("Anatomy");
        courseName.add("Biochemistry");
        courseName.add("Genetics");

        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName(firstName);
        studentPojo.setLastName(lastName);
        studentPojo.setEmail(email);
        studentPojo.setProgramme(programme);
        studentPojo.setCourses(courseName);

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(studentPojo)
                .post();
        response.then().statusCode(201);
    }

    @Test
    public void test04_ExtractStudentID(){
        HashMap<String, ? > data = given()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .extract().path("findAll{it.firstName == '"+firstName+"'}.get(0)");
        studentId = (int) data.get("id");
        System.out.println(studentId);
    }

    @Test
    public void test05_ExtractStudentWithEndName(){
        List<String> name = given()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .extract().path("findAll{it.lastName.endsWith('own')}");
        System.out.println(name);
    }

    @Test
    public void test06_UpdateStudent(){

        List<String> courseName = new ArrayList<>();
        courseName.add("Anatomy");
        courseName.add("Biochemistry");
        courseName.add("Genetics");

        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName("Jacks");
        studentPojo.setLastName("Browns");
        studentPojo.setEmail(email);
        studentPojo.setProgramme(programme);
        studentPojo.setCourses(courseName);

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
    public void test07_DeleteStudent(){

        Response response = given()
                .pathParam("id",studentId)
                .when()
                .delete("/{id}");
        response.then().log().all().statusCode(204);
    }

    @Test
    public void test08_ConfirmDelete(){

        Response response = given()
                .pathParam("id",studentId)
                .when()
                .get("/{id}");
        response.then().log().all().statusCode(404);
    }




}
