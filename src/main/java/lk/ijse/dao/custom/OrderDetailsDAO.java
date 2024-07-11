package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDao;
import lk.ijse.entity.OrderProduct;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsDAO extends CrudDao<OrderProduct> {
    boolean saveOD(ArrayList<OrderProduct> orderProducts) throws SQLException, ClassNotFoundException;
}
