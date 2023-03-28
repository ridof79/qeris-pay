package com.enigma.qerispay.dto.transaction;

import com.enigma.qerispay.entiy.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopUpDTO {
    private Customer customer;
    private Integer amount;
}
