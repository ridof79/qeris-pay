package com.enigma.qerispay.service.impl.transaction;

import com.enigma.qerispay.dto.ETransactionType;
import com.enigma.qerispay.dto.transaction.PaymentDTO;
import com.enigma.qerispay.dto.transaction.RequestPaymentCustomerDTO;
import com.enigma.qerispay.dto.transaction.RequestPaymentMerchantDTO;
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
import com.enigma.qerispay.service.transaction.RequestPaymentService;
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

        Wallet merchantWallet = walletService.getWalletById(requestPaymentMerchantDTO.getMerchant().getId());
        if (!(merchantWallet.getQerisCoin() < getCashback(requestPaymentMerchantDTO))) {
            String request = String.format(
                    RequestQRFormat.REQUEST_PAYMENT_QR,
                    requestPaymentMerchantDTO.getMerchant().getId(),
                    requestPaymentMerchantDTO.getAmount().toString(),
                    getCashback(requestPaymentMerchantDTO)
            );

            String fileName = String.format(RequestQRFormat.QR_CODE_FILENAME,
                    requestPaymentMerchantDTO.getMerchant().getId(),
                    requestPaymentMerchantDTO.getAmount(),
                    requestPaymentMerchantDTO.getCashback()
            );

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
        } else {
            throw new InsufficientBalanceException(TransactionConstant.INSUFFICIENT_QERISCOIN_BALANCE);
        }
    }


    @Override
    public RequestPaymentMerchantDTO decodeQRCodeFromMerchant(File file) {
        try {
            String[] resultDecode = decodeQRCode(file).split(",");
            return new RequestPaymentMerchantDTO(
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
    public PaymentDTO makePayment(RequestPaymentCustomerDTO paymentCustomerDTO) {
        RequestPaymentMerchantDTO requestPaymentMerchantDTO = decodeQRCodeFromMerchant(paymentCustomerDTO.getFile());

        Merchant merchant = merchantService.getMerchantById(requestPaymentMerchantDTO.getMerchant().getId());
        Customer customer = customerService.getCustomerById(paymentCustomerDTO.getCustomer().getId());
        Wallet walletCustomer = walletService.getWalletById(customer.getWallet().getId());
        Wallet walletMerchant = walletService.getWalletById(merchant.getWallet().getId());

        if (!(walletCustomer.getBalance()<requestPaymentMerchantDTO.getAmount())) {
            Transaction paymentTransaction = new Transaction();
            paymentTransaction.setType(transactionTypeService.getOrSave(ETransactionType.PAY));
            paymentTransaction.setAmount(requestPaymentMerchantDTO.getAmount());
            paymentTransaction.setCustomer(customer);
            paymentTransaction.setMerchant(merchant);

            if (requestPaymentMerchantDTO.getCashback() == 0) {
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
            transactionService.addTransaction(paymentTransaction);
            return new PaymentDTO(paymentTransaction);
        } else {
            throw new InsufficientBalanceException(TransactionConstant.INSUFFICIENT_BALANCE);
        }
    }

    private Integer getCashback(RequestPaymentMerchantDTO requestPaymentMerchantDTO) {
        Double cashback = null;
        if (requestPaymentMerchantDTO.getCashback() != null) {
            cashback = requestPaymentMerchantDTO.getCashback().doubleValue();
            cashback = (cashback / 100) * requestPaymentMerchantDTO.getCashback();
        } else {
            cashback = 0.0;
        }
        return cashback.intValue();
    }
}
