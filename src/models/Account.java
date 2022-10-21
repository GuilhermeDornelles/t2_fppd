package models;

public class Account {
    private int id;
    private String name;
    private float balance;

    public Account(int id, float balance, String name){
        this.id = id;
        this.balance = balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
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

    @Override
    public String toString(){
        String account = "";
        account += "{";
        account += "    'id': " + this.id + ",\n";
        account += "    'nome': '" + this.name + "',\n";
        account += "    'saldo': '" + this.balance + "'\n}";
        return account;
    }
}
