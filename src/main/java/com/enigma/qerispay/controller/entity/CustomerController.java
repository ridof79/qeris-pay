package com.enigma.qerispay.controller.entity;

import com.enigma.qerispay.auth.CustomUserDetails;
import com.enigma.qerispay.dto.entity.CustomerDTO;
import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.service.entity.CustomerService;
import com.enigma.qerispay.utils.constant.ApiUrlConstant;
import com.enigma.qerispay.utils.constant.ControllerConstant;
import com.enigma.qerispay.utils.constant.InsertDataConstant;
import com.enigma.qerispay.utils.customResponse.Response;
import com.enigma.qerispay.utils.exception.UnauthorizedException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.CUSTOMER_PATH)
@AllArgsConstructor
@SecurityRequirement(name = "Authorization")
public class CustomerController {

    CustomerService customerService;

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, Authentication authentication) {
        if (customer.getId().equals(((CustomUserDetails) authentication.getPrincipal()).getId())) {
            CustomerDTO updateCustomer = customerService.updateCustomer(customer);
            Response<?> response = new Response<>(ControllerConstant.SUCCESSFULLY_UPDATE_CUSTOMER_DATA, updateCustomer);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            return new ResponseEntity<>(ControllerConstant.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/list")
    public List<Customer> getAllCustomer() {
        return customerService.getAllCustomer();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id, Authentication authentication) throws AccessDeniedException {
        if (id.equals(((CustomUserDetails) authentication.getPrincipal()).getId())) {
            return customerService.getCustomerById(id);
        } else {
            throw new UnauthorizedException(ControllerConstant.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable String id, Authentication authentication) {
        customerService.deleteCustomer(id);
    }

    @PostMapping
    public ResponseEntity<Response<Customer>> saveCustomer(@RequestBody Customer customer) {
        Response<Customer> response = new Response<>();
        response.setMessage(InsertDataConstant.INSERT_CUSTOMER_SUCCESS);
        response.setData(customerService.saveCustomer(customer));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
