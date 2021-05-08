package com.revature.services;

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

    public void registerUser(User user) {
        if (!isValidUser(user)) {
            System.err.println("One of more fields did not contain valid input.");
        } else {
            userDAO.saveUserToDataBase(user);
        }
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

    public void openUserAccount(User user, String name, double initialBalance) {
        if (!hasAccountName(user, name)) {
            accountDAO.openAccount(user.getUserID(), name, initialBalance);
        } else {
            System.err.println("You already have an account with this name.");
        }
    }

    public List<Account> getAccounts (User user) {
        //purely to remove UserDAO dependency in dashboard, may flush out later.
        //With my current application logic, this method cannot receive a null user, however it can return a null value.
        return accountDAO.getAccountsByUserID(user);
    }
}
