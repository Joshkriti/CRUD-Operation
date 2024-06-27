package localhost.product_crudoperation;

import io.restassured.response.Response;
import localhost.product_model.ProductPojo;
import localhost.testbase.TestBase;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Product1_Crud extends TestBase {

    String name = "Hand Watch";
    String type = "HardGood";
    float price = 25.50F;
    int shipping = 2;
    String upc = "041333440019";
    String description = "It is really useful device that you cant leave out with.";
    String manufactures = "Casio";
    String model = "C";
    String url = "https://www.casio.co.uk/watches-clocks";
    String img = "https://images.app.goo.gl/bHAV1WMTR2zpCZQM7";
    int productId;

    @Test(priority = 1)
    public void getAllProduct() {
        given()
                .when()
                .get()
                .then().statusCode(200);
    }

    @Test(priority = 2)
    public void getProductById() {
        Response response = given()
                .pathParam("id", 185230)
                .when()
                .get("/{id}");
        response.then().log().all().statusCode(200);
    }

    @Test (priority = 3)
    public void createNewProduct() {

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
                .log().all()
                .header("Content-Type", "application/json")
                .when()
                .body(productPojo)
                .post()
                .getBody()
                .as(ProductPojo.class);

        System.out.println(productPojo1.getId());
        productId = productPojo1.getId();
    }



    @Test (priority = 4)
    public void updateProductDetails(){
        ProductPojo productPojo = new ProductPojo();

        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(30.00F);
        productPojo.setShipping(4);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufactures);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(img);

        Response response=given()
                .log().all()
                .header("Content-Type", "application/json")
                .pathParam("id", productId)
                .when()
                .body(productPojo)
                .patch("/{id}");
        response.then().statusCode(200);
    }

    @Test (priority = 5)
    public void deleteProduct(){
        Response response = given()
                .pathParam("id",productId)
                .when()
                .delete("/{id}");
        response.then().statusCode(200);
    }

    @Test (priority = 6)
    public void confirmDelete(){
        Response response = given()
                .pathParam("id", productId)
                .when()
                .get("/{id}");
        response.then().statusCode(404);
    }




}
