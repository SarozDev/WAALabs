package edu.miu.lab5bankrestclient;

import edu.miu.lab5bankrestclient.dto.Account;
import edu.miu.lab5bankrestclient.dto.AccountRequest;
import edu.miu.lab5bankrestclient.dto.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;

@SpringBootApplication
public class Lab5BankRestClientApplication implements CommandLineRunner {

    @Autowired
    private RestOperations restOperations;
    private String serverUrl = "http://localhost:8080/accounts";

    public static void main(String[] args) {
        SpringApplication.run(Lab5BankRestClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //add account
        restOperations.postForObject(serverUrl,new AccountRequest(12,"Ram"), Account.class);

        //get account
        Account account = restOperations.getForObject(serverUrl+"/12",Account.class);
        System.out.println(account);

        //deposit amount
        HashMap<String,?> depositParams=new HashMap<>(){{
           put("transactionType", TransactionType.DEPOSIT);
           put("amount", 27.07);
        }};

        String uri = UriComponentsBuilder.fromUriString(serverUrl+"/deposit/12")
                .queryParam("transactionType", TransactionType.DEPOSIT)
                .queryParam("amount", 27.07)
                .toUriString();

        Account account1 = restOperations.postForObject(uri,null,Account.class);
        System.out.println(account1);

        //delete a amount
        restOperations.delete(serverUrl+"/12");

    }
}
