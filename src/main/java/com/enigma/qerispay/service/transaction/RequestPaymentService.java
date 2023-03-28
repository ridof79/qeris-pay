package com.enigma.qerispay.service.transaction;

import com.enigma.qerispay.dto.transaction.PaymentDTO;
import com.enigma.qerispay.dto.transaction.RequestPaymentCustomerDTO;
import com.enigma.qerispay.dto.transaction.RequestPaymentMerchantDTO;
import com.enigma.qerispay.utils.customResponse.ResponseFile;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.IOException;

public interface RequestPaymentService {
    ResponseFile requestPayment(RequestPaymentMerchantDTO requestPaymentMerchantDTO) throws IOException, WriterException;
    RequestPaymentMerchantDTO decodeQRCodeFromMerchant(File qrCode);
    PaymentDTO makePayment(RequestPaymentCustomerDTO paymentCustomerDTO);
}
