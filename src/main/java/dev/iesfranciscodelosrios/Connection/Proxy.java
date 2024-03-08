package dev.iesfranciscodelosrios.Connection;

import dev.iesfranciscodelosrios.DAO.BankAccountDAO;
import dev.iesfranciscodelosrios.atm_client.model.BankAccount;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Proxy implements Runnable{
    private BankAccount bankAccount;
    private Socket clientSocket;

    private ObjectOutputStream outToClient;
    private ObjectInputStream inFromClient;

    public Proxy(BankAccount bankAccount,Socket socket){
        this.bankAccount = bankAccount;
        this.clientSocket = socket;
        try {
            outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
            inFromClient = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void run() {
        while (true){
            try {
                String message = (String) inFromClient.readObject();
                switch (message){
                    case "deposit":
                        BankAccount bankAccount = (BankAccount) inFromClient.readObject();
                        double amount = (double) inFromClient.readObject();
                        outToClient.writeObject(BankAccountDAO.getInstance().deposit(bankAccount,amount) ? "OK" : "Error");
                        break;
                    case "withdraw":
                        bankAccount = (BankAccount) inFromClient.readObject();
                        amount = (double) inFromClient.readObject();
                        outToClient.writeObject(BankAccountDAO.getInstance().withdraw(bankAccount,amount) ? "OK" : "Error");
                        break;
                    case "logout":
                        outToClient.writeObject(true);
                        clientSocket.close();
                        break;
                    case "login":
                        String dni = (String) inFromClient.readObject();
                        int pin = (int) inFromClient.readObject();
                        BankAccount result = BankAccountDAO.getInstance().login(dni,pin);
                        outToClient.writeObject(result);
                        break;
                    case "register":
                        bankAccount = (BankAccount) inFromClient.readObject();
                        outToClient.writeObject(BankAccountDAO.getInstance().register(bankAccount));
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
