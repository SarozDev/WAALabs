package miu.edu.waa.lab4.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Transaction {
    private String description;
    private Double amount;
    private LocalDate date;

    public Transaction(String description, Double amount){
        this.description = description;
        this.amount = amount;
        date = LocalDate.now();
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
