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
}
