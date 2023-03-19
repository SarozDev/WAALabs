package edu.miu.lab5bankmongo.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AccountRequest {
    @NotNull
    @Min(1)
    private int accountNumber;

    @NotNull
    @Size(min = 2, max = 100)
    private String accountHolder;

    private AccountRequest(int accountNumber, String accountHolder){
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
    }

    public int getAccountNumber(){
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber){
        this.accountNumber = accountNumber;
    }

    public String getAccountHolder(){
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder){
        this.accountHolder = accountHolder;
    }
}
