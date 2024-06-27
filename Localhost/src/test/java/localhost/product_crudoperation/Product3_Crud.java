package localhost.product_crudoperation;

import io.restassured.response.Response;
import localhost.product_model.ProductPojo;
import localhost.testbase.TestBase;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Product3_Crud extends TestBase {
    String name = "Computer";
    String type = "HardGood";
    float price = 150.00F;
    int shipping = 5;
    String upc = "041333440019";
    String description = "A computer is an electronic device that manipulates information, or data.";
    String manufactures = "MSI";
    String model = "M";
    String url = "https://www.currys.co.uk/products/msi-katana-b13v-15.6-gaming-laptop-intel-core-i9-rtx-4060-1-tb-ssd-10254228.html";
    String img = "https://media.currys.biz/i/currysprod/10254228?$l-large$&fmt=auto";
    int productId;

    @Test
    public void test01_GetAllProduct(){
        given()
                .when()
                .get()
                .then().statusCode(200);
    }

    @Test
    public void test02_GetProductById(){
        Response response = given()
                .pathParam("id",150115)
                .when()
                .get("/{id}");
        response.then().body("type", equalTo("HardGood")).statusCode(200);
    }

    @Test
    public void test03_CreateNewProduct(){
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

    @Test
    public void test04_updateProductDetails(){
        ProductPojo productPojo = new ProductPojo();

        productPojo.setName("Laptop and Computer");
        productPojo.setType("Hardware");
        productPojo.setPrice(2000.00F);
        productPojo.setShipping(shipping);
        productPojo.setUpc(upc);
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
    public void test05_DeleteProduct(){
        Response response = given()
                .header("Content-Type","application/json")
                .pathParam("id",productId)
                .when()
                .delete("/{id}");
        response.then().statusCode(200);
    }

    @Test
    public void test06_ConfirmProduct(){
        Response response = given()
                .header("Content-Type","application/json")
                .pathParam("id",productId)
                .when()
                .get("/{id}");
        response.then().statusCode(404);

    }
}
