package com.enigma.qerispay.dto.entity;

import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Wallet;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CustomerDTO {
    private String id;
    private String username;
    private Date birthDate;
    private String customerAddress;
    private String customerEmail;
    private String customerPhone;
    private Wallet wallet;

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.username = customer.getUsername();
        this.birthDate = customer.getBirthDate();
        this.customerAddress = customer.getCustomerAddress();
        this.customerEmail = customer.getCustomerEmail();
        this.customerPhone = customer.getCustomerPhone();
        this.wallet = customer.getWallet();
    }
}
