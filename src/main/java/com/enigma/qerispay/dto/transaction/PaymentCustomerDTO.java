package com.enigma.qerispay.dto.transaction;

import com.enigma.qerispay.entiy.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCustomerDTO {
    private Customer customer;
    private File file;
}
