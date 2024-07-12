package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.entity.Customer;
import lk.ijse.model.CustomerDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

   @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for(Customer c: all){
            allCustomers.add(new CustomerDTO(c.getCustomerId(),c.getName(),c.getAddress(),c.getContact(),c.getEmail()));
        }
        return allCustomers;
    }

    @Override
    public boolean saveCustomers(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(customerDTO.getCustomerId(),customerDTO.getName(),customerDTO.getAddress(),customerDTO.getContact(),customerDTO.getEmail()));
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(id);
        return new CustomerDTO(c.getCustomerId(),c.getName(),c.getAddress(),c.getContact(),c.getEmail());
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(customerDTO.getCustomerId(),customerDTO.getName(),customerDTO.getAddress(),customerDTO.getContact(),customerDTO.getEmail()));
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return customerDAO.delete(id);
    }

    @Override
    public ArrayList<String> getAllCusIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> allIds = customerDAO.getAllIds();
        ArrayList<String> customerIds = new ArrayList<>();
        for(String i : allIds){
            customerIds.add(i);
        }
        return customerIds;
    }

    @Override
    public int getAllCustomerCount() throws SQLException, ClassNotFoundException {
        return customerDAO.getAllCustomerCount();
    }

    @Override
    public Customer searchById(String id) throws SQLException, ClassNotFoundException {
    return customerDAO.searchById(id);
    }


}


