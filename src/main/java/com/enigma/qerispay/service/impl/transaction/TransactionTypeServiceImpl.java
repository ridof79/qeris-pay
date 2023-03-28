package com.enigma.qerispay.service.impl.transaction;

import com.enigma.qerispay.dto.ETransactionType;
import com.enigma.qerispay.entiy.transaction.TransactionType;
import com.enigma.qerispay.repository.transaction.TransactionTypeRepository;
import com.enigma.qerispay.service.transaction.TransactionTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionTypeServiceImpl implements TransactionTypeService {

    TransactionTypeRepository transactionTypeRepository;

    @Override
    public TransactionType getOrSave(ETransactionType transactionType) {
        return transactionTypeRepository.findByTransactionType(transactionType)
                .orElseGet(() -> transactionTypeRepository.save(new TransactionType(null, transactionType)));
    }
}
