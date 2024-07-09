package lk.ijse.dao;

import lk.ijse.entity.Customer;
import lk.ijse.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDao<T> extends SuperDao{
    boolean save(T entity) throws SQLException, ClassNotFoundException;

    T  search(String id) throws SQLException, ClassNotFoundException;

    boolean update(T entity) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    public ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

}