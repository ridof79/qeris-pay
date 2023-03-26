package com.enigma.qerispay.dto;

import com.enigma.qerispay.entiy.Merchant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPaymentMerchantDTO {

    private Merchant merchant;
    private Integer amount;
}
