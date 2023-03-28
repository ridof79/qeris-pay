package com.enigma.qerispay.service.impl.entity;

import com.enigma.qerispay.entiy.Wallet;
import com.enigma.qerispay.repository.WalletRepository;
import com.enigma.qerispay.service.entity.WalletService;
import com.enigma.qerispay.utils.constant.NotFoundConstant;
import com.enigma.qerispay.utils.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    WalletRepository walletRepository;

    @Override
    public Wallet saveWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet getWalletById(String id) {
        if (walletRepository.findById(id).isPresent()) {
            return walletRepository.findById(id).get();
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.WALLET_NOT_FOUND, id));
        }
    }

    @Override
    public Wallet updateWallet(Wallet wallet) {
        if (walletRepository.findById(wallet.getId()).isPresent()) {
            return walletRepository.save(wallet);
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.WALLET_NOT_FOUND, wallet.getId()));
        }
    }

    @Override
    public void deleteWallet(String id) {
        if (walletRepository.findById(id).isPresent()) {
            walletRepository.deleteById(id);
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.WALLET_NOT_FOUND, id));
        }
    }
}
