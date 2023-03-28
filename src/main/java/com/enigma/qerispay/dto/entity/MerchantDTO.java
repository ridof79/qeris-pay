package com.enigma.qerispay.dto.entity;

import com.enigma.qerispay.entiy.Merchant;
import com.enigma.qerispay.entiy.Wallet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantDTO {
    private String id;
    private String username;
    private String merchantAddress;
    private String merchantEmail;
    private String merchantNIB;
    private String merchantName;
    private String merchantPhone;
    private Wallet wallet;

    public MerchantDTO(Merchant merchant) {
        this.id = merchant.getId();
        this.username = merchant.getUsername();
        this.merchantAddress = merchant.getMerchantAddress();
        this.merchantEmail = merchant.getMerchantEmail();
        this.merchantNIB = merchant.getMerchantNIB();
        this.merchantName = merchant.getMerchantName();
        this.merchantPhone = merchant.getMerchantPhone();
        this.wallet = merchant.getWallet();
    }
}
