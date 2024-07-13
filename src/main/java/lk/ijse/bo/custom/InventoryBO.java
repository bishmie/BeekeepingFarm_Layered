package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.InventoryDTO;

import java.sql.SQLException;

public interface InventoryBO extends SuperBO {
    boolean saveInventory(InventoryDTO inventoryDTO) throws SQLException, ClassNotFoundException;

    boolean updateInventory(InventoryDTO inventoryDTO) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    InventoryDTO search(String id) throws SQLException, ClassNotFoundException;
}

