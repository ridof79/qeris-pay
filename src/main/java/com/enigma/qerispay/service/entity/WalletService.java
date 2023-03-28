package com.enigma.qerispay.service.entity;

import com.enigma.qerispay.entiy.Wallet;

public interface WalletService {
    Wallet saveWallet(Wallet wallet);
    Wallet getWalletById(String id);
    Wallet updateWallet(Wallet wallet);
    void deleteWallet(String id);
}
