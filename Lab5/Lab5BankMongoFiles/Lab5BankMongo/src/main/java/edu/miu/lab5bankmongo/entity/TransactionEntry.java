package edu.miu.lab5bankmongo.entity;

import org.w3c.dom.html.HTMLIsIndexElement;

import java.time.LocalDateTime;

public class TransactionEntry {
    private final double trxAmount;

    private final TransactionType transactionType;
    private final LocalDateTime transactionTime;

    public TransactionEntry(double trxAmount, TransactionType transactionType){
        this.trxAmount = trxAmount;
        this.transactionType = transactionType;
        this.transactionTime = LocalDateTime.now();
    }

    public double getTrxAmount(){return trxAmount;}
    public TransactionType getTransactionType(){return transactionType;}
    public LocalDateTime getTransactionTime(){return transactionTime;}
}
