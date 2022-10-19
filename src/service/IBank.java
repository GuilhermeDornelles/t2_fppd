package service;
import java.rmi.*;

import models.Account;

public interface IBank extends Remote {
    public Account createAccount();
    public boolean closeAccount(Account account);
    public Account getAccount(int id);
    public boolean withdraw(Account account, float value);
    public boolean deposit(Account account, float value);
    
}
