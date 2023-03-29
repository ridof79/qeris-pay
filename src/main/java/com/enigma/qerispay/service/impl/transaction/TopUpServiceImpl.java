package com.enigma.qerispay.service.impl.transaction;

import com.enigma.qerispay.dto.ETransactionType;
import com.enigma.qerispay.dto.transaction.TopUpDTO;
import com.enigma.qerispay.dto.transaction.TopUpMerchantDTO;
import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Merchant;
import com.enigma.qerispay.entiy.Wallet;
import com.enigma.qerispay.entiy.transaction.Transaction;
import com.enigma.qerispay.service.entity.CustomerService;
import com.enigma.qerispay.service.entity.MerchantService;
import com.enigma.qerispay.service.entity.WalletService;
import com.enigma.qerispay.service.transaction.TopUpService;
import com.enigma.qerispay.service.transaction.TransactionService;
import com.enigma.qerispay.service.transaction.TransactionTypeService;
import com.enigma.qerispay.utils.constant.NotFoundConstant;
import com.enigma.qerispay.utils.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TopUpServiceImpl implements TopUpService {

    TransactionService transactionService;
    TransactionTypeService transactionTypeService;
    WalletService walletService;
    CustomerService customerService;
    MerchantService merchantService;

    @Override
    @Transactional
    public TopUpDTO customerTopUp(TopUpDTO topUpDTO) {
        if (customerService.getCustomerById(topUpDTO.getCustomerId()) != null) {
            Customer customer = customerService.getCustomerById(topUpDTO.getCustomerId());

            Transaction topUpTransaction = new Transaction();
            topUpTransaction.setAmount(topUpDTO.getAmount());
            topUpTransaction.setCustomer(customer);
            topUpTransaction.setType(transactionTypeService.getOrSave(ETransactionType.TOP_UP));
            transactionService.addTransaction(topUpTransaction);

            Wallet wallet = walletService.getWalletById(customer.getWallet().getId());
            wallet.setBalance(wallet.getBalance() + topUpDTO.getAmount());
            walletService.updateWallet(wallet);

            return topUpDTO;
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.CUSTOMER_NOT_FOUND, topUpDTO.getCustomerId()));
        }
    }

    @Override
    @Transactional
    public TopUpMerchantDTO merchantTopUp(TopUpMerchantDTO topUpMerchantDTO) {
        if (merchantService.getMerchantById(topUpMerchantDTO.getMerchantId()) != null) {
            Merchant merchant = merchantService.getMerchantById(topUpMerchantDTO.getMerchantId());

            Transaction topUpTransaction = new Transaction();
            topUpTransaction.setAmount(topUpMerchantDTO.getAmount());
            topUpTransaction.setMerchant(merchant);
            topUpTransaction.setType(transactionTypeService.getOrSave(ETransactionType.TOP_UP));
            transactionService.addTransaction(topUpTransaction);

            Wallet wallet = walletService.getWalletById(merchant.getWallet().getId());
            wallet.setQerisCoin(wallet.getQerisCoin() + topUpMerchantDTO.getAmount());
            walletService.updateWallet(wallet);
            return topUpMerchantDTO;
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.MERCHANT_NOT_FOUND, topUpMerchantDTO.getMerchantId()));
        }
    }
}
