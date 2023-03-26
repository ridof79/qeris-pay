package com.enigma.qerispay.repository;

import com.enigma.qerispay.entiy.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findCustomerByCustomerPhone(String phoneCustomer);
}
