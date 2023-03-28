package com.enigma.qerispay.repository;

import com.enigma.qerispay.entiy.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findCustomerByCustomerPhone(String phoneCustomer);
    Customer findCustomerByCustomerEmail(String email);
    Boolean existsByCustomerEmail(String email);
}
