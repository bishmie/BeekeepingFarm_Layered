package lk.ijse.dao.custom.impl;

import lk.ijse.dao.CrudDao;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.Product;
import lk.ijse.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements CrudDao<User> {
    @Override
    public boolean save(User entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public User search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
