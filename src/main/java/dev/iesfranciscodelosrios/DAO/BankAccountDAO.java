package dev.iesfranciscodelosrios.DAO;

import dev.iesfranciscodelosrios.Connection.ConnectionData;
import dev.iesfranciscodelosrios.atm_client.model.BankAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankAccountDAO {
    private static BankAccountDAO _instance;

    // Constructor privado para aplicar el patrón Singleton
    private BankAccountDAO(){}

    /**
     * Método para realizar un depósito en una cuenta bancaria.
     * @param bankAccount La cuenta bancaria en la que se realizará el depósito.
     * @param amount El monto a depositar.
     * @return true si el depósito se realizó con éxito, false en caso contrario.
     */
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

    /**
     * Método para registrar una nueva cuenta bancaria en la base de datos.
     * @param bankAccount La cuenta bancaria a registrar.
     * @return La cuenta bancaria recién registrada.
     */
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

    /**
     * Método para iniciar sesión en una cuenta bancaria.
     * @param dni El DNI asociado a la cuenta bancaria.
     * @param pin El PIN de la cuenta bancaria.
     * @return La cuenta bancaria asociada al DNI y PIN proporcionados.
     */
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

    /**
     * Método para realizar un retiro de una cuenta bancaria.
     * @param bankAccount La cuenta bancaria de la que se realizará el retiro.
     * @param amount El monto a retirar.
     * @return true si el retiro se realizó con éxito, false en caso contrario.
     */
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

    /**
     * Método estático para obtener una instancia única de la clase BankAccountDAO (patrón Singleton).
     * @return La instancia única de BankAccountDAO.
     */
    public static BankAccountDAO getInstance(){
        if(_instance == null){
            _instance = new BankAccountDAO();
        }
        return _instance;
    }
}
