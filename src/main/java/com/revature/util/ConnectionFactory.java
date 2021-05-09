package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ConnectionFactory
 * <p>
 * This class immediately loads the required database driver into memory, and cannot be instantiated outside.
 * Returns a connection to the database via a method.
 */
public class ConnectionFactory {

    private static ConnectionFactory connectionFactory; //lazy singleton
    private Properties props = new Properties();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ConnectionFactory() {
        try {
            props.load(new FileReader("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns an instance of itself, only instantiated once.
     *
     * @return The instance of ConnectionFactory
     */
    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;
    }

    /**
     * Returns a connection to the database.
     *
     * @return A Connection object.
     */
    public Connection getConnection() {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(
                    props.getProperty("host-url"),
                    props.getProperty("login"),
                    props.getProperty("password"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;

    }
}
