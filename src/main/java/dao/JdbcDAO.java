package dao;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2
public abstract class JdbcDAO<T> {

    protected String SELECT_ALL_STATEMENT;
    protected String SELECT_MAX_ID_STATEMENT;
    protected String SELECT_BY_ID_STATEMENT;

    public JdbcDAO(String table) {
        this.SELECT_ALL_STATEMENT = String.format("SELECT * FROM %s;\n", table);
        this.SELECT_MAX_ID_STATEMENT = String.format("SELECT MAX(id) FROM %s;\n", table);
        this.SELECT_BY_ID_STATEMENT = String.format("SELECT *\n" +
                "FROM %s\n" +
                "WHERE users.id = ?;\n", table);

    }

    protected int toInt(Object id) {
        try {
            return Integer.parseInt(String.valueOf(id));
        } catch (Exception e) {
            log.error(String.format("Can't parse object [%s] to id", id));
            throw new RuntimeException(e);
        }
    }

    protected int getMaxId(Connection connection) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_MAX_ID_STATEMENT)) {
            log.info(ps);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            log.error(String.format("Can't execute [%s]", SELECT_MAX_ID_STATEMENT));
            printSQLException(e);
            return -1;
        }
    }

    protected void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}
