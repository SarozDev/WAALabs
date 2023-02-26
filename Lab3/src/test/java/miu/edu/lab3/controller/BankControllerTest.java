package miu.edu.lab3.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import miu.edu.lab3.entity.Bank;
import miu.edu.lab3.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class BankControllerTest {

    @Test
    void testGetAccount() {
        ResponseEntity<?> actualAccount = (new BankController(new HashMap<>())).getAccount(10);
        assertEquals("Bank acc does not exist!", actualAccount.getBody());
        assertEquals(404, actualAccount.getStatusCodeValue());
        assertTrue(actualAccount.getHeaders().isEmpty());
    }


    @Test
    void testGetAccount2() {
        HashMap<Integer, Bank> integerBankMap = new HashMap<>();
        integerBankMap.put(1, mock(Bank.class));
        ResponseEntity<?> actualAccount = (new BankController(integerBankMap)).getAccount(10);
        assertEquals("Bank acc does not exist!", actualAccount.getBody());
        assertEquals(404, actualAccount.getStatusCodeValue());
        assertTrue(actualAccount.getHeaders().isEmpty());
    }


    @Test
    void testGetAccount3() {
        HashMap<Integer, Bank> integerBankMap = new HashMap<>();
        integerBankMap.put(1, mock(Bank.class));
        ResponseEntity<?> actualAccount = (new BankController(integerBankMap)).getAccount(1);
        assertTrue(actualAccount.hasBody());
        assertEquals(200, actualAccount.getStatusCodeValue());
        assertTrue(actualAccount.getHeaders().isEmpty());
    }


    @Test
    void testGetAllBanks() {
        ResponseEntity<Collection<Bank>> actualAllBanks = (new BankController(new HashMap<>())).getAllBanks();
        assertTrue(actualAllBanks.hasBody());
        assertEquals(200, actualAllBanks.getStatusCodeValue());
        assertTrue(actualAllBanks.getHeaders().isEmpty());
    }


    @Test
    void testGetAllBanks2() {
        HashMap<Integer, Bank> integerBankMap = new HashMap<>();
        integerBankMap.put(1, mock(Bank.class));
        ResponseEntity<Collection<Bank>> actualAllBanks = (new BankController(integerBankMap)).getAllBanks();
        assertTrue(actualAllBanks.hasBody());
        assertEquals(200, actualAllBanks.getStatusCodeValue());
        assertTrue(actualAllBanks.getHeaders().isEmpty());
    }


    @Test
    void testDeposit() {
        ResponseEntity<?> actualDepositResult = (new BankController(new HashMap<>())).deposit(10, 10.0d);
        assertEquals("Account does not exist!", actualDepositResult.getBody());
        assertEquals(404, actualDepositResult.getStatusCodeValue());
        assertTrue(actualDepositResult.getHeaders().isEmpty());
    }



    @Test
    void testDeposit3() {
        ResponseEntity<?> actualDepositResult = (new BankController(new HashMap<>())).deposit(10, 0.5d);
        assertEquals("Amount must be greater than 0!", actualDepositResult.getBody());
        assertEquals(403, actualDepositResult.getStatusCodeValue());
        assertTrue(actualDepositResult.getHeaders().isEmpty());
    }


    @Test
    void testDeposit4() {
        HashMap<Integer, Bank> integerBankMap = new HashMap<>();
        integerBankMap.put(1, mock(Bank.class));
        ResponseEntity<?> actualDepositResult = (new BankController(integerBankMap)).deposit(10, 10.0d);
        assertEquals("Account does not exist!", actualDepositResult.getBody());
        assertEquals(404, actualDepositResult.getStatusCodeValue());
        assertTrue(actualDepositResult.getHeaders().isEmpty());
    }


    @Test
    void testDeposit5() {
        Bank bank = mock(Bank.class);
        doNothing().when(bank).addTransaction((Transaction) any());

        HashMap<Integer, Bank> integerBankMap = new HashMap<>();
        integerBankMap.put(1, bank);
        ResponseEntity<?> actualDepositResult = (new BankController(integerBankMap)).deposit(1, 10.0d);
        assertTrue(actualDepositResult.hasBody());
        assertEquals(200, actualDepositResult.getStatusCodeValue());
        assertTrue(actualDepositResult.getHeaders().isEmpty());
        verify(bank).addTransaction((Transaction) any());
    }


    @Test
    void testDeposit6() {
        Bank bank = mock(Bank.class);
        doNothing().when(bank).addTransaction((Transaction) any());

        HashMap<Integer, Bank> integerBankMap = new HashMap<>();
        integerBankMap.put(1, new Bank());
        integerBankMap.put(-1, bank);
        ResponseEntity<?> actualDepositResult = (new BankController(integerBankMap)).deposit(1, 10.0d);
        assertTrue(actualDepositResult.hasBody());
        assertEquals(200, actualDepositResult.getStatusCodeValue());
        assertTrue(actualDepositResult.getHeaders().isEmpty());
        List<Transaction> transactions = ((Bank) actualDepositResult.getBody()).getTransactions();
        assertEquals(1, transactions.size());
        Transaction getResult = transactions.get(0);
        assertEquals(10.0d, getResult.getAmount().doubleValue());
        assertEquals("deposit", getResult.getDescription());
    }


    @Test
    void testWithdraw() {
        ResponseEntity<?> actualWithdrawResult = (new BankController(new HashMap<>())).withdraw(10, 10.0d);
        assertEquals("Account does not exist!", actualWithdrawResult.getBody());
        assertEquals(404, actualWithdrawResult.getStatusCodeValue());
        assertTrue(actualWithdrawResult.getHeaders().isEmpty());
    }



    @Test
    void testWithdraw3() {
        ResponseEntity<?> actualWithdrawResult = (new BankController(new HashMap<>())).withdraw(10, 0.5d);
        assertEquals("Withdraw Amount must be greater than 0!", actualWithdrawResult.getBody());
        assertEquals(403, actualWithdrawResult.getStatusCodeValue());
        assertTrue(actualWithdrawResult.getHeaders().isEmpty());
    }


    @Test
    void testWithdraw4() {
        HashMap<Integer, Bank> integerBankMap = new HashMap<>();
        integerBankMap.put(1, mock(Bank.class));
        ResponseEntity<?> actualWithdrawResult = (new BankController(integerBankMap)).withdraw(10, 10.0d);
        assertEquals("Account does not exist!", actualWithdrawResult.getBody());
        assertEquals(404, actualWithdrawResult.getStatusCodeValue());
        assertTrue(actualWithdrawResult.getHeaders().isEmpty());
    }


    @Test
    void testWithdraw5() {
        Bank bank = mock(Bank.class);
        doNothing().when(bank).addTransaction((Transaction) any());
        when(bank.calcBalance()).thenReturn(10.0d);

        HashMap<Integer, Bank> integerBankMap = new HashMap<>();
        integerBankMap.put(1, bank);
        ResponseEntity<?> actualWithdrawResult = (new BankController(integerBankMap)).withdraw(1, 10.0d);
        assertTrue(actualWithdrawResult.hasBody());
        assertEquals(200, actualWithdrawResult.getStatusCodeValue());
        assertTrue(actualWithdrawResult.getHeaders().isEmpty());
        verify(bank).calcBalance();
        verify(bank).addTransaction((Transaction) any());
    }


    @Test
    void testWithdraw6() {
        Bank bank = mock(Bank.class);
        doNothing().when(bank).addTransaction((Transaction) any());
        when(bank.calcBalance()).thenReturn(1.0d);

        HashMap<Integer, Bank> integerBankMap = new HashMap<>();
        integerBankMap.put(1, bank);
        ResponseEntity<?> actualWithdrawResult = (new BankController(integerBankMap)).withdraw(1, 10.0d);
        assertEquals("Insufficient fund!", actualWithdrawResult.getBody());
        assertEquals(403, actualWithdrawResult.getStatusCodeValue());
        assertTrue(actualWithdrawResult.getHeaders().isEmpty());
        verify(bank).calcBalance();
    }

    @Test
    void testWithdraw7() {
        Bank bank = mock(Bank.class);
        doNothing().when(bank).addTransaction((Transaction) any());
        when(bank.calcBalance()).thenReturn(10.0d);

        HashMap<Integer, Bank> integerBankMap = new HashMap<>();
        integerBankMap.put(1, new Bank());
        integerBankMap.put(-1, bank);
        ResponseEntity<?> actualWithdrawResult = (new BankController(integerBankMap)).withdraw(1, 10.0d);
        assertEquals("Insufficient fund!", actualWithdrawResult.getBody());
        assertEquals(403, actualWithdrawResult.getStatusCodeValue());
        assertTrue(actualWithdrawResult.getHeaders().isEmpty());
    }

    @Test
    void testWithdraw9() {
        Bank bank = mock(Bank.class);
        doNothing().when(bank).addTransaction((Transaction) any());
        when(bank.calcBalance()).thenReturn(10.0d);

        HashMap<Integer, Bank> integerBankMap = new HashMap<>();
        integerBankMap.put(1, new Bank());
        integerBankMap.put(-1, bank);
        ResponseEntity<?> actualWithdrawResult = (new BankController(integerBankMap)).withdraw(1, Double.NaN);
        assertTrue(actualWithdrawResult.hasBody());
        assertEquals(200, actualWithdrawResult.getStatusCodeValue());
        assertTrue(actualWithdrawResult.getHeaders().isEmpty());
        List<Transaction> transactions = ((Bank) actualWithdrawResult.getBody()).getTransactions();
        assertEquals(1, transactions.size());
        Transaction getResult = transactions.get(0);
        assertEquals(Double.NaN, getResult.getAmount().doubleValue());
        assertEquals("withdraw", getResult.getDescription());
    }


    @Test
    void testWithdraw10() {
        Bank bank = mock(Bank.class);
        doNothing().when(bank).addTransaction((Transaction) any());
        when(bank.calcBalance()).thenReturn(10.0d);

        Bank bank1 = new Bank();
        bank1.addTransaction(new Transaction("The characteristics of someone or something", 10.0d));

        HashMap<Integer, Bank> integerBankMap = new HashMap<>();
        integerBankMap.put(1, bank1);
        integerBankMap.put(-1, bank);
        ResponseEntity<?> actualWithdrawResult = (new BankController(integerBankMap)).withdraw(1, 10.0d);
        assertTrue(actualWithdrawResult.hasBody());
        assertEquals(200, actualWithdrawResult.getStatusCodeValue());
        assertTrue(actualWithdrawResult.getHeaders().isEmpty());
        List<Transaction> transactions = ((Bank) actualWithdrawResult.getBody()).getTransactions();
        assertEquals(2, transactions.size());
        Transaction getResult = transactions.get(1);
        assertEquals("withdraw", getResult.getDescription());
        assertEquals(10.0d, getResult.getAmount().doubleValue());
    }


    @Test
    void testCreateAccount() {
        BankController bankController = new BankController(new HashMap<>());
        ResponseEntity<?> actualCreateAccountResult = bankController.createAccount(new Bank());
        assertEquals("Account Number/Customer not found!", actualCreateAccountResult.getBody());
        assertEquals(403, actualCreateAccountResult.getStatusCodeValue());
        assertTrue(actualCreateAccountResult.getHeaders().isEmpty());
    }

    @Test
    void testCreateAccount3() {
        BankController bankController = new BankController(new HashMap<>());
        ResponseEntity<?> actualCreateAccountResult = bankController
                .createAccount(new Bank(10, "Account Number/Customer not found!"));
        assertTrue(actualCreateAccountResult.hasBody());
        assertEquals(200, actualCreateAccountResult.getStatusCodeValue());
        assertTrue(actualCreateAccountResult.getHeaders().isEmpty());
    }


    @Test
    void testCreateAccount4() {
        BankController bankController = new BankController(new HashMap<>());
        Bank bank = mock(Bank.class);
        when(bank.getAccNumber()).thenReturn(10);
        ResponseEntity<?> actualCreateAccountResult = bankController.createAccount(bank);
        assertTrue(actualCreateAccountResult.hasBody());
        assertEquals(200, actualCreateAccountResult.getStatusCodeValue());
        assertTrue(actualCreateAccountResult.getHeaders().isEmpty());
        verify(bank, atLeast(1)).getAccNumber();
    }


    @Test
    void testCreateAccount5() {
        BankController bankController = new BankController(new HashMap<>());
        Bank bank = mock(Bank.class);
        when(bank.getCustomer()).thenReturn("Customer");
        when(bank.getAccNumber()).thenReturn(null);
        ResponseEntity<?> actualCreateAccountResult = bankController.createAccount(bank);
        assertTrue(actualCreateAccountResult.hasBody());
        assertEquals(200, actualCreateAccountResult.getStatusCodeValue());
        assertTrue(actualCreateAccountResult.getHeaders().isEmpty());
        verify(bank, atLeast(1)).getAccNumber();
        verify(bank).getCustomer();
    }


    @Test
    void testCreateAccount6() {
        HashMap<Integer, Bank> integerBankMap = new HashMap<>();
        integerBankMap.put(1, new Bank());
        BankController bankController = new BankController(integerBankMap);
        Bank bank = mock(Bank.class);
        when(bank.getCustomer()).thenReturn("Customer");
        when(bank.getAccNumber()).thenReturn(1);
        ResponseEntity<?> actualCreateAccountResult = bankController.createAccount(bank);
        assertEquals("Account already exists!", actualCreateAccountResult.getBody());
        assertEquals(403, actualCreateAccountResult.getStatusCodeValue());
        assertTrue(actualCreateAccountResult.getHeaders().isEmpty());
        verify(bank, atLeast(1)).getAccNumber();
    }


    @Test
    void testDeleteAccount() {
        ResponseEntity<?> actualDeleteAccountResult = (new BankController(new HashMap<>())).deleteAccount(10);
        assertEquals("Account not found!", actualDeleteAccountResult.getBody());
        assertEquals(404, actualDeleteAccountResult.getStatusCodeValue());
        assertTrue(actualDeleteAccountResult.getHeaders().isEmpty());
    }


    @Test
    void testDeleteAccount2() {
        HashMap<Integer, Bank> integerBankMap = new HashMap<>();
        integerBankMap.put(1, mock(Bank.class));
        ResponseEntity<?> actualDeleteAccountResult = (new BankController(integerBankMap)).deleteAccount(10);
        assertEquals("Account not found!", actualDeleteAccountResult.getBody());
        assertEquals(404, actualDeleteAccountResult.getStatusCodeValue());
        assertTrue(actualDeleteAccountResult.getHeaders().isEmpty());
    }


    @Test
    void testDeleteAccount3() {
        HashMap<Integer, Bank> integerBankMap = new HashMap<>();
        integerBankMap.put(1, mock(Bank.class));
        ResponseEntity<?> actualDeleteAccountResult = (new BankController(integerBankMap)).deleteAccount(1);
        assertEquals("Account 1 deleted successfully!", actualDeleteAccountResult.getBody());
        assertEquals(200, actualDeleteAccountResult.getStatusCodeValue());
        assertTrue(actualDeleteAccountResult.getHeaders().isEmpty());
    }
}

