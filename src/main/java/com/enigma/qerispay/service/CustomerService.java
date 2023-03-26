package com.enigma.qerispay.service;


import com.enigma.qerispay.entiy.Customer;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    List<Customer> getAllCustomer();
    Customer getCustomerById(String id);
    void deleteCustomer(String id);
    Customer getCustomerByPhone(String phoneNumber);
}