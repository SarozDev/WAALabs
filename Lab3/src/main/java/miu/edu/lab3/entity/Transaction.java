package miu.edu.lab3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String description;
    private Double amount;
    private LocalDate date;

    public Transaction(String description, Double amount){
        this.description = description;
        this.amount = amount;
        this.date = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "description='" + description + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
