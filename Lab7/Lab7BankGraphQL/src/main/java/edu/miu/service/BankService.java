package edu.miu.service;

import edu.miu.dto.AccountRequest;
import edu.miu.entity.Account;
import edu.miu.data.AccountRepository;
import edu.miu.dto.AccountRequest;
import edu.miu.entity.Account;
import edu.miu.entity.TransactionEntry;
import edu.miu.entity.TransactionType;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.time.format.TextStyle;
import java.util.ArrayList;

@Service
public class BankService {
    private AccountRepository repo;

    public Account createAccount(AccountRequest accountRequest) {
        Account acc = Account.builder().accountNumber(accountRequest.getAccountNumber())
                .accountHolder(accountRequest.getAccountHolder())
                .amount(0.00)
                .transactionEntries(new ArrayList<>()).build();

//        Account account = new Account();
//        account.setAccountNumber(accountRequest.getAccountNumber());
//        account.setAccountHolder(accountRequest.getAccountHolder());
//        account.setAmount(0.00);
//        account.setTransactionEntries(new ArrayList<>());
        repo.save(acc);
        return acc;

    }

    public Account deposit(int accountNumber, double amount) {
        Account account = repo.findById(accountNumber).orElse(null);
        if (account != null) {
            TransactionEntry transactionEntry = new TransactionEntry(amount, TransactionType.DEPOSIT);
            account.getTransactionEntries().add(transactionEntry);
            account.setAmount(account.getAmount() + amount);
            repo.save(account);
            return account;
        }

        return null;
    }

    public Account withdraw(int accountNumber, double amount) {
        Account account = repo.findById(accountNumber).orElse(null);
        if (account != null) {
            TransactionEntry transactionEntry = new TransactionEntry(amount, TransactionType.WITHDRAWAL);
            account.getTransactionEntries().add(transactionEntry);
            account.setAmount(account.getAmount() + amount);
            repo.save(account);
            return account;
        }

        return null;
    }

    public Account getAccount(int accountNumber) {
        return repo.findById(accountNumber).orElse(null);
    }

    public boolean removeAccount(int accountNumber) {
        if (repo.findById(accountNumber).isPresent()) {
            repo.deleteById(accountNumber);
            return true;
        } else {
            return false;
        }
    }
}
