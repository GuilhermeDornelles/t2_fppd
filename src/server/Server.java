package server;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import service.Bank;

public class Server {
    public Server(){
        try {
            //Definicao do ip onde o servico ira funcionar
            System.setProperty("java.rmi.server.hostname", "localhost");
            //Registro do servico em uma porta
            LocateRegistry.createRegistry(8080);
            //Cria o objeto que implementa os metodos que serao servidos
            Bank bank = new Bank();
            //Coloca na porta registrada o servico da calculadora
            Naming.bind("Server", (Remote) bank);
            System.out.println("Conexão estabelecida!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
