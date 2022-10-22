import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bank extends UnicastRemoteObject implements IBank {

    private int accountCounter;
    private ArrayList<Account> accounts;
    private ArrayList<Long> uniqueRequestKeys;

    public Bank() throws RemoteException {
        super();
        this.accountCounter = 0;
        this.accounts = new ArrayList<Account>();
        this.uniqueRequestKeys = new ArrayList<Long>();
    }

    @Override
    public Account createAccount(String name, Long uniqueKey) {
        if(!checkUniqueRequestKey(uniqueKey)) return null;
        
        if (!isValidName(name))
            return null;
        Account newAccount = null;
        name = name.toUpperCase();
        try {
            newAccount = new Account(++accountCounter, 0, name);
            this.accounts.add(newAccount);
        } catch (Exception e) {
            accountCounter--;
            throw e;
        }
        return newAccount;
    }

    @Override
    public boolean closeAccount(String name) {
        Account account = this.getAccount(name);
        boolean op = this.accounts.remove(account);

        if (op) {
            this.accountCounter--;
        }

        return op;
    }

    @Override
    public Account getAccount(String name) {
        if (accountCounter < 1)
            return null;
        List<Account> accountsList = this.accounts.stream()
                .filter(a -> a.getname().toLowerCase().equals(name.toLowerCase()))
                .collect(Collectors.toList());

        if (!accountsList.isEmpty()) {
            return accounts.get(0);
        }

        return null;
    }

    @Override
    public boolean isValidName(String name) {
        if (getAccount(name) == null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean withdraw(String name, float value, Long uniqueKey) {
        if(!checkUniqueRequestKey(uniqueKey)) return false;

        Account account = this.getAccount(name);
        float balance = account.getBalance();
        if (balance > value) {
            account.withdraw(value);
            return true;
        }
        return false;
    }

    @Override
    public boolean deposit(Account account, float value, Long uniqueKey) {
        if(!checkUniqueRequestKey(uniqueKey)) return false;

        account.deposit(value);
        return true;
    }

    @Override
    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }
}