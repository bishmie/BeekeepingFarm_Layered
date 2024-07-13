package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.InventoryBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.InventoryDAO;
import lk.ijse.entity.Inventory;
import lk.ijse.model.InventoryDTO;

import java.sql.SQLException;

public class InventoryBOImpl implements InventoryBO {
    InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.INVENTORY);
    @Override
    public boolean saveInventory(InventoryDTO inventoryDTO) throws SQLException, ClassNotFoundException {
        return inventoryDAO.save(new Inventory(inventoryDTO.getInventoryId(),inventoryDTO.getType(),inventoryDTO.getDescription(),inventoryDTO.getQty(),inventoryDTO.getUnitPrice(),inventoryDTO.getBeeHiveId()));
    }

    @Override
    public boolean updateInventory(InventoryDTO inventoryDTO) throws SQLException, ClassNotFoundException {
        return inventoryDAO.update(new Inventory(inventoryDTO.getInventoryId(),inventoryDTO.getType(),inventoryDTO.getDescription(),inventoryDTO.getQty(),inventoryDTO.getUnitPrice(),inventoryDTO.getBeeHiveId()));
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return inventoryDAO.delete(id);
    }

    @Override
    public InventoryDTO search(String id) throws SQLException, ClassNotFoundException {
        Inventory i = inventoryDAO.search(id);
        return new InventoryDTO(i.getInventoryId(),i.getType(),i.getDescription(),i.getQty(),i.getUnitPrice(),i.getBeeHiveId() );

    }
}
