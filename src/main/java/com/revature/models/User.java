package com.revature.models;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class User {

    private int userID;
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthday; //TODO calculate age from birthday
    private LocalDate joinedDate;
    private int age;

    public User() {

    }

    public User (int userID, String userName, String password, String email, String firstName, String lastName, LocalDate birthday,
                 int age) {
        this.userID = userID;
        this.setUserName(userName);
        this.setPassword(password);
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setBirthday(birthday);
        this.setJoinedDate(LocalDate.now());
        this.setAge((int) ChronoUnit.YEARS.between(birthday, LocalDate.now()));
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    public int getUserID() {
        return userID;
    }
}
