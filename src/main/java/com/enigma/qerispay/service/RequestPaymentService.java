package com.enigma.qerispay.service;

import com.enigma.qerispay.dto.RequestPaymentCustomerDTO;
import com.enigma.qerispay.dto.RequestPaymentMerchantDTO;

public interface RequestPaymentService {
    String requestPayment(RequestPaymentMerchantDTO requestPaymentMerchantDTO);
    RequestPaymentMerchantDTO decodeQRCodeFromMerchant(String qrCodeImage);
    void makePayment(RequestPaymentCustomerDTO paymentCustomerDTO);
}
