package com.enigma.qerispay.repository.transaction;

import com.enigma.qerispay.entiy.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
