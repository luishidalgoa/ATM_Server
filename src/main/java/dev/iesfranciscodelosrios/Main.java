package dev.iesfranciscodelosrios;

import dev.iesfranciscodelosrios.atm_client.model.BankAccount;
import dev.iesfranciscodelosrios.model.BankAccountHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int PORT = 8080;
    private static final int MAX_CLIENTS = 100;

    // Mapa para almacenar los flujos de salida de los clientes
    static Map<BankAccount, ObjectOutputStream> clients = new HashMap<>();
    // Pool de hilos para manejar las conexiones de los clientes
    static ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);

    /**
     * Método principal del servidor de chat.
     * Inicia el servidor, acepta conexiones de clientes y maneja las interacciones con ellos.
     */
    public static void main(String[] args) {
        // Mensaje indicando que el servidor está en funcionamiento
        System.out.println("Chat Server is running...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // Bucle para aceptar conexiones de clientes de forma continua
            while (true) {
                // Acepta la conexión de un cliente y crea un nuevo hilo para manejarlo
                pool.execute(new BankAccountHandler(serverSocket.accept(), new BankAccount()));
            }
        } catch (IOException e) {
            // Captura y manejo de excepciones de E/S
            throw new RuntimeException(e);
        }
    }
}
