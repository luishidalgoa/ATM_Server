package dev.iesfranciscodelosrios;

import dev.iesfranciscodelosrios.model.BankAccount;
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

    static Map<BankAccount, ObjectOutputStream> clients = new HashMap<>();
    static ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);
    public static void main(String[] args) {
        System.out.println("Chat Server is running...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                pool.execute(new BankAccountHandler(serverSocket.accept(), new BankAccount()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}