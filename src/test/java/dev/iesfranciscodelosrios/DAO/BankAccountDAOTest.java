package dev.iesfranciscodelosrios.DAO;

import dev.iesfranciscodelosrios.atm_client.model.BankAccount;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountDAOTest {

    @org.junit.jupiter.api.Test
    void deposit() {
        boolean aux= BankAccountDAO.getInstance().deposit(new BankAccount("pepe","pepe","12345678",1000,5,1),1000);
        System.out.println(aux);
        //comprobamos que el deposito funciona
        assertTrue(aux);
    }

    /*@org.junit.jupiter.api.Test
    void register() {
        BankAccount aux= BankAccountDAO.getInstance().register(new BankAccount("pepe","pepe","12345678",1000,1,1));
        System.out.println(aux);
        //comprobamos que el registro funciona
        assertNotNull(aux);
    }*/

    @org.junit.jupiter.api.Test
    void login() {
       BankAccount aux= BankAccountDAO.getInstance().login("1",1);
       System.out.println(aux);
       //comprobamos que el login funciona
       assertNotNull(aux);
    }

    @org.junit.jupiter.api.Test
    void withdraw() {
    }

    @org.junit.jupiter.api.Test
    void getInstance() {
    }
}