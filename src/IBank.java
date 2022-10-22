import java.rmi.*;
import java.util.ArrayList;

public interface IBank extends Remote {
    public Account createAccount(String name, Long uniqueKey) throws RemoteException;

    public boolean closeAccount(String name) throws RemoteException;

    public Account getAccount(String name) throws RemoteException;

    public boolean withdraw(String name, float value, Long uniqueKey) throws RemoteException;

    public boolean deposit(String name, float value, Long uniqueKey) throws RemoteException;

    public boolean isValidName(String name) throws RemoteException;

    public ArrayList<Account> getAccounts() throws RemoteException;
}
