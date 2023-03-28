package com.enigma.qerispay.service.transaction;

import com.enigma.qerispay.entiy.transaction.TransactionCashback;

import java.util.List;

public interface TransactionCashbackService {
    TransactionCashback addTransactionCashback (TransactionCashback cashback);
    TransactionCashback getTransactionCashbackById (String id);
    List<TransactionCashback> getAllTransactionCashback();
    TransactionCashback updateTransactionCashback (TransactionCashback cashback);
    void deleteTransactionCashback (String id);
}
