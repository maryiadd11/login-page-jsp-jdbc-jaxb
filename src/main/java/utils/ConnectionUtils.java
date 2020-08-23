package utils;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j2
public class ConnectionUtils {

    protected static final String MY_SQL_DRIVER_URL = "jdbc:mysql://localhost/Servlets_Part2";
    protected static final String MY_SQL_USERNAME = "root";
    protected static final String MY_SQL_PASSWORD = "MEO13$$$rnyk";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(MY_SQL_DRIVER_URL, MY_SQL_USERNAME, MY_SQL_PASSWORD);
        } catch (ClassNotFoundException e) {
            log.error("Can't find class for com.mysql.cj.jdbc.Driver");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            log.error("Can't create connection to Servlets_Part2 database");
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void closeQuietly(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
        }
    }

    public static void rollbackQuietly(Connection connection) {
        try {
            connection.rollback();
        } catch (Exception e) {
        }
    }
}
