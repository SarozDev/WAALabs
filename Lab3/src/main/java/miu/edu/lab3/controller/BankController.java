package miu.edu.lab3.controller;

import lombok.AllArgsConstructor;
import miu.edu.lab3.entity.Bank;
import miu.edu.lab3.entity.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/banks")
public class BankController {
    private Map<Integer, Bank> banks;

    @GetMapping("/getAccount/{accNumber}")
    public ResponseEntity<?> getAccount(@PathVariable int accNumber){
        Bank bank = banks.get(accNumber);
        return bank == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank acc does not exist!")
                : ResponseEntity.status(HttpStatus.OK).body(bank);
    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<Collection<Bank>> getAllBanks(){
        return ResponseEntity.status(HttpStatus.OK).body(banks.values());
    }

    @GetMapping("/deposit/{accNumber}/{amount}")
    public ResponseEntity<?> deposit(@PathVariable int accNumber, @PathVariable Double amount){
        if(amount < 1){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Amount must be greater than 0!");
        }

        Bank bank = banks.get(accNumber);
        if(bank!=null){
            Transaction transaction = new Transaction("deposit",amount);
            bank.addTransaction(transaction);
            banks.put(accNumber,bank);
            return ResponseEntity.status(HttpStatus.OK).body(bank);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account does not exist!");
    }

    @GetMapping("/withdraw/{accNumber}/{amount}")
    public ResponseEntity<?> withdraw(@PathVariable int accNumber, @PathVariable Double amount){
        if(amount < 1){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Withdraw Amount must be greater than 0!");
        }

        Bank bank = banks.get(accNumber);
        if(bank!=null){
            if(bank.calcBalance() - amount < 0){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient fund!");
            }

            Transaction transaction = new Transaction("withdraw",amount);
            bank.addTransaction(transaction);
            banks.put(accNumber,bank);
            return ResponseEntity.status(HttpStatus.OK).body(bank);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account does not exist!");
    }

    @PostMapping("/addAccount")
    public ResponseEntity<?> createAccount(@RequestBody Bank bank){
        if(bank.getAccNumber() == null && bank.getCustomer() == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account Number/Customer not found!");
        }

        if(banks.containsKey(bank.getAccNumber())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account already exists!");
        }

        banks.put(bank.getAccNumber(),bank);
        return ResponseEntity.status(HttpStatus.OK).body(bank);
    }

    @DeleteMapping("/delete/{accNumber}")
    public ResponseEntity<?> deleteAccount(@PathVariable int accNumber){
        if(banks.containsKey(accNumber)){
            banks.remove(accNumber);
            return ResponseEntity.status(HttpStatus.OK).body("Account " + accNumber + " deleted successfully!");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found!");
    }


}
