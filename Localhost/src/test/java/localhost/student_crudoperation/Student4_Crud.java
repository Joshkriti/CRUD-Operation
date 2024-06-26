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
import static org.hamcrest.CoreMatchers.equalTo;

public class Student4_Crud {

    String firstName = "Jayson";
    String lastName = "Norwood";
    String email = TestUtils.getRandomValue() + "@hotmail.com";
    String programme = "Mechanical Engineering";
    int studentId;

    @BeforeTest
    public void inIt(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/student";
    }

    @Test (priority = 1)
    public void getAllStudent(){
        given()
                .when()
                .get("/list")
                .then().statusCode(200);
    }
    @Test (priority = 2)
    public void getStudentById(){
        Response response = given()
                .pathParam("id",15)
                .when()
                .get("/{id}");
        response.then().body("firstName", equalTo("Hakeem")).statusCode(200);
    }
    @Test (priority = 3)
    public void createNewStudent(){

        List<String> courseList = new ArrayList<>();
        courseList.add("Machine Element Design");
        courseList.add("Engineering Analysis I");

        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName(firstName);
        studentPojo.setLastName(lastName);
        studentPojo.setEmail(email);
        studentPojo.setProgramme(programme);
        studentPojo.setCourses(courseList);

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(studentPojo)
                .post();
        response.then().statusCode(201);
    }

    @Test (priority = 4)
    public void extractStudentID(){
        HashMap<String, ? > data = given()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .extract().path("findAll{it.firstName == '"+firstName+"'}.get(1)");
        studentId = (int) data.get("id");
        System.out.println(studentId);
    }

    @Test (priority = 5)
    public void updateStudentDetails(){
        List<String> courseList = new ArrayList<>();
        courseList.add("Machine Element Design");
        courseList.add("Engineering Analysis I");
        courseList.add("Calculus & Linear Algebra I");

        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName("Joshua");
        studentPojo.setLastName(lastName);
        studentPojo.setEmail(email);
        studentPojo.setProgramme(programme);
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

    @Test (priority = 6)
    public void deleteStudent(){
        given()
                .pathParam("id", studentId)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204);
    }

    @Test (priority = 7)
    public void confirmDelete(){
        given()
                .pathParam("id",studentId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(404);
    }





}
