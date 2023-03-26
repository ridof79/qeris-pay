package com.enigma.qerispay.repository;

import com.enigma.qerispay.entiy.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant,String> {
}
