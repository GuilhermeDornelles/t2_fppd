import java.rmi.Naming;
import java.util.Arrays;
import java.util.Scanner;

public class ClientAtm {
    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        try {
            // Procura pelo servico do Banco no IP e porta definidos
            IBank bank = (IBank) Naming.lookup("rmi://localhost:1099/BankService");
            boolean running = true;
            Account userAccount = null;
            while (userAccount == null) {
                try {
                    userAccount = ClientBase.login(bank);
                } catch (Exception e) {
                    System.out.println("Erro de conexão. Tente novamente.");
                    System.out.println(e);
                    return;
                }
            }
            System.out.println("Conta logada!");

            do {
                Utils.printMenu(Arrays.asList("Informações da conta", "Depositar", "Sacar", "Consultar saldo"), true);

                float value;

                int key = Integer.parseInt(in.nextLine());
                switch (key) {
                    case 1:
                        System.out.println("Informações da conta:");
                        userAccount = ClientBase.fetchAccount(userAccount.getname(), bank);
                        System.out.println(userAccount);
                        break;
                    case 2:
                        value = 0;
                        while (value <= 0) {
                            System.out.println("Informe o valor a ser depositado:");
                            value = Float.parseFloat(in.nextLine());
                            System.out.println("VALOR: " + value);
                            if (value <= 0) {
                                System.out.println("Valor inválido!");
                            }
                        }
                        boolean deposit = ClientBase.deposit(userAccount.getname(), bank, value);
                        userAccount = ClientBase.fetchAccount(userAccount.getname(), bank);
                        if (deposit) {
                            System.out.println(
                                    "Deposito efetuado com sucesso!\n" + userAccount.getFormattedBalance());
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
                        boolean withdraw = ClientBase.withdraw(userAccount.getname(), bank, value);
                        userAccount = ClientBase.fetchAccount(userAccount.getname(), bank);
                        if (withdraw) {
                            System.out.println(
                                    "Saque efetuado com sucesso!\n" + userAccount.getFormattedBalance());
                        } else {
                            System.out.println("Erro ao efetuar depósito");
                        }
                        break;
                    case 4:
                        userAccount = ClientBase.fetchAccount(userAccount.getname(), bank);
                        System.out.println(userAccount.getFormattedBalance());
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
        in.close();
    }
}