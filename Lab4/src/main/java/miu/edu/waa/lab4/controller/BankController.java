package miu.edu.waa.lab4.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import miu.edu.waa.lab4.entity.Bank;
import miu.edu.waa.lab4.entity.Transaction;
import miu.edu.waa.lab4.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/banks")
public class BankController {
    private final BankService bankService;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody @Valid Bank bank) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(bankService.createAccount(bank));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/deposit/{accNumber}/{amount}")
    public ResponseEntity<?> deposit(@PathVariable int accNumber, @PathVariable Double amount) {
        if (amount < 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Deposit amount must be greater than 0");
        }

        Bank bank = bankService.getAllBanks().get(accNumber);
        if (bank != null) {
            Transaction transaction = new Transaction("deposit", amount);
            bank.addTransaction(transaction);
            bankService.getAllBanks().put(accNumber, bank);
            return ResponseEntity.status(HttpStatus.OK).body(bank);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank account does not exist!");
    }

    @GetMapping("/withdraw/{accNumber}/{amount}")
    public ResponseEntity<?> withdraw(@PathVariable int accNumber, @PathVariable Double amount) {
        if (amount < 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Withdrawal amount must be greater than 0");
        }

        Bank bank = bankService.getAllBanks().get(accNumber);
        if (bank != null) {
            if (bank.calcBalance() - amount < 0) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient Funds");
            }
            Transaction transaction = new Transaction("withdraw", -amount);
            bank.addTransaction(transaction);
            bankService.getAllBanks().put(accNumber, bank);
            return ResponseEntity.status(HttpStatus.OK).body(bank);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank account does not exist!");
    }

    @GetMapping("/{accNumber}")
    public ResponseEntity<?> getAccount(@PathVariable int accNumber) throws Exception {
        Bank bank = bankService.getAccount(accNumber);
        if (bank == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank account does not exist!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(bank.toString());
    }

    @GetMapping
    public ResponseEntity<Collection<Bank>> getAccount() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(bankService.getAllBanks().values());
    }

    @DeleteMapping("/delete/{accNumber}")
    public ResponseEntity<?> removeAccount(@PathVariable int accNumber) throws Exception {
        if (bankService.getAllBanks().containsKey(accNumber)) {
            bankService.removeAccount(accNumber);
            return ResponseEntity.status(HttpStatus.OK).body("Account " + accNumber + " deleted!");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank Account not found!");
    }

}
