package service;
import java.rmi.*;

import models.Account;

public interface IBank extends Remote {
    public Account createAccount(String nome);
    public boolean closeAccount(Account account);
    public Account getAccount(String nome);
    public boolean withdraw(Account account, float value);
    public boolean deposit(Account account, float value);
    
}
