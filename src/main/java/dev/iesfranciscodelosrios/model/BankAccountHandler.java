package dev.iesfranciscodelosrios.model;

import dev.iesfranciscodelosrios.Connection.Proxy;
import dev.iesfranciscodelosrios.atm_client.model.BankAccount;

import java.net.Socket;

public class BankAccountHandler implements Runnable {

    private Socket socket;
    private BankAccount bankAccount;
    private Proxy proxy;
    public BankAccountHandler(Socket socket, BankAccount bankAccount){
        this.socket = socket;
        this.bankAccount = bankAccount;
    }
    @Override
    public void run() {
        System.out.println("New client connected");
        proxy = new Proxy(bankAccount,socket);
        new Thread(proxy).start();
    }


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
