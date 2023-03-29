package com.enigma.qerispay.service.transaction;

import com.enigma.qerispay.dto.transaction.PaymentDTO;
import com.enigma.qerispay.dto.transaction.PaymentCustomerDTO;
import com.enigma.qerispay.dto.transaction.PaymentMerchantDTO;
import com.enigma.qerispay.utils.customResponse.ResponseFile;
import com.enigma.qerispay.utils.exception.InsufficientBalanceException;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.IOException;

public interface PaymentService {
    ResponseFile requestPayment(PaymentMerchantDTO paymentMerchantDTO) throws IOException, WriterException;
    PaymentMerchantDTO decodeQRCodeFromMerchant(File qrCode);
    PaymentDTO makePayment(PaymentCustomerDTO paymentCustomerDTO) throws InsufficientBalanceException;
}
