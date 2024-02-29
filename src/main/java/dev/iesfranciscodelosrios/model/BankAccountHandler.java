package dev.iesfranciscodelosrios.model;

import java.net.Socket;

public class BankAccountHandler implements Runnable {

    private Socket socket;
    private BankAccount bankAccount;
    public BankAccountHandler(Socket socket, BankAccount bankAccount){
        this.socket = socket;
        this.bankAccount = bankAccount;
    }
    @Override
    public void run() {
        System.out.println("New client connected");
    }


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
