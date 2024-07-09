package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.SupplierDTO;

import java.sql.SQLException;

public interface SupplierBO extends SuperBO {
     boolean  saveSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;

     boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;
}
