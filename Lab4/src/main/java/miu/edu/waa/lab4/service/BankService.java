package miu.edu.waa.lab4.service;

import lombok.AllArgsConstructor;
import miu.edu.waa.lab4.entity.Bank;
import miu.edu.waa.lab4.entity.Transaction;
import miu.edu.waa.lab4.repo.BankRepo;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class BankService {
    private BankRepo bankRepo;

    public Bank createAccount(Bank bank) throws Exception {
        if (bank.getAccNumber() == null && bank.getAccNumber() == null) {
            throw new Exception("Acc number required!");
        }

        if (getAllBanks().containsKey(bank.getAccNumber())) {
            throw new Exception("Account number already exists!");
        }

        bankRepo.saveUpdateBank(bank);
        return bank;
    }

    public Map<Integer, Bank> getAllBanks() {
        return bankRepo.findAll();
    }

    public Bank getAccount(int accNumber) throws Exception {
        Bank bank = getAllBanks().get(accNumber);
        if (bank == null) {
            throw new Exception("Bank account does not exist!");
        }

        return bank;
    }

    public Bank deposit(int accNumber, double amount) throws Exception {
        if (amount < 1) {
            throw new Exception("Deposit amount must be greater than 0");
        }

        Bank bank = getAllBanks().get(accNumber);
        if (bank != null) {
            Transaction transaction = new Transaction("deposit", amount);
            bank.addTransaction(transaction);
            bankRepo.saveUpdateBank(bank);
            return bank;
        }

        throw new Exception("Account not found!");
    }

    public Bank withdraw(int accNumber, double amount) throws Exception {
        if (amount < 1) {
            throw new Exception("Withdraw amount must be greater than 0");
        }

        Bank bank = getAllBanks().get(accNumber);
        if (bank != null) {
            if (bank.calcBalance() - amount < 0) {
                throw new Exception("Insufficient fund!");
            }
            Transaction transaction = new Transaction("deposit", amount);
            bank.addTransaction(transaction);
            bankRepo.saveUpdateBank(bank);
            return bank;
        }

        throw new Exception("Account not found!");
    }

    public String removeAccount(int accNumber) throws Exception {
        if (getAllBanks().containsKey(accNumber)) {
            bankRepo.delete(accNumber);
            return "Account number: " + accNumber + " deleted";
        }
        throw new Exception("Bank account doesn't exist!");
    }
}
