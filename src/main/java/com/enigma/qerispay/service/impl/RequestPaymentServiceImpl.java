package com.enigma.qerispay.service.impl;

import com.enigma.qerispay.dto.ETransactionType;
import com.enigma.qerispay.dto.RequestPaymentCustomerDTO;
import com.enigma.qerispay.dto.RequestPaymentMerchantDTO;
import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Merchant;
import com.enigma.qerispay.entiy.Wallet;
import com.enigma.qerispay.entiy.transaction.Transaction;
import com.enigma.qerispay.qr.QRCodeGenerator;
import com.enigma.qerispay.service.*;
import com.enigma.qerispay.utils.constant.RequestQRFormat;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public String requestPayment(RequestPaymentMerchantDTO requestPaymentMerchantDTO) {

        String merchantId = requestPaymentMerchantDTO.getMerchant().getId();
        Integer amount = requestPaymentMerchantDTO.getAmount();
        String request = String.format(RequestQRFormat.REQUEST_PAYMENT_QR, merchantId, amount.toString());

        String imagePath = String.format(RequestQRFormat.QR_CODE_IMAGE_PATH, requestPaymentMerchantDTO.getMerchant().getId());

        try {
            QRCodeGenerator.generateQRCodeImage(request, 250, 250, imagePath);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        return imagePath;
    }

    @Override
    public RequestPaymentMerchantDTO decodeQRCodeFromMerchant(String qrCodeImage) {
        try {
            File file = new File(qrCodeImage);
            String[] resultDecode = decodeQRCode(file).split(",");

            return new RequestPaymentMerchantDTO(merchantService.getMerchantById(resultDecode[0]), Integer.parseInt(resultDecode[1]));

        } catch (IOException e) {
            System.out.println("Could not decode QR Code, IOException :: " + e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public void makePayment(RequestPaymentCustomerDTO paymentCustomerDTO) {
        RequestPaymentMerchantDTO requestPaymentMerchantDTO = decodeQRCodeFromMerchant(paymentCustomerDTO.getQrCodePath());

        Merchant merchant = merchantService.getMerchantById(requestPaymentMerchantDTO.getMerchant().getId());
        Customer customer = customerService.getCustomerById(paymentCustomerDTO.getCustomer().getId());
        Wallet walletCustomer = walletService.getWalletById(customer.getWallet().getId());
        Wallet walletMerchant = walletService.getWalletById(merchant.getWallet().getId());

        Transaction paymentTransaction = new Transaction();
        paymentTransaction.setType(transactionTypeService.getOrSave(ETransactionType.PAY));
        paymentTransaction.setAmount(requestPaymentMerchantDTO.getAmount());
        paymentTransaction.setCustomer(customer);
        paymentTransaction.setMerchant(merchant);
        transactionService.addTransaction(paymentTransaction);

        walletCustomer.setBalance(walletCustomer.getBalance() - requestPaymentMerchantDTO.getAmount());
        walletMerchant.setBalance(walletMerchant.getBalance() + requestPaymentMerchantDTO.getAmount());

        walletService.updateWallet(walletCustomer);
        walletService.updateWallet(walletMerchant);
    }
}
