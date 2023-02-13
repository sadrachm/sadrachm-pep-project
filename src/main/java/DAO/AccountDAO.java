package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    public List<Account> getAllAccounts() {
        Connection conn = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM account";
            PreparedStatement prepState = conn.prepareStatement(sql);
            ResultSet result = prepState.executeQuery();
            while (result.next()) {
                Account account = new Account(result.getInt("account_id"), result.getString("username"), result.getString("password"));
                accounts.add(account);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account insertAccount(Account account) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?,?)";
            PreparedStatement prepState = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepState.setString(1, account.getUsername());
            prepState.setString(2, account.getPassword());
            prepState.executeUpdate();
            ResultSet result = prepState.getGeneratedKeys();
            if (result.next()) {
                int generated_account_id = (int) result.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account getAccountByUserAndId(Account account) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username=? AND password=?";
            PreparedStatement prepState = conn.prepareStatement(sql);
            prepState.setString(1, account.getUsername());
            prepState.setString(2, account.getPassword());
            ResultSet result = prepState.executeQuery();
            if (result.next()) {
                int x = result.getInt("account_id");
                String a = result.getString("username");
                String b = result.getString("password");
                return new Account(x, a, b);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account getAccountByID(int ID) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id=?";
            PreparedStatement prepState = conn.prepareStatement(sql);
            prepState.setInt(1, ID);
            ResultSet result = prepState.executeQuery();
            if (result.next()) {
                return new Account(result.getInt("account_id"), result.getString("username"), result.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    

}

