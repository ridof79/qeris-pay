package com.enigma.qerispay.service.impl.transaction;

import com.enigma.qerispay.dto.ETransactionType;
import com.enigma.qerispay.dto.transaction.PaymentDTO;
import com.enigma.qerispay.dto.transaction.PaymentCustomerDTO;
import com.enigma.qerispay.dto.transaction.PaymentMerchantDTO;
import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Merchant;
import com.enigma.qerispay.entiy.Wallet;
import com.enigma.qerispay.entiy.storage.FileStorage;
import com.enigma.qerispay.entiy.transaction.Transaction;
import com.enigma.qerispay.entiy.transaction.TransactionCashback;
import com.enigma.qerispay.qr.QRCodeGenerator;

import com.enigma.qerispay.service.entity.CustomerService;
import com.enigma.qerispay.service.entity.FIleStorageService;
import com.enigma.qerispay.service.entity.MerchantService;
import com.enigma.qerispay.service.entity.WalletService;
import com.enigma.qerispay.service.transaction.PaymentService;
import com.enigma.qerispay.service.transaction.TransactionCashbackService;
import com.enigma.qerispay.service.transaction.TransactionService;
import com.enigma.qerispay.service.transaction.TransactionTypeService;
import com.enigma.qerispay.utils.constant.RequestQRFormat;
import com.enigma.qerispay.utils.constant.TransactionConstant;
import com.enigma.qerispay.utils.customResponse.ResponseFile;
import com.enigma.qerispay.utils.exception.InsufficientBalanceException;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;

import static com.enigma.qerispay.qr.QRCodeGenerator.decodeQRCode;
import static com.enigma.qerispay.qr.StringDecoder.*;


@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    MerchantService merchantService;
    WalletService walletService;
    CustomerService customerService;
    TransactionService transactionService;
    TransactionTypeService transactionTypeService;
    FIleStorageService fIleStorageService;
    TransactionCashbackService cashbackService;

    @Override
    public ResponseFile requestPayment(PaymentMerchantDTO paymentMerchantDTO) throws IOException, WriterException {

        Wallet merchantWallet = merchantService.getMerchantById(paymentMerchantDTO.getMerchant().getId()).getWallet();
        if (!(merchantWallet.getQerisCoin() < getCashback(paymentMerchantDTO))) {
            String request = String.format(
                    RequestQRFormat.REQUEST_PAYMENT_QR,
                    paymentMerchantDTO.getMerchant().getId(),
                    paymentMerchantDTO.getAmount().toString(),
                    getCashback(paymentMerchantDTO)
            );

            String fileName = String.format(RequestQRFormat.QR_CODE_FILENAME,
                    paymentMerchantDTO.getMerchant().getId(),
                    paymentMerchantDTO.getAmount(),
                    getCashback(paymentMerchantDTO)
            );

            byte[] pngData = QRCodeGenerator.getQRCodeImage(encryptAES(request), 250, 250);
            FileStorage qr = new FileStorage(fileName, "image/png", pngData);
            fIleStorageService.addFile(qr);

            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/download/")
                    .path(qr.getId())
                    .toUriString();

            return new ResponseFile(
                    qr.getName(),
                    fileDownloadUri,
                    qr.getType(),
                    qr.getData().length);
        } else {
            throw new InsufficientBalanceException(TransactionConstant.INSUFFICIENT_QERISCOIN_BALANCE);
        }
    }


    @Override
    public PaymentMerchantDTO decodeQRCodeFromMerchant(File file) {
        try {

            String[] resultDecode = decryptAES(decodeQRCode(file)).split(",");
            return new PaymentMerchantDTO(
                    merchantService.getMerchantById(resultDecode[0]),
                    Integer.parseInt(resultDecode[1]),
                    Integer.parseInt(resultDecode[2])
            );

        } catch (IOException e) {
            System.out.printf(TransactionConstant.DECODE_QR_CODE_IOEXCEPTION + e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public PaymentDTO makePayment(PaymentCustomerDTO paymentCustomerDTO) throws InsufficientBalanceException{
        PaymentMerchantDTO paymentMerchantDTO = decodeQRCodeFromMerchant(paymentCustomerDTO.getFile());

        Merchant merchant = merchantService.getMerchantById(paymentMerchantDTO.getMerchant().getId());
        Customer customer = customerService.getCustomerById(paymentCustomerDTO.getCustomer().getId());
        Wallet walletCustomer = walletService.getWalletById(customer.getWallet().getId());
        Wallet walletMerchant = walletService.getWalletById(merchant.getWallet().getId());

        if (!(walletCustomer.getBalance()< paymentMerchantDTO.getAmount())) {
            Transaction paymentTransaction = new Transaction();
            paymentTransaction.setType(transactionTypeService.getOrSave(ETransactionType.PAY));
            paymentTransaction.setAmount(paymentMerchantDTO.getAmount());
            paymentTransaction.setCustomer(customer);
            paymentTransaction.setMerchant(merchant);

            if (paymentMerchantDTO.getCashback() == null || paymentMerchantDTO.getCashback() == 0) {
                transactionService.addTransaction(paymentTransaction);
            } else {
                TransactionCashback cashback = new TransactionCashback();
                cashback.setCustomer(customer);
                cashback.setMerchant(merchant);
                cashback.setQerisCointAmount(paymentMerchantDTO.getCashback());
                cashbackService.addTransactionCashback(cashback);
                paymentTransaction.setCashback(cashback);
                walletCustomer.setQerisCoin(walletCustomer.getQerisCoin() + paymentMerchantDTO.getCashback());
                walletMerchant.setQerisCoin(walletMerchant.getQerisCoin() - paymentMerchantDTO.getCashback());
            }

            walletCustomer.setBalance(walletCustomer.getBalance() - paymentMerchantDTO.getAmount());
            walletMerchant.setBalance(walletMerchant.getBalance() + paymentMerchantDTO.getAmount());

            walletService.updateWallet(walletCustomer);
            walletService.updateWallet(walletMerchant);
            transactionService.addTransaction(paymentTransaction);
            return new PaymentDTO(paymentTransaction);
        } else {
            throw new InsufficientBalanceException(TransactionConstant.INSUFFICIENT_BALANCE);
        }
    }

    private Integer getCashback(PaymentMerchantDTO paymentMerchantDTO) {
        Double cashback = null;
        if (paymentMerchantDTO.getCashback() != null) {
            cashback = paymentMerchantDTO.getCashback().doubleValue();
            cashback = (cashback / 100) * paymentMerchantDTO.getAmount();
        } else {
            cashback = 0.0;
        }
        return cashback.intValue();
    }
}
