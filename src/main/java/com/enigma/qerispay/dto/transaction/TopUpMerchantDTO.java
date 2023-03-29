package com.enigma.qerispay.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopUpMerchantDTO {
    private String merchantId;
    private Integer amount;
}
