import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

public class Server {
    public Server(){
        try {
            //Definicao do ip onde o servico ira funcionar
            System.setProperty("java.rmi.server.hostname", "localhost");
            
            //Registro do servico em uma porta
            LocateRegistry.createRegistry(1099);
            //Cria o objeto que implementa os metodos que serao servidos
            IBank bank = new Bank();
            mockData(bank);
            //Coloca na porta registrada o servico da calculadora
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
            bank.createAccount("Arthur Kunzler");
            bank.createAccount("Vinicius Boff");
            bank.createAccount("Guilherme Dornelles");
            bank.createAccount("Giovane Milani");
            bank.createAccount("Marcelo Neves");
            bank.createAccount("Miguel Xavier");
            bank.createAccount("Pedro Carlucci");
            bank.createAccount("Eduardo Ballico");
            for(Account account: bank.getAccounts()){
                System.out.println(account);
            }

        } catch (Exception e) {
            System.out.println("Error mocking data: " + e);
        }
    }
}
