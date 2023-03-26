package com.enigma.qerispay.repository.transaction;

import com.enigma.qerispay.entiy.transaction.TransactionQeris;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionQerisRepository extends JpaRepository<TransactionQeris, String> {
}
