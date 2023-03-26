package com.enigma.qerispay.service.impl;

import com.enigma.qerispay.entiy.transaction.TransactionCashback;
import com.enigma.qerispay.repository.transaction.TransactionCashbackRepository;
import com.enigma.qerispay.service.TransactionCashbackService;
import com.enigma.qerispay.utils.constant.NotFoundConstant;
import com.enigma.qerispay.utils.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionCashbackServiceImpl implements TransactionCashbackService {

    TransactionCashbackRepository cashbackRepository;

    @Override
    public TransactionCashback addTransactionCashback(TransactionCashback cashback) {
        return cashbackRepository.save(cashback);
    }

    @Override
    public TransactionCashback getTransactionCashbackById(String id) {
        if (cashbackRepository.findById(id).isPresent()) {
            return cashbackRepository.findById(id).get();
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.TRX_CASHBACK_NOT_FOUND, id));
        }
    }

    @Override
    public List<TransactionCashback> getAllTransactionCashback() {
        return cashbackRepository.findAll();
    }

    @Override
    public TransactionCashback updateTransactionCashback(TransactionCashback cashback) {
        if (cashbackRepository.findById(cashback.getId()).isPresent()) {
            return cashbackRepository.save(cashback);
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.TRX_CASHBACK_NOT_FOUND, cashback.getId()));
        }
    }

    @Override
    public void deleteTransactionCashback(String id) {
        if (cashbackRepository.findById(id).isPresent()) {
            cashbackRepository.deleteById(id);
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.TRX_CASHBACK_NOT_FOUND, id));
        }
    }
}
