package dao;

import lombok.extern.log4j.Log4j2;
import model.UserAccount;

import java.sql.*;
import java.util.List;

@Log4j2
public class UserAccountDAO extends JdbcDAO<UserAccount> implements GenericDAO<UserAccount> {

    protected static final String USER_ACCOUNT_TABLE = "`Servlets_Part2`.`user_account`";
    private Connection connection;

    public UserAccountDAO(Connection connection) {
        super(USER_ACCOUNT_TABLE);
        this.connection = connection;
    }

    protected static final String INSERT_STATEMENT = "INSERT INTO `Servlets_Part2`.`user_account`\n" +
                                                     "(`USER_NAME`,\n" +
                                                     " `GENDER`,\n" +
                                                     " `PASSWORD`)\n" +
                                                     "VALUES (?, ?, ?);";
    protected static final String UPDATE_STATEMENT = "UPDATE `Servlets_Part2`.`user_account`\n" +
                                                     "SET\n" +
                                                     "`USER_NAME` = ?,\n" +
                                                     "`GENDER` = ?,\n" +
                                                     "`PASSWORD` = ?\n" +
                                                     "WHERE `USER_NAME` = ?;\n";
    protected static final String DELETE_STATEMENT = "DELETE FROM `Servlets_Part2`.`user_account`\n" +
                                                     "WHERE `USER_NAME` = ?;\n";
    protected static final String SELECT_CRED_STATEMENT = "SELECT *\n" +
                                                     "FROM `Servlets_Part2`.`user_account`\n" +
                                                     "WHERE user_account.USER_NAME = ?\n" +
                                                     "AND user_account.PASSWORD = ?;\n";
    protected static final String SELECT_USER_STATEMENT = "SELECT *\n" +
                                                     "FROM `Servlets_Part2`.`user_account`\n" +
                                                     "WHERE user_account.USER_NAME = ?;\n";

    @Override
    public UserAccount create(UserAccount userAccount) throws SQLException {
        UserAccount createdUserAccount = null;
        try (
                PreparedStatement ps = connection.prepareStatement(INSERT_STATEMENT);
//                PreparedStatement ps = connection.prepareStatement(INSERT_ERROR_STATEMENT);
        ) {
            ps.setString(1, userAccount.getUserName());
            ps.setString(2, userAccount.getGender());
            ps.setString(3, userAccount.getPassword());
            log.info(INSERT_STATEMENT);
            if(ps.executeUpdate() > 0) {
                createdUserAccount = get(userAccount.getUserName(), userAccount.getPassword());
            }
        }
        return createdUserAccount;
    }

    @Override
    public UserAccount update(UserAccount userAccount) throws SQLException {
        UserAccount updatedUserAccount = null;
        try (
                PreparedStatement ps = connection.prepareStatement(UPDATE_STATEMENT);
        ) {
            ps.setString(1, userAccount.getUserName());
            ps.setString(2, userAccount.getGender());
            ps.setString(3, userAccount.getPassword());
            log.error(UPDATE_STATEMENT);
            log.error(String.format("[%s] users were updated ", ps.executeUpdate()));
            updatedUserAccount = get(userAccount.getUserName(), userAccount.getPassword());
        } catch (SQLException e) {
            String message = String.format("Can't update user [%s]", userAccount);
            log.error(message);
            throw new SQLException(message, e);
        }
        return updatedUserAccount;
    }

    @Override
    public UserAccount get(Object id) throws SQLException {
        String user = (String) id;
        UserAccount userAccount = null;
        try (PreparedStatement ps = connection.prepareStatement(SELECT_USER_STATEMENT)) {
            ps.setString(1, user);
            log.info(SELECT_USER_STATEMENT);
            userAccount = getUserAccount(ps);
        } catch (SQLException e) {
            String message = String.format("Can't get user with username [%s]", user);
            log.error(message);
            throw new SQLException(message, e);
        }
        return userAccount;
    }

    public UserAccount get(String user, String pass) throws SQLException {
        UserAccount userAccount = null;
        try (PreparedStatement ps = connection.prepareStatement(SELECT_CRED_STATEMENT)) {
            ps.setString(1, user);
            ps.setString(2, pass);
            log.info(SELECT_CRED_STATEMENT);
            userAccount = getUserAccount(ps);
        } catch (SQLException e) {
            String message = String.format("Can't get user with username [%s]", user);
            log.error(message);
            throw new SQLException(message, e);
        }
        return userAccount;
    }

    private UserAccount getUserAccount(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        rs.next();
        String userName = rs.getString("USER_NAME");
        String gender = rs.getString("GENDER");
        String password = rs.getString("PASSWORD");
        return new UserAccount(userName, gender, password);
    }

    @Override
    public boolean delete(Object id) {
        new RuntimeException("Not implemented yet");
        return false;
    }

    @Override
    public List<UserAccount> listAll() {
        new RuntimeException("Not implemented yet");
        return null;
    }

}
