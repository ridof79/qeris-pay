package com.enigma.qerispay.dto;

import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Merchant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPaymentCustomerDTO {
    private Customer customer;
    private File file;
}
