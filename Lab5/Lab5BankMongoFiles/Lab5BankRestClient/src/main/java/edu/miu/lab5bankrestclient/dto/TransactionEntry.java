package edu.miu.lab5bankrestclient.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransactionEntry {
    private double transactionAmount;
    private TransactionType transactionType;
    private LocalDateTime transactionDate;

    public TransactionEntry(double transactionAmount, TransactionType transactionType){
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        transactionDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "TransactionEntry{" +
                "transactionAmount=" + transactionAmount +
                ", transactionType=" + transactionType +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
