package com.enigma.qerispay.service.impl;

import com.enigma.qerispay.dto.ETransactionType;
import com.enigma.qerispay.dto.TopUpDTO;
import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Wallet;
import com.enigma.qerispay.entiy.transaction.Transaction;
import com.enigma.qerispay.entiy.transaction.TransactionType;
import com.enigma.qerispay.service.*;
import com.enigma.qerispay.utils.constant.NotFoundConstant;
import com.enigma.qerispay.utils.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TopUpServiceImpl implements TopUpService {

    TransactionService transactionService;
    TransactionTypeService transactionTypeService;
    WalletService walletService;
    CustomerService customerService;

    @Override
    public TopUpDTO customerTopUp(TopUpDTO topUpDTO) {
        if (customerService.getCustomerById(topUpDTO.getCustomer().getId())!=null) {
            Customer customer = customerService.getCustomerById(topUpDTO.getCustomer().getId());

            Transaction topUpTransaction = new Transaction();

            topUpTransaction.setAmount(topUpDTO.getAmount());
            topUpTransaction.setCustomer(topUpDTO.getCustomer());

            TransactionType type = transactionTypeService.getOrSave(ETransactionType.TOP_UP);

            topUpTransaction.setType(type);
            transactionService.addTransaction(topUpTransaction);

            Wallet wallet = walletService.getWalletById(customer.getWallet().getId());

            wallet.setBalance(wallet.getBalance() + topUpDTO.getAmount());
            walletService.updateWallet(wallet);
            return topUpDTO;
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.CUSTOMER_NOT_FOUND, topUpDTO.getCustomer().getId()));
        }
    }
}
