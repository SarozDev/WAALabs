package miu.edu.lab3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bank {
    private Integer accNumber;
    private String customer;
    private List<Transaction> transactions = new ArrayList<>();

    public Bank(Integer accNumber, String customer){
        this.accNumber = accNumber;
        this.customer = customer;
    }
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public double calcBalance() {
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    @Override
    public String toString() {
        return "Bank{" +
                "accNumber=" + accNumber +
                ", customer='" + customer + '\'' +
                ", transactions=" + transactions +
                '}';
    }
}
