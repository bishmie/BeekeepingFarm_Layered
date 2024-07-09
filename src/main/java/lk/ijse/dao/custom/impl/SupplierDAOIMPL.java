package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOIMPL implements SupplierDAO {
    @Override
    public boolean save(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO supplier (supplierId,name,address,contact,email,inventoryId) VALUES (?,?,?,?,?,?)" , entity.getSupplierId(),entity.getName(),entity.getAddress(),entity.getContact(),entity.getEmail(),entity.getInventoryId());
    }

    @Override
    public Supplier search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE supplier SET name =?, address =?, contact =?, email =?, inventoryId =? WHERE supplierId =?" , entity.getName(),entity.getAddress(),entity.getContact(),entity.getEmail(),entity.getInventoryId(),entity.getSupplierId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
