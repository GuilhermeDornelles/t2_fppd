import java.rmi.*;

public interface IBank extends Remote {
    public Account createAccount(String nome) throws RemoteException;
    public boolean closeAccount(Account account) throws RemoteException;
    public Account getAccount(String nome) throws RemoteException;
    public boolean withdraw(Account account, float value) throws RemoteException;
    public boolean deposit(Account account, float value) throws RemoteException;
    
}
