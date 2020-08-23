package dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {

    public T create(T t) throws SQLException;

    public T update(T t) throws SQLException;

    public T get(Object id) throws SQLException;

    public boolean delete(Object id) throws SQLException;

    public List<T> listAll() throws SQLException;
}
