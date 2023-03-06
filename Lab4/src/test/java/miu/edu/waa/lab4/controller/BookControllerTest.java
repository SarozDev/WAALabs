package miu.edu.waa.lab4.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookControllerTest {
    JSONObject request;

    @BeforeEach
    void setUp() throws JSONException {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/books";

        request = new JSONObject();
        request.put("isbn", 1);
        request.put("author", "TestAuthor");
        request.put("title", "TestTitle");
        request.put("price", 1.0);
    }

    @Test
    void add_get_delete_book() {
        //add
        given()
                .relaxedHTTPSValidation("TLSv1.2")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json")
                .body(request.toJSONString())
                .when()
                .post()
                .then()
                .statusCode(200);

        //get
        given()
                .relaxedHTTPSValidation("TLSv1.2")
                .when()
                .get(String.valueOf(request.get("isbn")))
                .then()
                .body("author", equalTo(request.get("author")));

        //delete
        given().when()
                .delete("/" + request.get("isbn"))
                .then()
                .statusCode(200);

    }

    @Test
    void updateBook() {
        //add
        given()
                .relaxedHTTPSValidation("TLSv1.2")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json")
                .body(request.toJSONString())
                .when()
                .post()
                .then()
                .statusCode(200);

        request.put("author", "author updated");

        given()
                .relaxedHTTPSValidation("TLSv1.2")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json")
                .body(request.toJSONString())
                .when()
                .put()
                .then()
                .body("author", equalTo("author updated"))
                .statusCode(200);

        //restore
        request.put("author","author1");
        given()
                .relaxedHTTPSValidation("TLSv1.2")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json")
                .body(request.toJSONString())
                .when()
                .put()
                .then()
                .body("author", equalTo("author1"))
                .statusCode(200);

    }

    @Test
    void getAllBooks(){
        given()
                .relaxedHTTPSValidation("TLSv1.2")
                .when()
                .get()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200);
    }

    @Test
    void searchBooks(){
        given()
                .relaxedHTTPSValidation("TLSv1.2")
                .when()
                .get("/author/" + request.get("author"))
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200);
    }
}
