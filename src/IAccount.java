import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAccount extends Remote {
    public boolean deposit(float value) throws RemoteException;
    public boolean withdraw(float value) throws RemoteException;
    public int getId() throws RemoteException;
    public String getname() throws RemoteException;
    public float getBalance() throws RemoteException;
}
