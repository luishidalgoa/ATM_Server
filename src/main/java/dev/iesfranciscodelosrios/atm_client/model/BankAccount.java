package dev.iesfranciscodelosrios.atm_client.model;

import java.io.Serializable;
import java.util.Objects;

public class BankAccount implements Serializable {

    public String name;
    public String surname;
    public String dni;
    public double balance;
    public int IBAN;
    public int Pin;

    public BankAccount(String name, String surname, String dni, double balance, int IBAN, int Pin) {
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.balance = balance;
        this.IBAN = IBAN;
        this.Pin = Pin;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Double.compare(balance, that.balance) == 0 && IBAN == that.IBAN && Pin == that.Pin && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(dni, that.dni);
    }
    @Override
    public String toString() {
        return "BankAccount{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dni='" + dni + '\'' +
                ", balance=" + balance +
                ", IBAN=" + IBAN +
                ", Pin=" + Pin +
                '}';
    }
}

