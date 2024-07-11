package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;

import java.sql.SQLException;

public interface OrderBO extends SuperBO {
    int getTodayOrderCount() throws SQLException, ClassNotFoundException;

    int getTotalOrderCount() throws SQLException, ClassNotFoundException;

    int getTotalRevenue() throws SQLException, ClassNotFoundException;
}
