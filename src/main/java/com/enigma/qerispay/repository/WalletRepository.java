package com.enigma.qerispay.repository;

import com.enigma.qerispay.entiy.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, String> {
}
