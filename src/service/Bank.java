package service;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import models.Account;



public class Bank extends UnicastRemoteObject implements IBank{
    private int accountCounter;
    private ArrayList<Account> accounts;


    public Bank() throws RemoteException {
        super();
        this.accountCounter = 0;
        this.accounts = new ArrayList<Account>();
    }

    @Override
    public Account createAccount() {
        Account newAccount = null;
        try {
            newAccount = new Account(++accountCounter, 0);    
        } catch (Exception e) {
            accountCounter--;
            throw e; 
        }
        return newAccount;
    }

    @Override
    public boolean closeAccount(Account account) {
        boolean op = this.accounts.remove(account); 
        
        if(op){
            this.accountCounter--;
        }
        
        return op;
    }

    @Override
    public Account getAccount(int id) {
        List<Account> accounts = this.accounts.stream()
        .filter(a -> a.getId() == id)
        .collect(Collectors.toList());

        if(accounts.isEmpty()){
            return accounts.get(0); 
        }

        return null;
    }

    @Override
    public boolean withdraw(Account account, float value) {
        float balance = account.getBalance();
        if(balance > value){
            account.setBalance(balance-value);
            return true;
        }
        return false;
    }

    @Override
    public boolean deposit(Account account, float value) {
        account.setBalance(account.getBalance() + value);
        return true;
    }
       
}