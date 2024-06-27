package localhost.product_crudoperation;

import io.restassured.response.Response;
import localhost.product_model.ProductPojo;
import localhost.testbase.TestBase;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Product2_Crud extends TestBase {
    String name = "Wall Clock";
    String type = "HardGood";
    float price = 10.50F;
    int shipping = 2;
    String upc = "041333440019";
    String description = "It is really useful device that you cant leave out with.";
    String manufactures = "John Lewis";
    String model = "JL";
    String url = "https://www.johnlewis.com/john-lewis-anyday-longstock-roman-numeral-quartz-fob-wall-clock-navy";
    String img = "https://johnlewis.scene7.com/is/image/JohnLewis/240224250?wid=640&hei=853";
    int productId;

    @Test
    public void test1_GetAllProduct(){
        given()
                .when()
                .get()
                .then().statusCode(200);
    }
    @Test
    public void test2_GetProductById(){
        Response response = given()
                .pathParam("id",43900)
                .when()
                .get("/{id}");
        response.then().statusCode(200);
    }
    @Test
    public void test3_CreateNewProduct(){
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
    public void test4_UpdateProductDetails(){
        ProductPojo productPojo = new ProductPojo();

        productPojo.setName("Wall and hand clock");
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setShipping(5);
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
    public void test5_DeleteProduct(){
        Response response = given()
                .pathParam("id",productId)
                .when()
                .delete("/{id}");
        response.then().statusCode(200);
    }

    @Test
    public void test6_ConfirmDelete(){
        Response response = given()
                .pathParam("id", productId)
                .when()
                .get("/{id}");
        response.then().statusCode(404);
    }

}
