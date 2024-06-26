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

public class Student5_Crud {

    String firstName = "Kiran";
    String lastName = "White";
    String email = TestUtils.getRandomValue() + "@gmail.com";
    String programme = "Computer Science";
    int studentId;

    @BeforeTest
    public void inIt() {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.port = 8080;
        RestAssured.basePath = "/student";
    }

    @Test(priority = 1)
    public void getAllStudent() {
        given()
                .when()
                .get("/list")
                .then().statusCode(200);
    }

    @Test(priority = 2)
    public void getStudentById() {
        Response response = given()
                .pathParam("id", 50)
                .when()
                .get("/{id}");
        response.then().body("firstName", equalTo("Yoshio")).statusCode(200);
    }

    @Test(priority = 3)
    public void createNewStudent() {

        List<String> courses = new ArrayList<>();
        courses.add("Algorithms");
        courses.add("Software Development");
        courses.add("Ethics");

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
        response.then().statusCode(201);
    }

    @Test(priority = 4)
    public void extractStudentId(){
        HashMap<String,Integer> dataOfStudent = given()
                .when()
                .get("/list")
                .then().statusCode(200)
                .extract().path("findAll{it.lastName == '" + lastName + "'}.get(1)");
        studentId =  dataOfStudent.get("id");
        System.out.println(studentId);
    }

    @Test (priority = 5)
    public void updateStudentData(){

        List<String> courses = new ArrayList<>();
        courses.add("Criminal Law");
        courses.add("Property Law");

        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName(firstName);
        studentPojo.setLastName(lastName);
        studentPojo.setEmail(email);
        studentPojo.setProgramme("Law");
        studentPojo.setCourses(courses);

        Response response = given()
                .header("Content-Type", "application/json")
                .pathParam("id",studentId)
                .when()
                .body(studentPojo)
                .patch("/{id}");
        response.then().statusCode(200);
    }

    @Test (priority = 6)
    public void deleteStudent(){
        Response response = given()
                .pathParam("id",studentId)
                .when()
                .delete("/{id}");
        response.then().statusCode(204);
    }

    @Test (priority = 7)
    public void confirmDelete(){
        Response response = given()
                .pathParam("id",studentId)
                .when()
                .get("/{id}");
        response.then().statusCode(404);
    }
}
