package edu.miu.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Builder
@Document
public class Account {

    @Id
    private int accountNumber;

    private String accountHolder;
    private double amount;

    public Account(int accountNumber, String accountHolder, double amount, List<TransactionEntry> transactionEntries) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.amount = amount;
        this.transactionEntries = transactionEntries;
    }

    private List<TransactionEntry> transactionEntries;

    public Account(){

    }

    public static class Builder{

    }

    public Account(int accountNumber, String accountHolder){
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
    }

    public int getAccountNumber(){
        return accountNumber;
    }

    public double getAmount(){return this.amount;}

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setAccountNumber(int accountNumber){this.accountNumber = accountNumber;}

    public String getAccountHolder(){
        return accountHolder;
    }

     public void setAccountHolder(String accountHolder){
        this.accountHolder = accountHolder;
     }

     public List<TransactionEntry> getTransactionEntries(){return transactionEntries;}

    public void setTransactionEntries(List<TransactionEntry> transactionEntries){
        this.transactionEntries = transactionEntries;
    }

}
