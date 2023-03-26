package com.enigma.qerispay.controller;

import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.service.CustomerService;
import com.enigma.qerispay.utils.constant.ApiUrlConstant;
import com.enigma.qerispay.utils.constant.InsertDataConstant;
import com.enigma.qerispay.utils.customResponse.Response;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.CUSTOMER_PATH)
@AllArgsConstructor
public class CustomerController {
    CustomerService customerService;

    @GetMapping("/list")
    public List<Customer> getAllCustomer() {
        return customerService.getAllCustomer();
    }

    @PutMapping
    Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable String id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/search")
    public Customer findCustomerByPhone(@RequestParam(name = "phone") String customerPhone) {
        return customerService.getCustomerByPhone(customerPhone);
    }

    @PostMapping
    public ResponseEntity<Response<Customer>> saveCustomer(@RequestBody Customer customer) {
        Response<Customer> response = new Response<>();
        response.setMessage(InsertDataConstant.INSERT_CUSTOMER_SUCCES);
        response.setData(customerService.saveCustomer(customer));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
