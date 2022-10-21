package client;

/**
 *  Cliente da calculadora
 */

import java.rmi.Naming;
import java.util.Scanner;

import models.Account;
import service.Bank;

public class ClientAgency {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try {
            // Procura pelo servico da calculadora no IP e porta definidos
            Bank bank = (Bank) Naming.lookup("rmi://localhost:1099/Server");
            boolean running = true;

            Account account = null;

            System.out.println("Informe o nome do titular conta:");
            while (account == null) {
                account = bank.getAccount(in.nextLine());
                if (account == null) {
                    System.out.println("Conta não encontrada");
                }
            }
            System.out.println("Conta logada!");

            do {
                System.out.println("1 - Abrir conta");
                System.out.println("2 - Informações da conta");
                System.out.println("3 - Depositar");
                System.out.println("4 - Sacar");
                System.out.println("5 - Consultar saldo");
                System.out.println("0 - sair");
                boolean exec = true;
                float value;

                int key = Integer.parseInt(in.nextLine());
                switch (key) {
                    case 1:
                        System.out.println("Informe o nome do titular:");
                        String name = "";
                        while (name == "") {
                            name = in.nextLine();
                        }
                        account = bank.createAccount(name);
                        System.out.println("Nova conta criada: \n" + account);
                        break;
                    case 2:
                        System.out.println("Informações da conta:");
                        System.out.println(account);
                        break;
                    case 3:
                        value = 0;
                        while (value <= 0) {
                            System.out.println("Informe o valor a ser depositado:");
                            value = Float.parseFloat(in.nextLine());
                            if (value <= 0) {
                                System.out.println("Valor inválido!");
                            }
                        }
                        boolean deposit = bank.deposit(account, value);
                        if (deposit) {
                            System.out.println("Deposito efetuado com sucesso! Saldo atual: " + account.getBalance());
                        } else {
                            System.out.println("Erro ao efetuar depósito");
                        }
                        break;
                    case 4:
                        value = 0;
                        while (value <= 0) {
                            System.out.println("Informe o valor a ser sacado:");
                            value = Float.parseFloat(in.nextLine());
                            if (value <= 0) {
                                System.out.println("Valor inválido!");
                            }
                        }
                        boolean withdraw = bank.withdraw(account, value);
                        if (withdraw) {
                            System.out.println("Saque efetuado com sucesso! Saldo atual: " + account.getBalance());
                        } else {
                            System.out.println("Erro ao efetuar depósito");
                        }
                        break;
                    case 5:
                        System.out.println("Saldo da conta:");
                        System.out.println(account.getBalance());
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
}
