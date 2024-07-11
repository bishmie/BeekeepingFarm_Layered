package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.entity.Product;
import lk.ijse.entity.Supplier;
import lk.ijse.model.ProductDTO;
import lk.ijse.model.SupplierDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    @Override
    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(supplierDTO.getSupplierId(),supplierDTO.getName(),supplierDTO.getAddress(),supplierDTO.getContact(),supplierDTO.getEmail(),supplierDTO.getInventoryId()));
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(supplierDTO.getSupplierId(),supplierDTO.getName(),supplierDTO.getAddress(),supplierDTO.getContact(),supplierDTO.getEmail(),supplierDTO.getInventoryId()));
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }

    @Override
    public SupplierDTO searchUpplier(String id) throws SQLException, ClassNotFoundException {
        Supplier supplier=  supplierDAO.search(id);
        return new SupplierDTO(supplier.getSupplierId(),supplier.getName(),supplier.getAddress(),supplier.getContact(),supplier.getEmail(),supplier.getInventoryId());
    }

    @Override
    public int getSupplierCount() throws SQLException, ClassNotFoundException {
        return supplierDAO.getAllSupplierCount();
    }
}
