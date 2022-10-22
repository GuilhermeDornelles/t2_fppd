import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bank extends UnicastRemoteObject implements IBank {
    private int accountCounter;
    private ArrayList<Account> accounts;

    public Bank() throws RemoteException {
        super();
        this.accountCounter = 0;
        this.accounts = new ArrayList<Account>();
    }

    @Override
    public Account createAccount(String name) {
        Account newAccount = null;
        try {
            newAccount = new Account(++accountCounter, 0, name);
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
        if(accountCounter < 1) return null;
        List<Account> accountsList = this.accounts.stream()
                .filter(a -> a.getname() == name)
                .collect(Collectors.toList());

        if (!accountsList.isEmpty()) {
            return accountsList.get(0);
        }

        return null;
    }

    @Override
    public boolean withdraw(Account account, float value) {
        float balance = account.getBalance();
        if (balance > value) {
            account.withdraw(balance - value);
            return true;
        }
        return false;
    }   

    @Override
    public boolean deposit(Account account, float value) {
        account.deposit(value);
        return true;
    }

}