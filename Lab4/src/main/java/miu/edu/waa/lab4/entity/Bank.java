package miu.edu.waa.lab4.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class Bank {

    @Size(min = 1)
    private Integer accNumber;
    private String accHolder;

    public Bank(Integer accNumber, String accHolder){
        this.accNumber = accNumber;
        this.accHolder = accHolder;
    }

    @NotNull
    @Length(min = 3)
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
    }

    public Double calcBalance(){
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    @Override
    public String toString() {
        return "Bank{" +
                "accNumber=" + accNumber +
                ", transactions=" + transactions +
                '}';
    }
}
