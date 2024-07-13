package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.InventoryDAO;
import lk.ijse.entity.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDAOImpl implements InventoryDAO {
    @Override
    public boolean save(Inventory entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO inventory (inventoryId,type,description,qty,unitPrice,beehiveId) VALUES (?,?,?,?,?,?)",entity.getInventoryId(),entity.getType(),entity.getDescription(),entity.getQty(),entity.getUnitPrice(),entity.getBeeHiveId());
    }

    @Override
    public Inventory search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM inventory WHERE inventoryId=?",id);
        if(resultSet.next()){
            String type = resultSet.getString(2);
            String desc = resultSet.getString(3);
            String qty = resultSet.getString(4);
            String unitPrice = resultSet.getString(5);
            String beekeeperId = resultSet.getString(6);
          return new Inventory(resultSet.getString(1),type,desc,qty,unitPrice,beekeeperId);
        }
        return null;
    }

    @Override
    public boolean update(Inventory entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE inventory SET type =?, description =?, qty =?, unitPrice =?, beehiveId =? WHERE inventoryId =?",entity.getType(),entity.getDescription(),entity.getQty(),entity.getUnitPrice(),entity.getBeeHiveId(),entity.getInventoryId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM inventory WHERE inventoryId =?",id);
    }

    @Override
    public ArrayList<Inventory> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
