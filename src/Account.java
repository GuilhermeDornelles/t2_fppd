import java.io.Serializable;
import java.util.concurrent.Semaphore;

public class Account implements Serializable {
    private Semaphore semaphore;
    private int slotsLimit = 1;
    private int id;
    private String name;
    private float balance;

    public Account(int id, float balance, String name) {
        this.semaphore = new Semaphore(slotsLimit);
        this.id = id;
        this.balance = balance;
        this.name = name;
    }

    public boolean deposit(float value) {
        try {
            semaphore.acquire();
            this.balance += value;
            semaphore.release();
            return true;
        } catch (Exception e) {
            System.out.println("Error in deposit: " + e);
            semaphore.release();
            return false;
        }
    }

    public boolean withdraw(float value) {
        try {
            semaphore.acquire();
            if (this.balance - value >= 0) {
                this.balance = this.balance - value;
                System.out.println("SALDO PÓS SAQUE: " + this.balance);
                semaphore.release();
                return true;
            }
            semaphore.release();
            return false;

        } catch (Exception e) {
            System.out.println("Error in withdraw: " + e);
            return false;
        }
    }

    public int getId() {
        return this.id;
    }

    public String getname() {
        return this.name;
    }

    public float getBalance() {
        return this.balance;
    }

    public String getFormattedBalance(){
        return "Saldo: R$" + this.balance;
    }

    @Override
    public String toString() {
        return String.format("{\n    'id': %d;\n    'titular': %s;\n    'saldo': %.2f\n}", this.id, this.name, this.balance);
    }
}