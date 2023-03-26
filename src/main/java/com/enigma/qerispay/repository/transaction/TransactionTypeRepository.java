package com.enigma.qerispay.repository.transaction;

import com.enigma.qerispay.entiy.transaction.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, String> {
}
