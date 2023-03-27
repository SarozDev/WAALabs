package edu.miu.lab5bankmongo.controller;

import edu.miu.dto.AccountRequest;
import edu.miu.entity.Account;
import edu.miu.entity.TransactionType;
import edu.miu.service.BankService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

@Controller
public class BankController {

    @Autowired
    private BankService bankService;

    @QueryMapping
    public Account getAccount(@PathVariable int accountNumber) {
        return bankService.getAccount(accountNumber);
    }

    @MutationMapping
    public Optional<Account> createAccount(@Argument AccountRequest accountRequest) {
        Account account = bankService.createAccount(accountRequest);
        return Optional.ofNullable(account);
    }

    @MutationMapping
    public Optional<Account> deposit(@Argument int accountNumber, @Argument TransactionType transactionType, @Argument double amount) {
        Account account;
        if (transactionType.equals(TransactionType.DEPOSIT)) {
            account = bankService.deposit(accountNumber, amount);
        } else {
            account = bankService.withdraw(accountNumber, amount);
        }

        return Optional.ofNullable(account);
    }

    @MutationMapping
    public Boolean removeAccount(@Argument int accountNumber) {
        return bankService.removeAccount(accountNumber);
    }
}
