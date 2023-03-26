package com.enigma.qerispay.service;

import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Merchant;

import java.util.List;

public interface MerchantService {
    Merchant saveMerchant(Merchant merchant);
    Merchant updateMerchant(Merchant merchant);
    List<Merchant> getAllMerchant();
    Merchant getMerchantById(String id);
    void deleteMerchant(String id);
    Merchant getMerchantByNIB(String nib);
}
