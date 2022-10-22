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
}