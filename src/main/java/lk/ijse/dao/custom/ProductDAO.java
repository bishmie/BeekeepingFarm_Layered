package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDao;
import lk.ijse.entity.OrderProduct;
import lk.ijse.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO extends CrudDao<Product> {
    ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException;

    boolean updateProArr(ArrayList<OrderProduct> productArrayList) throws SQLException, ClassNotFoundException;
    boolean updateQnt(String pId, int qnt) throws SQLException, ClassNotFoundException;

}