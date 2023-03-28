package com.enigma.qerispay.service.transaction;

import com.enigma.qerispay.entiy.transaction.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction addTransaction(Transaction transaction);
    Transaction getTransactionById(String id);
    List<Transaction> getAllTransaction();
    Transaction updateTransaction(Transaction transaction);
    void deleteTransaction(String id);
}
