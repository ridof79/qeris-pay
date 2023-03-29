package com.enigma.qerispay.dto.transaction;

import com.enigma.qerispay.entiy.Merchant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMerchantDTO {

    private Merchant merchant;
    private Integer amount;
    private Integer cashback;
}
