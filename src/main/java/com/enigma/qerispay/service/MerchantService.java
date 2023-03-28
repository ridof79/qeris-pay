package com.enigma.qerispay.service;

import com.enigma.qerispay.dto.entity.MerchantDTO;
import com.enigma.qerispay.entiy.Merchant;

import java.util.List;

public interface MerchantService {
    Merchant saveMerchant(Merchant merchant);
    MerchantDTO updateMerchant(Merchant merchant);
    List<Merchant> getAllMerchant();
    Merchant getMerchantById(String id);
    void deleteMerchant(String id);
    Merchant getMerchantByNIB(String nib);
}
