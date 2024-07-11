package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.OrderDetailsDAO;
import lk.ijse.entity.OrderProduct;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public boolean save(OrderProduct entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderProduct search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(OrderProduct entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<OrderProduct> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
