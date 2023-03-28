package com.enigma.qerispay.service.impl.transaction;

import com.enigma.qerispay.dto.ETransactionType;
import com.enigma.qerispay.dto.entity.CustomerDTO;
import com.enigma.qerispay.dto.transaction.SendBalanceDTO;
import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Wallet;
import com.enigma.qerispay.entiy.transaction.Transaction;
import com.enigma.qerispay.service.entity.CustomerService;
import com.enigma.qerispay.service.entity.WalletService;
import com.enigma.qerispay.service.transaction.CustomerSendBalanceService;
import com.enigma.qerispay.service.transaction.TransactionService;
import com.enigma.qerispay.service.transaction.TransactionTypeService;
import com.enigma.qerispay.utils.constant.TransactionConstant;
import com.enigma.qerispay.utils.exception.InsufficientBalanceException;
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
        CustomerDTO receiverDTO = customerService.getCustomerByPhone(sendBalanceDTO.getPhoneNumberReceiver());
        Customer receiver = customerService.getCustomerById(receiverDTO.getId());

        Wallet senderWallet = sender.getWallet();
        Wallet receiverWallet = receiver.getWallet();

        if (!(senderWallet.getBalance() < sendBalanceDTO.getAmount())) {

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

        } else {
            throw new InsufficientBalanceException(TransactionConstant.INSUFFICIENT_BALANCE);
        }
    }
}
