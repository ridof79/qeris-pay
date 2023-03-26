package com.enigma.qerispay.repository.transaction;

import com.enigma.qerispay.dto.ETransactionType;
import com.enigma.qerispay.entiy.transaction.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, String> {
    Optional<TransactionType> findByTransactionType(ETransactionType transactionType);

}
