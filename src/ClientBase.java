import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ClientBase {
    private static Scanner in = new Scanner(System.in);

    // public static void main(String[] args) {
    // try {
    // // Procura pelo servico do Banco no IP e porta definidos
    // IBank bank = (IBank) Naming.lookup("rmi://localhost:1099/BankService");
    // boolean running = true;
    // Account userAccount = null;
    // while (userAccount == null) {
    // try {
    // userAccount = login(bank);
    // } catch (Exception e) {
    // System.out.println("Erro de conexão. Tente novamente.");
    // System.out.println(e);
    // return;
    // }
    // }
    // System.out.println("Conta logada!");

    // do {
    // Utils.printMenu(Arrays.asList("Informações da conta", "Depositar", "Sacar",
    // "Consultar saldo"), true);

    // float value;

    // int key = Integer.parseInt(in.nextLine());
    // switch (key) {
    // case 1:
    // System.out.println("Informações da conta:");
    // System.out.println(userAccount);
    // break;
    // case 2:
    // value = 0;
    // while (value <= 0) {
    // System.out.println("Informe o valor a ser depositado:");
    // value = Float.parseFloat(in.nextLine());
    // System.out.println("VALOR: " + value);
    // if (value <= 0) {
    // System.out.println("Valor inválido!");
    // }
    // }
    // boolean deposit = deposit(userAccount.getname(), bank, value);
    // userAccount = fetchAccount(userAccount.getname(), bank);
    // if (deposit) {
    // System.out.println(
    // "Deposito efetuado com sucesso!\n" + userAccount.getFormattedBalance());
    // } else {
    // System.out.println("Erro ao efetuar depósito");
    // }
    // break;
    // case 3:
    // value = 0;
    // while (value <= 0) {
    // System.out.println("Informe o valor a ser sacado:");
    // value = Float.parseFloat(in.nextLine());
    // if (value <= 0) {
    // System.out.println("Valor inválido!");
    // }
    // }
    // boolean withdraw = withdraw(userAccount.getname(), bank, value);
    // userAccount = fetchAccount(userAccount.getname(), bank);
    // if (withdraw) {
    // System.out.println(
    // "Saque efetuado com sucesso!\n" + userAccount.getFormattedBalance());
    // } else {
    // System.out.println("Erro ao efetuar depósito");
    // }
    // break;
    // case 4:
    // userAccount = fetchAccount(userAccount.getname(), bank);
    // System.out.println(userAccount.getFormattedBalance());
    // break;
    // case 0:
    // running = false;
    // System.out.println("Conexão finalizada");
    // break;
    // default:
    // break;

    // }
    // } while (running);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    public static Account login(IBank bank) {
        try {
            Account account = null;
            System.out.println("Informe o nome do titular da conta:");
            String name = in.nextLine();
            account = bank.getAccount(name);
            if (account == null) {
                System.out.println("Conta não encontrada");
            }
            return account;
        } catch (Exception e) {
            System.out.println("ERROR IN LOGIN: " + e);
            return null;
        }

    }

    public static Account loginAndRegister(IBank bank) {
        try {
            int esc = 0;
            while (esc != 1 && esc != 2) {
                Utils.printMenu(Arrays.asList("Criar conta", "Manipular conta"), false);
                esc = Integer.parseInt(in.nextLine());
            }
            Account accountLogged = null;
            if (esc == 1) {
                System.out.println("Informe o nome do titular:");
                String name = "";
                while (name == "") {
                    name = in.nextLine();
                }

                accountLogged = bank.createAccount(name);
                if (accountLogged != null) {
                    System.out.println("Nova conta criada: \n" + accountLogged);
                    System.out.println("Titular da conta: " + name);
                } else {
                    System.out.println("Titular já possui conta!");
                }
                return accountLogged;
            }

            System.out.println("Informe o nome do titular da conta:");
            String name = in.nextLine();
            accountLogged = bank.getAccount(name);
            if (accountLogged == null) {
                System.out.println("Conta não encontrada");
            }
            return accountLogged;
        } catch (Exception e) {
            System.out.println("ERROR IN LOGIN OR REGISTER " + e);
            return null;
        }

    }

    public static Account fetchAccount(String name, IBank bank) {
        try {
            return bank.getAccount(name);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static boolean deposit(String name, IBank bank, float value) {
        try {
            boolean deposit = bank.deposit(name, value);
            if (deposit) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean withdraw(String name, IBank bank, float value) {
        try {
            boolean withdraw = bank.withdraw(name, value);
            if (withdraw) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
