package com.enigma.qerispay.service.impl;

import com.enigma.qerispay.dto.ETransactionType;
import com.enigma.qerispay.dto.RequestPaymentCustomerDTO;
import com.enigma.qerispay.dto.RequestPaymentMerchantDTO;
import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Merchant;
import com.enigma.qerispay.entiy.Wallet;
import com.enigma.qerispay.entiy.storage.FileStorage;
import com.enigma.qerispay.entiy.transaction.Transaction;
import com.enigma.qerispay.entiy.transaction.TransactionCashback;
import com.enigma.qerispay.qr.QRCodeGenerator;
import com.enigma.qerispay.service.*;
import com.enigma.qerispay.utils.constant.RequestQRFormat;
import com.enigma.qerispay.utils.customResponse.ResponseFile;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;

import static com.enigma.qerispay.qr.QRCodeGenerator.decodeQRCode;

@Service
@AllArgsConstructor
public class RequestPaymentServiceImpl implements RequestPaymentService {

    MerchantService merchantService;
    WalletService walletService;
    CustomerService customerService;
    TransactionService transactionService;
    TransactionTypeService transactionTypeService;
    FIleStorageService fIleStorageService;
    TransactionCashbackService cashbackService;

    @Override
    public ResponseFile requestPayment(RequestPaymentMerchantDTO requestPaymentMerchantDTO) throws IOException, WriterException {
        String merchantId = requestPaymentMerchantDTO.getMerchant().getId();
        Integer amount = requestPaymentMerchantDTO.getAmount();

        Integer a = requestPaymentMerchantDTO.getCashback();
        Double cashback = null;
        if (a != null) {
            cashback = a.doubleValue();
            cashback = (cashback / 100) * amount;
        } else {
            cashback = 0.0;
        }
        Integer resultCashback = cashback.intValue();

        String request = String.format(RequestQRFormat.REQUEST_PAYMENT_QR, merchantId, amount.toString(), resultCashback);
        String fileName = String.format(RequestQRFormat.QR_CODE_FILENAME, requestPaymentMerchantDTO.getMerchant().getId(), requestPaymentMerchantDTO.getAmount(), requestPaymentMerchantDTO.getCashback());

        byte[] pngData = QRCodeGenerator.getQRCodeImage(request, 250, 250);
        FileStorage qr = new FileStorage(fileName, "image/png", pngData);
        fIleStorageService.addFile(qr);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/")
                .path(qr.getId())
                .toUriString();

        return new ResponseFile(
                qr.getName(),
                fileDownloadUri,
                qr.getType(),
                qr.getData().length);
    }

    @Override
    public RequestPaymentMerchantDTO decodeQRCodeFromMerchant(File file) {
        try {
            String[] resultDecode = decodeQRCode(file).split(",");
            return new RequestPaymentMerchantDTO(merchantService.getMerchantById(resultDecode[0]), Integer.parseInt(resultDecode[1]), Integer.parseInt(resultDecode[2]));

        } catch (IOException e) {
            System.out.println("Could not decode QR Code, IOException :: " + e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public void makePayment(RequestPaymentCustomerDTO paymentCustomerDTO) {
        RequestPaymentMerchantDTO requestPaymentMerchantDTO = decodeQRCodeFromMerchant(paymentCustomerDTO.getFile());

        Merchant merchant = merchantService.getMerchantById(requestPaymentMerchantDTO.getMerchant().getId());
        Customer customer = customerService.getCustomerById(paymentCustomerDTO.getCustomer().getId());
        Wallet walletCustomer = walletService.getWalletById(customer.getWallet().getId());
        Wallet walletMerchant = walletService.getWalletById(merchant.getWallet().getId());

        Transaction paymentTransaction = new Transaction();
        paymentTransaction.setType(transactionTypeService.getOrSave(ETransactionType.PAY));
        paymentTransaction.setAmount(requestPaymentMerchantDTO.getAmount());
        paymentTransaction.setCustomer(customer);
        paymentTransaction.setMerchant(merchant);

        if(requestPaymentMerchantDTO.getCashback()==0) {
            transactionService.addTransaction(paymentTransaction);
        } else {
            TransactionCashback cashback = new TransactionCashback();
            cashback.setCustomer(customer);
            cashback.setMerchant(merchant);
            cashback.setQerisCointAmount(requestPaymentMerchantDTO.getCashback());
            cashbackService.addTransactionCashback(cashback);
            paymentTransaction.setCashback(cashback);
            walletCustomer.setQerisCoin(walletCustomer.getQerisCoin() + requestPaymentMerchantDTO.getCashback());
            walletMerchant.setQerisCoin(walletMerchant.getQerisCoin() - requestPaymentMerchantDTO.getCashback());
        }

        walletCustomer.setBalance(walletCustomer.getBalance() - requestPaymentMerchantDTO.getAmount());
        walletMerchant.setBalance(walletMerchant.getBalance() + requestPaymentMerchantDTO.getAmount());

        walletService.updateWallet(walletCustomer);
        walletService.updateWallet(walletMerchant);
    }
}
