package com.revature.daos;

import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UserDAO {

    //private

    public UserDAO() {

    }

    public void saveUserToDataBase(User user) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String query = "insert into project0.users (username, password, email, first_name, last_name, birthday, joined_date, \"age\")" +
                    " values (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(query, new String[] {"userid"});
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setObject(6, user.getBirthday());
            stmt.setObject(7, LocalDateTime.now());
            stmt.setInt(8, user.getAge());
            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected != 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                while (rs.next()) {
                    user.setUserID(rs.getInt("userid"));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserByUserNameAndPassword(String username, String password) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            User user = null;
            String query = "select * from project0.users where username = ? and password = ?;";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
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
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public
}
