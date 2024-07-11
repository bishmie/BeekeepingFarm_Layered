package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.PlaceOrderDTO;

import java.sql.SQLException;

public interface PlaceOrderBO extends SuperBO {

    String generateNewOrderId() throws SQLException, ClassNotFoundException;

    String getCurrentId() throws SQLException, ClassNotFoundException;

    boolean placeOrder(PlaceOrderDTO po) throws SQLException;
}
