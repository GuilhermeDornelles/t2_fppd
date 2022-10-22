import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ClientAgency {
    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        try {
            // Procura pelo servico do Banco no IP e porta definidos
            IBank bank = (IBank) Naming.lookup("rmi://localhost:1099/BankService");
            boolean running = true;
            Account userAccount = null;
            try {
                userAccount = login(bank);
            } catch (Exception e) {
                System.out.println("Erro de conexão. Tente novamente.");
                System.out.println(e);
                return;
            }
            System.out.println("Conta logada!");

            do {
                System.out.println("1 - Informações da conta");
                System.out.println("2 - Depositar");
                System.out.println("3 - Sacar");
                System.out.println("4 - Consultar saldo");
                System.out.println("0 - sair");
                float value;

                int key = Integer.parseInt(in.nextLine());
                switch (key) {
                    case 1:
                        System.out.println("Informações da conta:");
                        System.out.println(userAccount);
                        break;
                    case 2:
                        System.out.println(userAccount);
                        value = 0;
                        while (value <= 0) {
                            System.out.println("Informe o valor a ser depositado:");
                            value = Float.parseFloat(in.nextLine());
                            System.out.println("VALOR: " + value);
                            if (value <= 0) {
                                System.out.println("Valor inválido!");
                            }
                        }
                        boolean deposit = bank.deposit(userAccount, value);
                        if (deposit) {
                            System.out.println("Deposito efetuado com sucesso! Saldo atual: " + userAccount.getBalance());
                        } else {
                            System.out.println("Erro ao efetuar depósito");
                        }
                        break;
                    case 3:
                        value = 0;
                        while (value <= 0) {
                            System.out.println("Informe o valor a ser sacado:");
                            value = Float.parseFloat(in.nextLine());
                            if (value <= 0) {
                                System.out.println("Valor inválido!");
                            }
                        }
                        boolean withdraw = bank.withdraw(userAccount, value);
                        if (withdraw) {
                            System.out.println("Saque efetuado com sucesso! Saldo atual: " + userAccount.getBalance());
                        } else {
                            System.out.println("Erro ao efetuar depósito");
                        }
                        break;
                    case 4:
                        System.out.println("Saldo da conta:");
                        System.out.println(userAccount.getBalance());
                        break;
                    case 0:
                        running = false;
                        System.out.println("Conexão finalizada");
                        break;
                    default:
                        break;

                }
            } while (running);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Account login(IBank bank) throws RemoteException{
        int esc = 0;
        while (esc != 1 && esc != 2) {
            Utils.printMenu(Arrays.asList("Criar conta", "Manipular conta"));
            esc = Integer.parseInt(in.nextLine());
        }
        Account accountLogged = null;
        if (esc == 1) {
            System.out.println("Informe o nome do titular:");
            String name = "";
            while (name == "") {
                name = in.nextLine();
            }
            System.out.println("Titular da conta: " + name);
            accountLogged = bank.createAccount(name);
            System.out.println("Nova conta criada: \n" + accountLogged);
        }

        if(accountLogged == null){
            System.out.println("Informe o nome do titular da conta:");
        }
        while (accountLogged == null) {
            String name = in.nextLine();
            accountLogged = bank.getAccount(name);
            if (accountLogged == null) {
                System.out.println("Conta não encontrada");
            }
        }
        return accountLogged;
    }
}

