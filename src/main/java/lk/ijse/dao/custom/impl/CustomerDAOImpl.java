package lk.ijse.dao.custom.impl;

import com.beust.ah.A;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer");
        while (rst.next()) {
            Customer customer = new Customer(rst.getString("customerId"), rst.getString("name"), rst.getString("address"), rst.getString("contact"), rst.getString("email"));
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer(customerId,name,address,contact,email) VALUES (?,?,?,?,?)", customer.getCustomerId(), customer.getName(), customer.getAddress(), customer.getContact(), customer.getEmail());

    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE customerId =?", id);
        if (resultSet.next()) {
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String email = resultSet.getString(5);
            return new Customer(resultSet.getString(1), name, address, contact, email);

        }
        return null;
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE customer SET name =?, address =?, contact =?, email =? WHERE customerId =?", customer.getName(), customer.getAddress(), customer.getContact(), customer.getEmail(), customer.getCustomerId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM customer WHERE customerId=?", id);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> all = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT customerId FROM customer");
        while (resultSet.next()) {
            String id = resultSet.getString("customerId");
            all.add(id);
        }
        return all;
    }

    @Override
    public int getAllCustomerCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) AS customerCount FROM customer");

        if (resultSet.next()) {
            return resultSet.getInt("customerCount");
        }

        return 0;
    }
}
