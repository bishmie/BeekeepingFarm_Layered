package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.entity.Customer;
import lk.ijse.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;


public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAllCustomers();
        for(Customer c: all){
            allCustomers.add(new CustomerDTO(c.getCustomerId(),c.getName(),c.getAddress(),c.getContact(),c.getEmail()));
        }
        return allCustomers;
    }

    @Override
    public boolean saveCustomers(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.saveCustomers(new Customer(customerDTO.getCustomerId(),customerDTO.getName(),customerDTO.getAddress(),customerDTO.getContact(),customerDTO.getEmail()));
    }
}


