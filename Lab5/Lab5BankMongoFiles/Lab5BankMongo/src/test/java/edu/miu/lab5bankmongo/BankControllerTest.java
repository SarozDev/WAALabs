package edu.miu.lab5bankmongo;

import edu.miu.lab5bankmongo.entity.Account;
import edu.miu.lab5bankmongo.entity.TransactionType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
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
        Account account = new Account(12, "Ram");

        given()
                .contentType("application/json")
                .body(account)
                .when().post("/accounts").then()
                .statusCode(200);

        //test account get
        given()
                .when()
                .get("/accounts/12")
                .then()
                .contentType(ContentType.JSON)
                .and()
                .body("accountNumber", equalTo(12))
                .body("accountHolder", equalTo("Ram"))
                .body("amount", equalTo(0.0f))
                .body("transactionEntries", hasSize(0));

        //cleanup
        given()
                .when()
                .delete("accounts/12");


    }

    @Test
    public void testCreateAccount() {
        //add the account
        Account account = new Account(12, "Ram");

        given()
                .contentType("application/json")
                .body(account)
                .when().post("/accounts").then()
                .statusCode(200);

        //test account get
        given()
                .when()
                .get("/accounts/12")
                .then()
                .statusCode(200)
                .and()
                .body("accountNumber", equalTo(12))
                .body("accountHolder", equalTo("Ram"))
                .body("amount", equalTo(0.0f))
                .body("transactionEntries", hasSize(0));

        //cleanup
        given()
                .when()
                .delete("accounts/12");
    }

    @Test
    public void testDepositAmount() {
        //add the account
        Account account = new Account(12, "Ram");

        given()
                .contentType("application/json")
                .body(account)
                .when().post("/accounts").then()
                .statusCode(200);

        // deposit Amount
        given()
                .contentType(ContentType.JSON)
                .queryParams(new HashMap<>() {{
                    put("transactionType", TransactionType.DEPOSIT);
                    put("amount", 100.0);
                }})
                .when().post("/accounts/deposit/12")
                .then()
                .statusCode(200);

        //test account get
        given()
                .when()
                .get("/accounts/12")
                .then()
                .statusCode(200)
                .and()
                .body("accountNumber", equalTo(12))
                .body("accountHolder", equalTo("Ram"))
                .body("amount", equalTo(0.0f))
                .body("transactionEntries", hasSize(0));

        //cleanup
        given()
                .when()
                .delete("accounts/12");


    }

    @Test
    public void testWithdrawalAmount() {
        //add the account
        Account account = new Account(12, "Ram");

        given()
                .contentType("application/json")
                .body(account)
                .when().post("/accounts").then()
                .statusCode(200);

        // deposit Amount
        given()
                .contentType(ContentType.JSON)
                .queryParams(new HashMap<>() {{
                    put("transactionType", TransactionType.WITHDRAWAL);
                    put("amount", 100.0);
                }})
                .when().post("/accounts/deposit/12")
                .then()
                .statusCode(200);

        //test account get
        given()
                .when()
                .get("/accounts/12")
                .then()
                .statusCode(200)
                .and()
                .body("accountNumber", equalTo(12))
                .body("accountHolder", equalTo("Ram"))
                .body("amount", equalTo(0.0f))
                .body("transactionEntries", hasSize(0));

        //cleanup
        given()
                .when()
                .delete("accounts/12");


    }

    @Test
    public void testDeleteAccount() {
        //add the account
        Account account = new Account(12, "Ram");
        given()
                .contentType("application/json")
                .body(account)
                .when().post("/accounts").then()
                .statusCode(200);

        //cleanup
        given()
                .when()
                .delete("accounts/12");

        given()
                .when()
                .get("/accounts/12")
                .then()
                .statusCode(404)
                .and()
                .body("errorMessage",equalTo("Could not get account info. Account not found!"));
    }

}
