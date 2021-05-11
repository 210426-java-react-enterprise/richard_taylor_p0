package com.revature.services;

import com.revature.Exceptions.InvalidRequestException;
import com.revature.Exceptions.ResourcePersistenceException;
import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.util.List;

/**
 * UserService
 * <p>
 * This class is where most of the business logic lives. Acts as a layer of validation between the
 * screens and the DAOs.
 */
public class UserService {

    private UserDAO userDAO;
    private AccountDAO accountDAO;

    public UserService(UserDAO userDAO, AccountDAO accountDAO) {
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
    }

    /**
     * Checks every property of a user object to ensure no invalid data is being passed into the database.
     *
     * @param user The user object passed in
     * @return True if the user is valid, false if even one if the fields is not
     */
    public boolean isValidUser(User user) {
        if (user.getUserID() < 0) return false; //should never happen, but better safe than sorry
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty() || user.getFirstName().length() > 50)
            return false;
        if (user.getEmail() == null || user.getEmail().trim().isEmpty() || user.getEmail().length() > 255) return false;
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) return false;
        if (user.getLastName() == null || user.getLastName().trim().isEmpty() || user.getLastName().length() > 50)
            return false;
        if (user.getUserName() == null || user.getUserName().trim().isEmpty() || user.getUserName().length() > 50)
            return false;
        if (user.getBirthday() == null) return false;

        return user.getAge() >= 0 && user.getAge() <= 200;
    }

    /**
     * Checks to see if the user is valid, then checks the username and email provided by the user. If neither are taken,
     * enters the new user into the database.
     *
     * @param user The user object passed in
     * @return The same user object to be used for unit testing
     * @throws InvalidRequestException
     * @throws ResourcePersistenceException
     */
    public User registerUser(User user) throws InvalidRequestException, ResourcePersistenceException {

        if (!isValidUser(user)) {
            throw new InvalidRequestException("Invalid user data was provided!");
        }

        if (!userDAO.isUserNameAvailable(user.getUserName())) {
            throw new ResourcePersistenceException("The username provided is already in use.");
        }

        if (!userDAO.isEmailAvailable(user.getEmail())) {
            throw new ResourcePersistenceException("The email provided is already in use.");
        }

        return userDAO.save(user);

    }

    /**
     * Takes in a list of accounts that belong to the user, and returns the account with the specified name.
     *
     * @param accounts The list of accounts to be passed in
     * @param name     The name to be searched for
     * @return The account with the specified
     * @throws InvalidRequestException if the name specified does not match any accounts
     */
    public Account getAccountByName(List<Account> accounts, String name) throws InvalidRequestException {

        Account accountToReturn = null;
        for (Account account : accounts) {
            if (account.getName().equals(name)) {
                accountToReturn = account;
            }
        }

        if (accountToReturn == null)
            throw new InvalidRequestException("The name specified did not match any accounts");

        return accountToReturn;
    }

    /**
     * Gets a list of accounts by the user from the database. This method is to ensure account names are unique among users.
     * However, 2 separate users can have an account with the same name.
     *
     * @param user The logged in user
     * @param name The account name to be searched for
     * @return true if the user has an account with the name, false if not.
     */
    public boolean hasAccountName(User user, String name) {
        List<Account> accounts;
        accounts = accountDAO.getAccountsByUserID(user);

        if(!(accounts == null)) {
            for (Account account : accounts) {
                if (account.getName().equals(name))
                    return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if the user has an account by the specified name. If not, opens a new account with provided info.
     *
     * @param user           The logged in user
     * @param name           The account name specified
     * @param initialBalance The initial deposit
     * @throws InvalidRequestException If the user already has an account with the specified name
     */
    public void openUserAccount(User user, String name, double initialBalance) throws InvalidRequestException {
        if (!hasAccountName(user, name)) {
            accountDAO.openAccount(user.getUserID(), name, initialBalance);
        } else {
            throw new InvalidRequestException("You already have an account with this name");
        }
    }

    /**
     * Simply calls getAccountsByUserID in the DAO. Done to remove UserDAO dependency in dashboard.
     *
     * @param user The logged in user
     * @return The list of accounts belonging to the user, null if there are none (must be handled)
     */
    public List<Account> getAccounts(User user) {
        return accountDAO.getAccountsByUserID(user);
    }

    public String getUserNameFromAccount(int accountID) throws InvalidRequestException {
        String name = "";
        name = accountDAO.getUserNameFromAccount(accountID);

        if (name.isEmpty())
            throw new InvalidRequestException("The account does not exist");

        return name;
    }

    public boolean recordTransaction(String sender, int senderID, int recipientID, String transactionType, double amount) {
        String recipient = "";
        try {
            recipient = getUserNameFromAccount(recipientID);
        } catch (InvalidRequestException e) {
            e.printStackTrace();
            return false;
        }

        accountDAO.saveTransaction(sender, senderID, recipient, recipientID, transactionType,amount);
        return true;
    }

}
