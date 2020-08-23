package dao;

import lombok.extern.log4j.Log4j2;
import model.*;

import java.sql.*;
import java.util.*;

@Log4j2
public class ProductDAO extends JdbcDAO<Product> implements GenericDAO<Product> {

    protected static final String USER_ACCOUNT_TABLE = "`Servlets_Part2`.`product`";
    private Connection connection;

    public ProductDAO(Connection connection) {
        super(USER_ACCOUNT_TABLE);
        this.connection = connection;
    }

    protected static final String INSERT_STATEMENT = "INSERT INTO `Servlets_Part2`.`product`\n" +
                                                     "(`CODE`,\n" +
                                                     "`NAME`,\n" +
                                                     "`PRICE`)\n" +
                                                     "VALUES (?, ?, ?);";
    protected static final String UPDATE_STATEMENT = "UPDATE `Servlets_Part2`.`product`\n" +
                                                     "SET\n" +
                                                     "`CODE` = ?,\n" +
                                                     "`NAME` = ?,\n" +
                                                     "`PRICE` = ?\n" +
                                                     "WHERE `CODE` = ?;\n";
    protected static final String DELETE_STATEMENT = "DELETE FROM `Servlets_Part2`.`product`\n" +
                                                     "WHERE `CODE` = ?;\n";
    protected static final String SELECT_STATEMENT = "SELECT *\n" +
                                                     "FROM `Servlets_Part2`.`product`\n" +
                                                     "WHERE `CODE` = ?\n";

    @Override
    public Product create(Product product) throws SQLException {
        Product createdProduct = null;
        try (
                PreparedStatement ps = connection.prepareStatement(INSERT_STATEMENT);
//                PreparedStatement ps = connection.prepareStatement(INSERT_ERROR_STATEMENT);
        ) {
            ps.setString(1, product.getCode());
            ps.setString(2, product.getName());
            ps.setFloat(3, product.getPrice());
            log.info(INSERT_STATEMENT);
            if (ps.executeUpdate() > 0) {
                createdProduct = get(product.getCode());
            }
        }
        return createdProduct;
    }

    @Override
    public Product update(Product product) throws SQLException {
        Product updatedProduct = null;
        try (
                PreparedStatement ps = connection.prepareStatement(UPDATE_STATEMENT);
        ) {
            ps.setString(1, product.getCode());
            ps.setString(2, product.getName());
            ps.setFloat(3, product.getPrice());
            ps.setString(4, product.getCode());
            log.error(UPDATE_STATEMENT);
            log.error(String.format("[%s] products were updated ", ps.executeUpdate()));
            updatedProduct = get(product.getCode());
        } catch (SQLException e) {
            String message = String.format("Can't update product [%s]", product);
            log.error(message);
            throw new SQLException(message, e);
        }
        return updatedProduct;
    }

    @Override
    public Product get(Object id) throws SQLException {
        return get((String) id);
    }

    public Product get(String code) throws SQLException {
        Product product = null;
        try (PreparedStatement ps = connection.prepareStatement(SELECT_STATEMENT)) {
            ps.setString(1, code);
            log.info(SELECT_STATEMENT);
            product = getProduct(ps);
        } catch (SQLException e) {
            String message = String.format("Can't get product with code [%s]", code);
            log.error(message);
            throw new SQLException(message, e);
        }
        return product;
    }

    private Product getProduct(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        rs.next();
        String userName = rs.getString("CODE");
        String gender = rs.getString("NAME");
        Float price = rs.getFloat("PRICE");
        return new Product(userName, gender, price);
    }

    @Override
    public boolean delete(Object id) throws SQLException {
        boolean rowDeleted = false;
        try (
                PreparedStatement ps = connection.prepareStatement(DELETE_STATEMENT);
        ) {
            log.info(DELETE_STATEMENT);
            ps.setString(1, (String) id);
            rowDeleted = ps.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public List<Product> listAll() throws SQLException {
        List<Product> productList = new ArrayList<>();
        try (
                PreparedStatement ps = connection.prepareStatement(SELECT_ALL_STATEMENT);
        ) {
            log.info(SELECT_ALL_STATEMENT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String code = rs.getString("CODE");
                String name = rs.getString("NAME");
                Float price = rs.getFloat("PRICE");
                productList.add(new Product(code, name, price));
            }
        } catch (SQLException e) {
            String message = "Can't select all products list from database";
            log.error(message);
            throw new SQLException(message, e);
        }
        return productList;
    }

}
