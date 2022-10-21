import java.io.Serializable;

public class Account implements IAccount {
    private int id;
    private String name;
    private float balance;

    public Account(int id, float balance, String name) {
        this.id = id;
        this.balance = balance;
        this.name = name;
    }

    @Override
    public boolean deposit(float value) {
        this.balance += value;
        return true;
    }

    @Override
    public boolean withdraw(float value) {
        if(this.balance - value >=0){
            this.balance -= value;
            return true;
        }
        return false;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getname() {
        return this.name;
    }

    @Override
    public float getBalance() {
        return this.balance;
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
