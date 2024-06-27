package localhost.product_crudoperation;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import localhost.product_model.ProductPojo;
import localhost.testbase.TestBase;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Product4_Crud extends TestBase {
    String name = "Cloths";
    String type = "Every types";
    float price = 15.00F;
    int shipping = 10;
    String upc = "041333440019";
    String description = "Flexible to wear, fit to size";
    String manufactures = "F&F";
    String model = "F";
    String url = "https://www.tesco.com/zones/clothing/womens";
    String img = "https://digitalcontent.api.tesco.com/v2/media/homepage/3dbb63e0-d610-4668-82c7-35191b0e0c8d/2411_734x380-top.jpeg";
    int productId;

    @Test
    public void test001_GetAllProduct(){
        given()
                .when()
                .get()
                .then().statusCode(200);
    }

    @Test
    public void test002_GetProductByID(){
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id",185230)
                .when()
                .get("/{id}");
        response.then().statusCode(200);
    }

    @Test
    public void test003_CreateNewProduct(){
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
    public void test004_updateProductDetails() {
        ProductPojo productPojo = new ProductPojo();

        productPojo.setName("cloths for every gender");
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setShipping(shipping);
        productPojo.setUpc("E2457866D");
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufactures);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(img);

        Response response = given()
                .header("Content-Type", "application/json")
                .pathParam("id",productId)
                .when()
                .body(productPojo)
                .put("/{id}");
        response.then().statusCode(200);
    }

    @Test
    public void test005_DeleteProduct(){
        Response response = given()
                .header("Content-Type", "application/json")
                .pathParam("id",productId)
                .when()
                .delete("/{id}");
        response.then().statusCode(200);
    }

    @Test
    public void test006_ConfirmDeleteProduct(){
        Response response = given()
                .header("Content-Type", "application/json")
                .pathParam("id",productId)
                .when()
                .get("/{id}");
        response.then().statusCode(404);
    }
}
