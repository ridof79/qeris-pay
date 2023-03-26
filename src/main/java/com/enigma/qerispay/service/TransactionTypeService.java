package com.enigma.qerispay.service;

import com.enigma.qerispay.dto.ETransactionType;
import com.enigma.qerispay.entiy.transaction.TransactionType;

public interface TransactionTypeService {
    TransactionType getOrSave(ETransactionType transactionType);

}
