package com.enigma.qerispay.service.impl.entity;

import com.enigma.qerispay.dto.entity.CustomerDTO;
import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.repository.CustomerRepository;
import com.enigma.qerispay.service.entity.CustomerService;
import com.enigma.qerispay.service.security.RoleService;
import com.enigma.qerispay.utils.constant.NotFoundConstant;
import com.enigma.qerispay.utils.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;
    RoleService roleService;

    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public CustomerDTO updateCustomer(Customer customer) {
        if (customerRepository.findById(customer.getId()).isPresent()) {
            Customer customerOld = customerRepository.findById(customer.getId()).get();
            customer.setPassword(customerOld.getPassword());
            customer.setUsername(customerOld.getUsername());
            customer.setEnabled(customerOld.isEnabled());
            customer.setRoles(customerOld.getRoles());
            Customer customerSaved = saveCustomer(customer);
            return new CustomerDTO(customerSaved);
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.CUSTOMER_NOT_FOUND, customer.getId()));
        }
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(String id) {
        if (customerRepository.findById(id).isPresent()) {
            return customerRepository.findById(id).get();
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.CUSTOMER_NOT_FOUND, id));
        }
    }
    @Override
    public void deleteCustomer(String id) {
        if (customerRepository.findById(id).isPresent()) {
            customerRepository.delete(customerRepository.findById(id).get());
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.CUSTOMER_NOT_FOUND, id));
        }
    }

    @Override
    public CustomerDTO getCustomerByPhone(String phoneNumber) {
        if (customerRepository.findCustomerByCustomerPhone(phoneNumber) != null) {
            Customer getCustomer = customerRepository.findCustomerByCustomerPhone(phoneNumber);
            return new CustomerDTO(getCustomer);
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.CUSTOMER_PHONE_NOT_FOUND, phoneNumber));
        }
    }
}
