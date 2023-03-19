package miu.edu.waa.lab4.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import miu.edu.waa.lab4.entity.Bank;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class BankControllerTest {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void testGetOneAccount() {
        //add the account
        Bank bank = new Bank(11, "Ram");

        given()
                .contentType("application/json")
                .body(bank)
                .when()
                .post("/banks").then()
                .statusCode(200);

        //get account
        given()
                .when()
                .get("accounts/11")
                .then()
                .contentType(ContentType.JSON)
                .and()
                .body("accNumber", equalTo(11))
                .body("accHolder", equalTo("Ram"))
                .body("amount", equalTo(0.0f))
                .body("transactions", hasSize(0));

        //cleanUp
        given()
                .when()
                .delete("/banks/11");

    }

    @Test
    public void testCreateAccount() {
        //add account
        Bank bank = new Bank(11, "Ram");
        given()
                .contentType("application/json")
                .body(bank)
                .when()
                .post("/banks").then()
                .statusCode(200);

        //get account
        given()
                .when()
                .get("accounts/11")
                .then()
                .contentType(ContentType.JSON)
                .and()
                .body("accNumber", equalTo(11))
                .body("accHolder", equalTo("Ram"))
                .body("amount", equalTo(0.0f))
                .body("transactions", hasSize(0));

        //cleanUp
        given()
                .when()
                .delete("/banks/11");
    }

    @Test
    public void testDepositAmount() {
        //add account
        Bank bank = new Bank(11, "Ram");
        given()
                .contentType("application/json")
                .body(bank)
                .when()
                .post("/banks").then()
                .statusCode(200);

        //deposit amount
        given()
                .contentType(ContentType.JSON)
                .queryParams(new HashMap<>() {
                    {
                        put("description", "deposit");
                        put("amount", 100.0);
                    }
                })
                .when()
                .post("/banks/11").then().statusCode(200);

        //get account
        given()
                .when()
                .get("accounts/11")
                .then()
                .contentType(ContentType.JSON)
                .and()
                .body("accNumber", equalTo(11))
                .body("accHolder", equalTo("Ram"))
                .body("amount", equalTo(0.0f))
                .body("transactions", hasSize(0));

        //cleanUp
        given()
                .when()
                .delete("/banks/11");
    }

    @Test
    public void testWithdrawalAmount() {
        mapBankAccountWithTransaction();

        //get account
        given()
                .when()
                .get("accounts/11")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .and()
                .body("accNumber", equalTo(11))
                .body("accHolder", equalTo("Ram"))
                .body("amount", equalTo(80.0f))
                .body("transactions", hasSize(2));

        //cleanUp
        given()
                .when()
                .delete("/banks/11");
    }

    @Test
    public void testDeleteAccount() {
        //add account
        Bank bank = new Bank(11, "Ram");
        given()
                .contentType("application/json")
                .body(bank)
                .when()
                .post("/banks").then()
                .statusCode(200);

        given()
                .when()
                .delete("banks/11");

        given()
                .when()
                .get("accounts/11")
                .then()
                .statusCode(404)
                .and()
                .body("errorMessage", equalTo("Bank account does not exist!"));
    }

    private void mapBankAccountWithTransaction() {
        //add account
        Bank bank = new Bank(11, "Ram");
        given()
                .contentType("application/json")
                .body(bank)
                .when()
                .post("/banks").then()
                .statusCode(200);

        //deposit amount
        given()
                .contentType(ContentType.JSON)
                .queryParams(new HashMap<>() {
                    {
                        put("description", "deposit");
                        put("amount", 100.0);
                    }
                })
                .when()
                .post("/banks/11").then().statusCode(200);

        //deposit amount
        given()
                .contentType(ContentType.JSON)
                .queryParams(new HashMap<>() {
                    {
                        put("description", "withdraw");
                        put("amount", 20.0);
                    }
                })
                .when()
                .post("/banks/11").then().statusCode(200);

    }
}
