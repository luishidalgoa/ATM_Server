package dev.iesfranciscodelosrios.model;

import dev.iesfranciscodelosrios.Connection.Proxy;
import dev.iesfranciscodelosrios.atm_client.model.BankAccount;

import java.net.Socket;

public class BankAccountHandler implements Runnable {

    private Socket socket; // Socket para la conexión con el cliente
    private BankAccount bankAccount; // Cuenta bancaria del cliente asociada a este manejador
    private Proxy proxy; // Objeto Proxy para manejar la comunicación con el cliente

    // Constructor que recibe el socket y la cuenta bancaria del cliente
    public BankAccountHandler(Socket socket, BankAccount bankAccount){
        this.socket = socket;
        this.bankAccount = bankAccount;
    }

    // Método run() implementado de la interfaz Runnable, que se ejecuta cuando se inicia el hilo
    @Override
    public void run() {
        System.out.println("New client connected"); // Imprime un mensaje indicando que un nuevo cliente se ha conectado
        proxy = new Proxy(bankAccount, socket); // Crea un nuevo objeto Proxy para manejar la comunicación con el cliente
        new Thread(proxy).start(); // Inicia un nuevo hilo para ejecutar el objeto Proxy
    }

    // Métodos getter y setter para el socket
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
