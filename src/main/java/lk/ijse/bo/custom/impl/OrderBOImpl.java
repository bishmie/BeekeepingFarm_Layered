package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.OrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.OrderDAO;

import java.sql.SQLException;

public class OrderBOImpl implements OrderBO {
    OrderDAO orderDAO=(OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    @Override
    public int getTodayOrderCount() throws SQLException, ClassNotFoundException {
        return orderDAO.todayOrderCount();
    }

    @Override
    public int getTotalOrderCount() throws SQLException, ClassNotFoundException {
        return orderDAO.totalOrderCount();
    }

    @Override
    public int getTotalRevenue() throws SQLException, ClassNotFoundException {
        return orderDAO.getTotalRevenue();
    }
}
