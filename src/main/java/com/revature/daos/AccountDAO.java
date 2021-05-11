package com.revature.daos;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;
import com.revature.util.List;
import com.revature.util.PoorArrayList;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * AccountDAO
 * <p>
 * Class for interacting directly with the database, specifically to do with account-specific queries.
 * The logic of this application mostly ensures that invalid data doesn't get here.
 */
public class AccountDAO {

    public AccountDAO() {

    }

    /**
     * Returns a list of all the accounts that belong to a specified user.
     *
     * @param user The ID of the user
     * @return A list of all the accounts belonging to the user
     */
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
        return new PoorArrayList<>();
    }

    /**
     * Takes input from the user, and opens an account with it.
     *
     * @param userID         The ID of the logged in user
     * @param accountName    The account name the user specifies (unique to users)
     * @param initialBalance The initial deposit (can be zero, if the user wishes)
     */
    public void openAccount(int userID, String accountName, double initialBalance) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            int newID = 0;
            String query = "insert into project0.account (account_name, balance)\n" +
                    "values (?, ?);";
            PreparedStatement stmt = conn.prepareStatement(query, new String[]{"accountid"});

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

    /**
     * Private method only called when openAccount is called, runs a separate query to
     * link the user to the account using the users_account junction table.
     * <p>
     * TODO: figure out how to do this using triggers in the database.
     *
     * @param userID    the user ID
     * @param accountID the account ID
     */
    private void linkAccount(int userID, int accountID) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
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

    /**
     * Adds money to the account balance.
     *
     * @param amount    The amount to deposit
     * @param accountID the ID of the account
     */
    public void addBalance(double amount, int accountID) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "update project0.account set balance = balance + ? where accountid = ?";

            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setDouble(1, amount);
            stmt.setInt(2, accountID);

            int rowsUpdated = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Subtracts money from the account balance. No need to check for negative or high values here
     * as that's done elsewhere before this method is called.
     *
     * @param amount    The amount to withdraw or transfer
     * @param accountID The ID of the account
     */
    public void subtractBalance(double amount, int accountID) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "update project0.account set balance = balance - ? where accountid = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountID);

            int rowsUpdated = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks to see if the account exists.
     *
     * @param accountID The account ID to be checked
     * @return True if the account exists, false if not.
     */
    public boolean accountExists(int accountID) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "select * from project0.account where accountid = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, accountID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void saveTransaction(String sender, int senderID, String recipient, int recipientID, String transactionType, double amount) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "insert into project0.transactions (sender_name, sender_account, recipient_name, recipient_account, amount, transaction_type, transaction_date)\n" +
                    "values (?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, sender);
            stmt.setInt(2, senderID);
            stmt.setString(3, recipient);
            stmt.setInt(4, recipientID);
            stmt.setDouble(5, amount);
            stmt.setString(6, transactionType);
            stmt.setObject(7, LocalDateTime.now());

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated != 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                while (rs.next()) {
                    Transaction transaction = new Transaction(); //TODO do something here idk
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getTransactionsByUsername(String username) {

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            List<Transaction> transactions = new PoorArrayList<>();
            String query = "select * from project0.transactions\n" +
                    "where sender_name = ?\n" +
                    "or recipient_name = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, username);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionID(rs.getInt("transactionid"));
                transaction.setSender(rs.getString("sender_name"));
                transaction.setSenderAccount(rs.getInt("sender_account"));
                transaction.setRecipient(rs.getString("recipient_name"));
                transaction.setRecipientAccount(rs.getInt("recipient_account"));
                transaction.setTransactionType(rs.getString("transaction_type"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setDate(rs.getTimestamp("transaction_date").toLocalDateTime());
                transactions.add(transaction);
            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserNameFromAccount(int accountID) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "select username\n" +
                    "from project0.users_account ua\n" +
                    "join project0.users\n" +
                    "using(userid)\n" +
                    "join project0.account\n" +
                    "using(accountid) where accountid = ?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, accountID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

}


