package com.enigma.qerispay.service.impl;

import com.enigma.qerispay.entiy.transaction.Transaction;
import com.enigma.qerispay.entiy.transaction.TransactionCashback;
import com.enigma.qerispay.repository.transaction.TransactionRepository;
import com.enigma.qerispay.repository.transaction.TransactionTypeRepository;
import com.enigma.qerispay.service.TransactionCashbackService;
import com.enigma.qerispay.service.TransactionService;
import com.enigma.qerispay.utils.constant.NotFoundConstant;
import com.enigma.qerispay.utils.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;
    TransactionCashbackService transactionCashbackService;

    @Override
    public Transaction addTransaction(Transaction transaction) {
        Transaction savedTransaction = transactionRepository.save(transaction);
        savedTransaction.setDate(Date.valueOf(LocalDate.now()));

        if (savedTransaction.getCashback()!=null) {
            TransactionCashback addCashback = transactionCashbackService.addTransactionCashback(transaction.getCashback());
            savedTransaction.setCashback(addCashback);
        }

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionById(String id) {
        if(transactionRepository.findById(id).isPresent()) {
            return transactionRepository.findById(id).get();
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.TRX_NOT_FOUND, id));
        }
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) {
        if(transactionRepository.findById(transaction.getId()).isPresent()) {
            return transactionRepository.save(transaction);
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.TRX_NOT_FOUND, transaction.getId()));
        }
    }

    @Override
    public void deleteTransaction(String id) {
        if(transactionRepository.findById(id).isPresent()) {
            transactionRepository.deleteById(id);
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.TRX_NOT_FOUND, id));
        }
    }
}
