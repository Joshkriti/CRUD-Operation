package localhost.product_crudoperation;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import localhost.product_model.ProductPojo;
import localhost.testbase.TestBase;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Product5_Crud extends TestBase {
    String name = "Necklace";
    String type = "HardGood";
    float price = 25.50F;
    int shipping = 15;
    String upc = "KK3440019G";
    String description = "Comfortable to wear.";
    String manufactures = "H.Samuel";
    String model = "H.S";
    String url = "https://www.hsamuel.co.uk/sterling-silver-18-inch-st-christopher-necklace/p/V-1070606";
    String img = "https://www.hsamuel.co.uk/productimages/processed/V-1070606_0_800.jpg?pristine=true";
    int productId;

    @Test
    public void test0001_GetAllStudent(){
        given()
                .when()
                .get()
                .then().statusCode(200);
    }

    @Test
    public void test0002_GetStudentById(){
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", 185267)
                .when()
                .get("/{id}");
        response.then().statusCode(200);
    }

    @Test
    public void test0003_CreateNewStudent(){
        ProductPojo productPojo = new ProductPojo();

        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setShipping(shipping);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufactures);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(img);

        ProductPojo productPojo1 = given()
                .header("Content-Type","application/json")
                .when()
                .body(productPojo)
                .post()
                .getBody()
                .as(ProductPojo.class);
        System.out.println(productPojo1.getId());
        productId = productPojo1.getId();
    }

    @Test
    public void test0004_UpdateProductDetails(){
        ProductPojo productPojo = new ProductPojo();

        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(100.00F);
        productPojo.setShipping(shipping);
        productPojo.setUpc(upc);
        productPojo.setDescription("Suitable for every event wear");
        productPojo.setManufacturer(manufactures);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(img);

        Response response = given()
                .header("Content-Type","application/json")
                .pathParam("id",productId)
                .when()
                .body(productPojo)
                .put("/{id}");
        response.then().statusCode(200);

    }
    @Test
    public void test0005_DeleteProduct(){
        Response response = given()
                .pathParam("id",productId)
                .when()
                .delete("/{id}");
        response.then().statusCode(200);

    }
    @Test
    public void test0006_ConfirmDelete(){
        Response response = given()
                .pathParam("id",productId)
                .when()
                .get("/{id}");
        response.then().statusCode(404);

    }

}
