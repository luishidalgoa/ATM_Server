package dev.iesfranciscodelosrios.Connection;

import dev.iesfranciscodelosrios.DAO.BankAccountDAO;
import dev.iesfranciscodelosrios.atm_client.model.BankAccount;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Proxy implements Runnable {
    private BankAccount bankAccount; // Cuenta bancaria asociada al proxy
    private Socket clientSocket; // Socket del cliente

    private ObjectOutputStream outToClient; // Stream de salida hacia el cliente
    private ObjectInputStream inFromClient; // Stream de entrada desde el cliente

    // Constructor que inicializa el proxy con la cuenta bancaria y el socket del cliente
    public Proxy(BankAccount bankAccount, Socket socket) {
        this.bankAccount = bankAccount;
        this.clientSocket = socket;
        try {
            // Inicializar los streams de entrada y salida
            outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
            inFromClient = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            // Manejar cualquier error de IO
            throw new RuntimeException(e);
        }
    }

    // Método que se ejecuta cuando se inicia el hilo
    @Override
    public void run() {
        while (true) { // Bucle infinito para manejar múltiples solicitudes del cliente
            try {
                // Leer el mensaje enviado por el cliente
                String message = (String) inFromClient.readObject();
                switch (message) {
                    case "deposit":
                        // Procesar una solicitud de depósito
                        BankAccount bankAccount = (BankAccount) inFromClient.readObject();
                        double amount = (double) inFromClient.readObject();
                        // Enviar una respuesta al cliente según el resultado de la operación
                        outToClient.writeObject(BankAccountDAO.getInstance().deposit(bankAccount, amount) ? "OK" : "Error");
                        break;
                    case "withdraw":
                        // Procesar una solicitud de retiro
                        bankAccount = (BankAccount) inFromClient.readObject();
                        amount = (double) inFromClient.readObject();
                        // Enviar una respuesta al cliente según el resultado de la operación
                        outToClient.writeObject(BankAccountDAO.getInstance().withdraw(bankAccount, amount) ? "OK" : "Error");
                        break;
                    case "logout":
                        // Procesar una solicitud de cierre de sesión
                        // Enviar una confirmación al cliente y cerrar el socket
                        outToClient.writeObject(true);
                        clientSocket.close();
                        break;
                    case "login":
                        // Procesar una solicitud de inicio de sesión
                        String dni = (String) inFromClient.readObject();
                        int pin = (int) inFromClient.readObject();
                        // Realizar el inicio de sesión y enviar el resultado al cliente
                        BankAccount result = BankAccountDAO.getInstance().login(dni, pin);
                        outToClient.writeObject(result);
                        break;
                    case "register":
                        // Procesar una solicitud de registro
                        bankAccount = (BankAccount) inFromClient.readObject();
                        // Realizar el registro y enviar el resultado al cliente
                        outToClient.writeObject(BankAccountDAO.getInstance().register(bankAccount));
                        break;
                    default:
                        // Manejar cualquier otro mensaje no reconocido
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                // Manejar cualquier error de IO o de clase no encontrada
                throw new RuntimeException(e);
            }
        }
    }
}
