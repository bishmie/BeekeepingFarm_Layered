package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAllCustomers() throws SQLException, ClassNotFoundException {

        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer");
        while (rst.next()) {
            Customer customer = new Customer(rst.getString("customerId"), rst.getString("name"), rst.getString("address"),rst.getString("contact"), rst.getString("email"));
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public boolean saveCustomers(Customer customer) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer(customerId,name,address,contact,email) VALUES (?,?,?,?,?)",customer.getCustomerId(),customer.getName(),customer.getAddress(),customer.getContact(),customer.getEmail());

    }
}
