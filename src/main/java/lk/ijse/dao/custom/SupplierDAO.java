package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDao;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;

public interface SupplierDAO extends CrudDao<Supplier> {
    int getAllSupplierCount() throws SQLException, ClassNotFoundException;
}
