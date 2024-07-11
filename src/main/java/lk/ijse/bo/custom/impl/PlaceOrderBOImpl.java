package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PlaceOrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.OrderDetailsDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.Order;
import lk.ijse.entity.OrderProduct;
import lk.ijse.model.OrderProductDTO;
import lk.ijse.model.PlaceOrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class PlaceOrderBOImpl implements PlaceOrderBO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERPRODUCT);

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.getNextOrderId();
    }

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        return orderDAO.getCurrentId();
    }

    @Override
    public boolean placeOrder(PlaceOrderDTO po)  {
        ///////////////////////////

        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isSaved = orderDAO.save(new Order(po.getOrder().getOrderId(),po.getOrder().getCustomerId(), po.getOrder().getOrderDate()));

            if (isSaved){
                ArrayList<OrderProduct> orderProducts = new ArrayList<>();
                for (OrderProductDTO orderProduct : po.getOdList()){
                    orderProducts.add(new OrderProduct(orderProduct.getOrderId(),orderProduct.getProductId(), orderProduct.getQty(), orderProduct.getSellingPrice()));
                }
                boolean isSavedOd = orderDetailsDAO.saveOD(orderProducts);

                if (isSavedOd){
                    connection.commit();
                    return true;
                }else {
                    connection.rollback();
                    return false;
                }
            }else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        }
    }
}
