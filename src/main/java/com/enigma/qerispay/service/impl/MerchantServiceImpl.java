package com.enigma.qerispay.service.impl;

import com.enigma.qerispay.dto.entity.MerchantDTO;
import com.enigma.qerispay.entiy.Merchant;
import com.enigma.qerispay.entiy.Wallet;
import com.enigma.qerispay.repository.MerchantRepository;
import com.enigma.qerispay.service.MerchantService;
import com.enigma.qerispay.service.RoleService;
import com.enigma.qerispay.service.WalletService;
import com.enigma.qerispay.utils.constant.NotFoundConstant;
import com.enigma.qerispay.utils.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    MerchantRepository merchantRepository;
    WalletService walletService;
    RoleService roleService;

    @Override
    @Transactional
    public Merchant saveMerchant(Merchant merchant) {

        Merchant savedMerchant = merchantRepository.save(merchant);
        Wallet addWallet = walletService.saveWallet(savedMerchant.getWallet());
        savedMerchant.setWallet(addWallet);

        return merchantRepository.save(merchant);
    }

    @Override
    @Transactional
    public MerchantDTO updateMerchant(Merchant merchant) {
        if (merchantRepository.findById(merchant.getId()).isPresent()) {
            Merchant merchantSaved = saveMerchant(merchant);
            return new MerchantDTO(merchantSaved);
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.MERCHANT_NOT_FOUND, merchant.getId()));
        }
    }

    @Override
    public List<Merchant> getAllMerchant() {
        return merchantRepository.findAll();
    }

    @Override
    public Merchant getMerchantById(String id) {
        if (merchantRepository.findById(id).isPresent()) {
            return merchantRepository.findById(id).get();
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.MERCHANT_NOT_FOUND, id));
        }
    }

    @Override
    public void deleteMerchant(String id) {
        if (merchantRepository.findById(id).isPresent()) {
            merchantRepository.deleteById(id);
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.MERCHANT_NOT_FOUND, id));
        }
    }

    @Override
    public Merchant getMerchantByNIB(String nib) {
        if (merchantRepository.findMerchantByMerchantNIB(nib)!=null) {
            return merchantRepository.findMerchantByMerchantNIB(nib);
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.MERCHANT_NIB_NOT_FOUND, nib));
        }
    }
}
