package com.enigma.qerispay.service;

import com.enigma.qerispay.dto.RequestPaymentCustomerDTO;
import com.enigma.qerispay.dto.RequestPaymentMerchantDTO;
import com.enigma.qerispay.utils.customResponse.ResponseFile;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.IOException;

public interface RequestPaymentService {
    ResponseFile requestPayment(RequestPaymentMerchantDTO requestPaymentMerchantDTO) throws IOException, WriterException;
    RequestPaymentMerchantDTO decodeQRCodeFromMerchant(File qrCode);
    void makePayment(RequestPaymentCustomerDTO paymentCustomerDTO);
}
