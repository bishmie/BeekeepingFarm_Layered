package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDao;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDao<Customer> {


    ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException;
}
