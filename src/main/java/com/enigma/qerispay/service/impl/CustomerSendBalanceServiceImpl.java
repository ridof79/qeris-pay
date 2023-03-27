package com.enigma.qerispay.service.impl;

import com.enigma.qerispay.dto.ETransactionType;
import com.enigma.qerispay.dto.SendBalanceDTO;
import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Wallet;
import com.enigma.qerispay.entiy.transaction.Transaction;
import com.enigma.qerispay.service.*;
import com.enigma.qerispay.utils.constant.TransactionConstant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerSendBalanceServiceImpl implements CustomerSendBalanceService {

    CustomerService customerService;
    WalletService walletService;
    TransactionTypeService typeService;
    TransactionService transactionService;

    @Override
    public SendBalanceDTO sendBalance(SendBalanceDTO sendBalanceDTO) {
        Customer sender = customerService.getCustomerById(sendBalanceDTO.getSender().getId());
        Customer receiver = customerService.getCustomerByPhone(sendBalanceDTO.getPhoneNumberReceiver());

        Wallet senderWallet = sender.getWallet();
        Wallet receiverWallet = receiver.getWallet();

        Transaction transaction = new Transaction();
        transaction.setCustomer(sender);
        transaction.setAmount(sendBalanceDTO.getAmount());
        transaction.setType(typeService.getOrSave(ETransactionType.TRANSFER));
        transaction.setDescription(String.format(TransactionConstant.TRANSFER_BALANCE, sendBalanceDTO.getAmount(), receiver.getCustomerName()));
        transactionService.addTransaction(transaction);

        senderWallet.setBalance(senderWallet.getBalance() - sendBalanceDTO.getAmount());
        receiverWallet.setBalance(receiverWallet.getBalance() + sendBalanceDTO.getAmount());
        walletService.updateWallet(senderWallet);
        walletService.updateWallet(receiverWallet);

        return sendBalanceDTO;
    }
}
