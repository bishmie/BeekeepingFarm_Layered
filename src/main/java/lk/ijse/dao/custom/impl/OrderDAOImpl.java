package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean save(Order entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO orders(orderId,customerId,orderDate) VALUES (?,?,?)", entity.getOrderId(), entity.getCustomerId(),entity.getOrderDate());

    }

    @Override
    public Order search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getNextOrderId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1");

        if (resultSet.next()) {
            String orderId = resultSet.getString("orderId");
            if (orderId != null && orderId.startsWith("OID-")) {
                try {
                    int numericId = Integer.parseInt(orderId.replace("OID-", ""));
                    return String.format("OID-%03d", numericId + 1);
                } catch (NumberFormatException e) {
                    // Log the error and handle it appropriately
                    e.printStackTrace();
                    // Decide on a fallback orderId, e.g., "OID-001"
                    return "OID-001";
                }
            } else {
                // Handle the case where the orderId does not start with "OID-"
                // Decide on a fallback orderId, e.g., "OID-001"
                return "OID-001";
            }
        } else {
            return "OID-001";
        }

    }

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1");
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }

    @Override
    public int todayOrderCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= SQLUtil.execute("SELECT COUNT(*) AS todayOrderCount FROM orders WHERE DATE(orderDate) = CURRENT_DATE");
        if (resultSet.next()) {
            return resultSet.getInt("todayOrderCount");
        }
        return 0;
    }

    @Override
    public int totalOrderCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) AS orderCount FROM orderProduct");
   if(resultSet.next()){
       return resultSet.getInt("orderCount");

   }
return 0;
    }

    @Override
    public int getTotalRevenue() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(qty * sellingPrice) AS totalRevenue FROM orderProduct");
        if (resultSet.next()) {
            return resultSet.getInt("totalRevenue");
        }

        return 0;
    }
}
