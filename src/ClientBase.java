import java.util.Arrays;
import java.util.Scanner;

public class ClientBase {
    private static Scanner in = new Scanner(System.in);

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

                accountLogged = bank.createAccount(name, Utils.createUniqueKey(String.format("%s", name)));
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
            boolean deposit = bank.deposit(name, value, Utils.createUniqueKey(String.format("%s %f", name, value)));
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
            boolean withdraw = bank.withdraw(name, value, Utils.createUniqueKey(String.format("%s %f", name, value)));
            if (withdraw) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static Account getAccountInfo(Account userAcc, IBank bank){
        System.out.println("Informações da conta:");
        Account userAccount = ClientBase.fetchAccount(userAcc.getname(), bank);
        System.out.println(userAccount);
        return userAccount;
    }

    public static Account executeDeposit(Account userAcc, IBank bank){
        float value = 0;
        while (value <= 0) {
            System.out.println("Informe o valor a ser depositado:");
            value = Float.parseFloat(in.nextLine());
            System.out.println("VALOR: " + value);
            if (value <= 0) {
                System.out.println("Valor inválido!");
            }
        }
        boolean deposit = ClientBase.deposit(userAcc.getname(), bank, value);
        Account userAccount = ClientBase.fetchAccount(userAcc.getname(), bank);
        if (deposit) {
            System.out.println(
                    "Deposito efetuado com sucesso!\n" + userAccount.getFormattedBalance());
        } else {
            System.out.println("Erro ao efetuar depósito");
        }
        return userAccount;
    }

    public static Account executeWithdraw(Account userAcc, IBank bank){
        float value = 0;
        while (value <= 0) {
            System.out.println("Informe o valor a ser sacado:");
            value = Float.parseFloat(in.nextLine());
            if (value <= 0) {
                System.out.println("Valor inválido!");
            }
        }
        boolean withdraw = ClientBase.withdraw(userAcc.getname(), bank, value);
        Account userAccount = ClientBase.fetchAccount(userAcc.getname(), bank);
        if (withdraw) {
            System.out.println(
                    "Saque efetuado com sucesso!\n" + userAccount.getFormattedBalance());
        } else {
            System.out.println("Erro ao efetuar saque");
        }
        return userAccount;
    }

    public static Account getBalance(Account userAcc, IBank bank){
        Account userAccount = ClientBase.fetchAccount(userAcc.getname(), bank);
        System.out.println(userAccount.getFormattedBalance());
        return userAccount;
    }

    public static boolean run(Account userAccount, IBank bank){
        boolean running = true;
        do {
            Utils.printMenu(Arrays.asList("Informações da conta", "Depositar", "Sacar", "Consultar saldo"), true);
            
            int key = Integer.parseInt(in.nextLine());
            switch (key) {
                case 1:
                userAccount = ClientBase.getAccountInfo(userAccount, bank);
                break;
            case 2:
                userAccount = ClientBase.executeDeposit(userAccount, bank);
                break;
            case 3:
                userAccount = ClientBase.executeWithdraw(userAccount, bank);
                break;
            case 4:
                userAccount = ClientBase.getBalance(userAccount, bank);
                break;
            case 0:
                running = false;
                System.out.println("Saindo da conta...");
                break;
            default:
                running = false;
                System.out.println("Saindo da conta...");
                break;
            }
        } while (running);
        return running;
    }

    public static boolean proceedToNewLoginOrNot(){
        System.out.println("Deseja fazer login novamente?");
        String input = in.nextLine().trim().substring(0, 1);
        return input.equalsIgnoreCase("s") || input.equalsIgnoreCase("y") || input.equalsIgnoreCase("1");
    }
}