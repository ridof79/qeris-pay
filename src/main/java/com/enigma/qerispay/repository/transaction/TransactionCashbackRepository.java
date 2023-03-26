package com.enigma.qerispay.repository.transaction;

import com.enigma.qerispay.entiy.transaction.TransactionCashback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionCashbackRepository extends JpaRepository<TransactionCashback, String> {
}
