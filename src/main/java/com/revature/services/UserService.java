package com.revature.services;

import com.revature.Exceptions.InvalidRequestException;
import com.revature.Exceptions.ResourcePersistenceException;
import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.util.List;
import com.revature.util.PoorArrayList;

public class UserService {

    private UserDAO userDAO;
    private AccountDAO accountDAO;

    public UserService(UserDAO userDAO, AccountDAO accountDAO) {
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
    }

    public boolean isValidUser(User user) {
        if(user.getUserID() < 0) return false;
        if(user.getFirstName() == null || user.getFirstName().trim().isEmpty() || user.getFirstName().length() > 50) return false;
        if(user.getEmail() == null || user.getEmail().trim().isEmpty() || user.getEmail().length() > 255) return false;
        if(user.getPassword() == null || user.getPassword().trim().isEmpty()) return false;
        if(user.getLastName() == null || user.getLastName().trim().isEmpty() || user.getLastName().length() > 50) return false;
        if(user.getUserName() == null || user.getUserName().trim().isEmpty() || user.getUserName().length() > 50) return false;
        if(user.getBirthday() == null) return false;

        return user.getAge() >= 0 && user.getAge() <= 200;
    }

    public void registerUser(User user) throws InvalidRequestException, ResourcePersistenceException {

        if (!isValidUser(user)) {
            throw new InvalidRequestException("Invalid user data was provided!");
        }

        if(!userDAO.isUserNameAvailable(user.getUserName())) {
            throw new ResourcePersistenceException("The username provided is already in use.");
        }

        if(!userDAO.isEmailAvailable(user.getEmail())) {
            throw new ResourcePersistenceException("The email provided is already in use.");
        }

        userDAO.saveUserToDataBase(user);

    }

    public Account getAccountByName(List<Account> accounts, String name) {
        Account accountToReturn = null;
        for (Account account: accounts) {
            if(account.getName().equals(name)) {
                accountToReturn = account;
            }
        }

        return  accountToReturn;

    }

    public boolean hasAccountName (User user, String name) {
        List<Account> accounts;
        accounts = accountDAO.getAccountsByUserID(user);
        if (accounts == null || accounts.size() == 0) //prevent potential NPE
            return true;

        for(Account account: accounts) {
            if (account.getName().equals(name))
                return true;
        }
        return false;
    }

    public void openUserAccount(User user, String name, double initialBalance) throws ResourcePersistenceException {
        if (!hasAccountName(user, name)) {
            accountDAO.openAccount(user.getUserID(), name, initialBalance);
        } else {
            throw new ResourcePersistenceException("You already have an account with this name");
        }
    }

    public List<Account> getAccounts (User user) {
        //purely to remove UserDAO dependency in dashboard, may flush out later.
        //With my current application logic, this method cannot receive a null user, however it can return a null value.
        return accountDAO.getAccountsByUserID(user);
    }
}
