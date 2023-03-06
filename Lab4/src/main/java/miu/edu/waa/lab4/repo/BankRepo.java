package miu.edu.waa.lab4.repo;

import miu.edu.waa.lab4.entity.Bank;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BankRepo {
    private final Map<Integer, Bank> bankAccounts = new HashMap<>();

    public void saveUpdateBank(Bank bank){
        bankAccounts.put(bank.getAccNumber(),bank);
    }

    public void delete(Integer accNum){
        bankAccounts.remove(accNum);
    }

    public Map<Integer, Bank> findAll(){
        return bankAccounts;
    }
}
