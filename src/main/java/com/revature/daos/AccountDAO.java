package com.revature.daos;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;
import com.revature.util.List;
import com.revature.util.PoorArrayList;

import java.sql.*;
import java.time.LocalDateTime;

public class AccountDAO {

    public AccountDAO() {

    }
    public List<Account> getAccountsByUserID(User user) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            List<Account> accounts = new PoorArrayList<>();
            String query = "select accountid, account_name, balance from project0.users_account\n" +
                    "join project0.users\n" +
                    "using (userid)\n" +
                    "join project0.account\n" +
                    "using (accountid)\n" +
                    "where project0.users.userid = ?;";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, user.getUserID());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setAccountID(rs.getInt("accountid"));
                account.setName(rs.getString("account_name"));
                account.setBalance(rs.getDouble("balance"));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void openAccount(int userID, String accountName, double initialBalance) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "insert into project0.account (account_name, balance)\n" +
                    "values (?, ?);";
            int newID = 0;
            PreparedStatement stmt = conn.prepareStatement(query, new String[] {"accountid"});
            stmt.setString(1, accountName);
            stmt.setDouble(2, initialBalance);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected != 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                while (rs.next()) {
                    newID = rs.getInt("accountid");
                }
            }
            linkAccount(userID, newID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void linkAccount(int userID, int accountID) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "insert into project0.users_account (accountid, userid)\n" +
                    "values (?, ?);";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, accountID);
            stmt.setInt(2, userID);
            int result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveBalance(double newBalance, int accountID) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "update project0.account set balance = ? where accountid = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDouble(1, newBalance);
            stmt.setInt(2, accountID);

            int rowsUpdated = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
