import java.io.Serializable;

public class Account implements Serializable {
    private int id;
    private String name;
    private float balance;

    public Account(int id, float balance, String name) {
        this.id = id;
        this.balance = balance;
        this.name = name;
    }

    public boolean deposit(float value) {
        this.balance += value;
        return true;
    }

    public boolean withdraw(float value) {
        if (this.balance - value >= 0) {
            this.balance = this.balance - value;
            System.out.println("SALDO PÓS SAQUE: " + this.balance);
            return true;
        }
        return false;
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