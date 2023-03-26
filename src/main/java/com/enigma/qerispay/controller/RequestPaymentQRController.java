package com.enigma.qerispay.controller;

import com.enigma.qerispay.dto.RequestPaymentCustomerDTO;
import com.enigma.qerispay.dto.RequestPaymentMerchantDTO;
import com.enigma.qerispay.service.RequestPaymentService;
import com.enigma.qerispay.utils.constant.ApiUrlConstant;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrlConstant.PAYMENT_QR_PATH)
@AllArgsConstructor
public class RequestPaymentQRController {

    RequestPaymentService requestPaymentService;

    @PostMapping
    public String requestPayment(@RequestBody RequestPaymentMerchantDTO requestPaymentMerchantDTO) {
        return requestPaymentService.requestPayment(requestPaymentMerchantDTO);
    }

    @GetMapping
    public void makePayment(@RequestBody RequestPaymentCustomerDTO requestPaymentCustomerDTO) {
        requestPaymentService.makePayment(requestPaymentCustomerDTO);
    }
}
