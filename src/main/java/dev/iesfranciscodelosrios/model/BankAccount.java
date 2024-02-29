package dev.iesfranciscodelosrios.model;

import java.net.Socket;

public class BankAccount {

    public String name;
    public String surname;
    public String dni;
    public double balance;
    public int IBAN;
    public int Pin;

    public BankAccount(String name, String surname, String dni, double balance, int IBAN, int pin) {
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.balance = balance;
        this.IBAN = IBAN;
        Pin = pin;
    }

    public BankAccount() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getIBAN() {
        return IBAN;
    }

    public void setIBAN(int IBAN) {
        this.IBAN = IBAN;
    }

    public int getPin() {
        return Pin;
    }

    public void setPin(int pin) {
        Pin = pin;
    }
}

