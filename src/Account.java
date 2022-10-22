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
        System.out.println("DEPOSITANDO");
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
        String account = "";
        account += "{\n";
        account += "    'id': " + this.id + ",\n";
        account += "    'titular': '" + this.name + "',\n";
        account += "    'saldo': '" + this.balance + "'\n}";
        return account;
    }
}