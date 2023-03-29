package com.enigma.qerispay.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendBalanceDTO {
    private String senderId;
    private Integer amount;
    private String phoneNumberReceiver;
}

