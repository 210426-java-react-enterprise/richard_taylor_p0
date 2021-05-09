package com.revature.daos;

import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * UserDAO
 * <p>
 * Class for interacting directly with the database, specifically to do with user-specific queries.
 * The logic of this application mostly ensures that invalid data doesn't get here.
 */
public class UserDAO {


    public UserDAO() {

    }

    /**
     * Saves user provided data to the database. All prerequisite validation should be done before this method is called.
     *
     * @param user The user object to be saved.
     * @return The user that has been saved to the database (primarily for unit tests in service layer)
     */
    public User save(User user) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "insert into project0.users (username, password, email, first_name, last_name, birthday, joined_date, \"age\")" +
                    " values (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(query, new String[]{"userid"});
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setObject(6, user.getBirthday());
            stmt.setObject(7, LocalDateTime.now());
            stmt.setInt(8, user.getAge());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected != 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                while (rs.next()) {
                    user.setUserID(rs.getInt("userid"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Called when a user logs in, checks provided credentials and uses them to retrieve a user object from the database.
     *
     * @param username The username
     * @param password The password
     * @return A user object if the credentials match, null otherwise.
     */
    public User getUserByUserNameAndPassword(String username, String password) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            User user = null;
            String query = "select * from project0.users where username = ? and password = ?;";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setUserID((rs.getInt("userid")));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setBirthday(rs.getTimestamp("birthday").toLocalDateTime());
                user.setJoinedDate(rs.getTimestamp("joined_date").toLocalDateTime());
                user.setAge(rs.getInt("age"));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks the database to determine if a user already has the username provided.
     *
     * @param username The username to be checked
     * @return False if the username is taken, true if it's not.
     */
    public boolean isUserNameAvailable(String username) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String query = "select * from project0.users where username = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Checks the database to determine if a user already has the email provided.
     *
     * @param email The email to be checked
     * @return False if the email is taken, true if it's not.
     */
    public boolean isEmailAvailable(String email) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String query = "select * from project0.users where email = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
