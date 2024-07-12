package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.ProductDAO;
import lk.ijse.entity.OrderProduct;
import lk.ijse.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAOIMpl implements ProductDAO {
    @Override
    public boolean save(Product entity) throws SQLException, ClassNotFoundException {
    return SQLUtil.execute("INSERT INTO product(productId,productName,sellingPrice,netWeight,qty,harvestId) Values(?,?,?,?,?,?)",entity.getProductId(),entity.getProductName(),entity.getSellingPrice(),entity.getNetWeight(),entity.getQty(),entity.getHarvestId());
    }

    @Override
    public Product search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM product WHERE productId =?", id);
        if (resultSet.next()) {
            String name = resultSet.getString(2);
            String sellingPrice = resultSet.getString(3);
            String netWeight = resultSet.getString(4);
            String qty = resultSet.getString(5);
            String harvestId = resultSet.getString(6);
            return new Product(resultSet.getString(1), name, sellingPrice, netWeight, qty,harvestId);
        }
        return null;    }

    @Override
    public boolean update(Product entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE product SET productName =?, sellingPrice =?, netWeight =?, qty =?, harvestId =? WHERE productId =?",entity.getProductName(),entity.getNetWeight(),entity.getQty(),entity.getHarvestId(),entity.getProductId());

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM product WHERE productId=?",id);
    }

    @Override
    public ArrayList<Product> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Product> allProducts = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM product");
        while (resultSet.next())

        {
            Product product = new Product(resultSet.getString("productId"),resultSet.getString("productName"),resultSet.getString("sellingPrice"),resultSet.getString("netWeight"),resultSet.getString("qty"),resultSet.getString("harvestId"));
       allProducts.add(product);
        }
        return allProducts;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {

        ArrayList<String> ids = new ArrayList<>();
        ResultSet resultSet =SQLUtil.execute("SELECT productId FROM product");
        while (resultSet.next()){
            String productId = resultSet.getString("productId");
        ids.add(productId);
        }
        return ids;
    }

    @Override
    public boolean updateProArr(ArrayList<OrderProduct> productArrayList) throws SQLException, ClassNotFoundException {
        for (OrderProduct od : productArrayList) {
            boolean isUpdateQty = this.updateQnt(od.getProductId(), od.getQty());
            if (!isUpdateQty) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean updateQnt(String pId, int qnt) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE product SET qty = qty - ? WHERE productId = ?",qnt,pId);
    }
}
