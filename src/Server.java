import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

public class Server {
    public Server() {
        try {
            System.setProperty("java.rmi.server.hostname", "localhost");

            LocateRegistry.createRegistry(1099);
            IBank bank = new Bank();
            mockData(bank);
            Naming.bind("BankService", (Remote) bank);
            System.out.println("Conexão estabelecida!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }

    public void mockData(IBank bank) {
        try {
            System.out.println("Mocking data:");
            bank.createAccount("Arthur Kunzler", Utils.createUniqueKey("Arthur Kunzler"));
            bank.createAccount("Vinicius Boff", Utils.createUniqueKey("Vinicius Boff"));
            bank.createAccount("Guilherme Dornelles", Utils.createUniqueKey("Guilherme Dornelles"));
            bank.createAccount("Giovane Milani", Utils.createUniqueKey("Giovane Milani"));
            bank.createAccount("Marcelo Neves", Utils.createUniqueKey("Marcelo Neves"));
            bank.createAccount("Miguel Xavier", Utils.createUniqueKey("Miguel Xavier"));
            bank.createAccount("Pedro Carlucci", Utils.createUniqueKey("Pedro Carlucci"));
            bank.createAccount("Eduardo Ballico", Utils.createUniqueKey("Eduardo Ballico"));
            for (Account account : bank.getAccounts()) {
                System.out.println(account);
            }

        } catch (Exception e) {
            System.out.println("Error mocking data: " + e);
        }
    }
}