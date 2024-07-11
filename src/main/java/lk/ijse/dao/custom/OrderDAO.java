package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDao;
import lk.ijse.entity.Order;

import java.sql.SQLException;

public interface OrderDAO extends CrudDao<Order> {
    String getNextOrderId() throws SQLException, ClassNotFoundException;

    String getCurrentId() throws SQLException, ClassNotFoundException;

    int todayOrderCount() throws SQLException, ClassNotFoundException;

    int totalOrderCount() throws SQLException, ClassNotFoundException;

    int getTotalRevenue() throws SQLException, ClassNotFoundException;
}
