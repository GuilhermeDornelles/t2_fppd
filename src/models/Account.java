package models;

public class Account {
    private int id;
    private String nome;
    private float saldo;

    public Account(int id, float saldo){
        this.id = id;
        this.saldo = saldo;
    }

    public void setBalance(float saldo) {
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public float getBalance() {
        return saldo;
    }
}
