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
        
        Account newAccount = null;
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
    public boolean closeAccount(Account account) {
        boolean op = this.accounts.remove(account);

        if (op) {
            this.accountCounter--;
        }

        return op;
    }

    @Override
    public Account getAccount(String name) {
        List<Account> accounts = this.accounts.stream()
                .filter(a -> a.getname() == name)
                .collect(Collectors.toList());

        if (accounts.isEmpty()) {
            return accounts.get(0);
        }

        return null;
    }

    @Override
    public boolean withdraw(Account account, float value, Long uniqueKey) {
        if(!checkUniqueRequestKey(uniqueKey)) return false;

        float balance = account.getBalance();
        if (balance > value) {
            account.withdraw(balance - value);
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

    private boolean checkUniqueRequestKey(Long newKey){
        for (Long key : uniqueRequestKeys) {
            if (key == newKey) return false;
        }
        return uniqueRequestKeys.add(newKey);
    }

}