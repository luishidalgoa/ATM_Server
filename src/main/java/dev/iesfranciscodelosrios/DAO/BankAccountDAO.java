package dev.iesfranciscodelosrios.DAO;

import dev.iesfranciscodelosrios.Connection.ConnectionData;
import dev.iesfranciscodelosrios.atm_client.model.BankAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankAccountDAO {
    private static BankAccountDAO _instance;
    private BankAccountDAO(){}

    public boolean deposit(BankAccount bankAccount, double amount){
        Connection conn = ConnectionData.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE bankaccount SET balance = balance + ? WHERE dni = ?");
            ps.setDouble(1, amount);
            ps.setString(2, bankAccount.dni);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BankAccount register(BankAccount bankAccount){
        Connection conn = ConnectionData.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO bankaccount (name, surname, dni, balance, pin) VALUES (?,?,?,?,?)");
            ps.setString(1, bankAccount.getName());
            ps.setString(2, bankAccount.getSurname());
            ps.setString(3, bankAccount.getDni());
            ps.setDouble(4, bankAccount.getBalance());
            ps.setInt(5, bankAccount.getPin());
            ps.executeUpdate();
            return login(bankAccount.getDni(),bankAccount.getPin());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BankAccount login(String dni, int pin){
        Connection conn = ConnectionData.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM bankaccount WHERE dni = ? AND pin = ?");
            ps.setString(1, dni);
            ps.setInt(2, pin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                BankAccount bankAccount = new BankAccount();
                bankAccount.setIBAN(rs.getInt("IBAN"));
                bankAccount.setName(rs.getString("name"));
                bankAccount.setSurname(rs.getString("surname"));
                bankAccount.setDni(rs.getString("dni"));
                bankAccount.setBalance(rs.getDouble("balance"));
                bankAccount.setPin(rs.getInt("pin"));
                return bankAccount;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean withdraw(BankAccount bankAccount, double amount){
        Connection conn = ConnectionData.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE bankaccount SET balance = balance - ? WHERE IBAN = ?");
            ps.setDouble(1, amount);
            ps.setInt(2, bankAccount.getIBAN());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static BankAccountDAO getInstance(){
        if(_instance == null){
            _instance = new BankAccountDAO();
        }
        return _instance;
    }
}
