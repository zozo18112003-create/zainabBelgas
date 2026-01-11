package com.hotelmanagement.service;

import com.hotelmanagement.dao.CustomerDAO;
import com.hotelmanagement.dao.impl.CustomerDAOImpl;
import com.hotelmanagement.entity.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerService {

    private CustomerDAO customerDAO = new CustomerDAOImpl();

    public void addCustomer(Customer customer) {
        customerDAO.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    public List<Customer> getCustomersWithBills() {
        // Example usage of Streams as requested
        return customerDAO.findAll()
                .stream()
                .filter(c -> c.getBills() != null && !c.getBills().isEmpty())
                .collect(Collectors.toList());
    }
}
