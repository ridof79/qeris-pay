package com.enigma.qerispay.dto;

import com.enigma.qerispay.entiy.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendBalanceDTO {
    private Customer sender;
    private Integer amount;
    private String phoneNumberReceiver;
}

