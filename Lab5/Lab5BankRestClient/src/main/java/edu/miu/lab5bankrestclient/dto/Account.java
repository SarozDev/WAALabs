package edu.miu.lab5bankrestclient.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.descriptor.web.MessageDestinationRef;

import java.util.List;

@Data
@NoArgsConstructor
public class Account {
    private int accountNumber;
    private String accountHolder;
    private double amount;
    private List<TransactionEntry> transactionEntries;

    public Account(int accountNumber, String accountHolder){
        this.accountHolder = accountHolder;
        this.accountNumber=accountNumber;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", accountHolder='" + accountHolder + '\'' +
                ", amount=" + amount +
                ", transactionEntries=" + transactionEntries +
                '}';
    }
}
