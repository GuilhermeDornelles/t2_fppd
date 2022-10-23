import java.rmi.Naming;
import java.util.Arrays;
import java.util.Scanner;

public class ClientAgency {
    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        try {
            // Procura pelo servico do Banco no IP e porta definidos
            IBank bank = (IBank) Naming.lookup("rmi://localhost:1099/BankService");
            
            boolean running = true;
            while(running){
                Account userAccount = null;
                while (userAccount == null) {
                    try {
                        userAccount = ClientBase.loginAndRegister(bank);
                    } catch (Exception e) {
                        System.out.println("Erro de conexão. Tente novamente.");
                        System.out.println(e);
                        return;
                    }
                }

                System.out.println("Conta logada!");

                ClientBase.run(userAccount, bank);

                running = ClientBase.proceedToNewLoginOrNot();
            }
            System.out.println("Cliente finalizado...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        in.close();
    }
}