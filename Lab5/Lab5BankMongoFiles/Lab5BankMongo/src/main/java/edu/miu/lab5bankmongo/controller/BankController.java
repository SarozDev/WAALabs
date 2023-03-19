package edu.miu.lab5bankmongo.controller;

import edu.miu.lab5bankmongo.dto.AccountRequest;
import edu.miu.lab5bankmongo.entity.Account;
import edu.miu.lab5bankmongo.entity.TransactionType;
import edu.miu.lab5bankmongo.service.BankService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/accounts")
public class BankController {

    @Autowired
    private BankService bankService;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getAccount(@PathVariable int accountNumber) {
        Account accountInfo = bankService.getAccount(accountNumber);
        if (accountInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(accountInfo);
    }

    @PostMapping
    public ResponseEntity<Account> deposit(@RequestBody @Valid AccountRequest accountRequest) {
        Account account = bankService.createAccount(accountRequest);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @PostMapping("/deposit/{accountNumber}")
    public ResponseEntity<?> deposit(@PathVariable int accountNumber, @RequestParam TransactionType transactionType, @RequestParam double amount) {
        Account account;
        if (transactionType.equals(TransactionType.DEPOSIT)) {
            account = bankService.deposit(accountNumber, amount);
        } else {
            account = bankService.withdraw(accountNumber, amount);
        }

        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found, could not complete " + transactionType);
        }
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<?> removeAccount(@PathVariable int accountNumber) {
        boolean isDeleted = bankService.removeAccount(accountNumber);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found!");
        }

        HashMap<String, ?> response = new HashMap<>() {{
            put("Success", "1");
        }};

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
