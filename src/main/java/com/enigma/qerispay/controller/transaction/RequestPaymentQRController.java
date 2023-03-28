package com.enigma.qerispay.controller.transaction;

import com.enigma.qerispay.dto.RequestPaymentCustomerDTO;
import com.enigma.qerispay.dto.RequestPaymentMerchantDTO;
import com.enigma.qerispay.entiy.storage.FileStorage;
import com.enigma.qerispay.service.CustomerService;
import com.enigma.qerispay.service.FIleStorageService;
import com.enigma.qerispay.service.RequestPaymentService;
import com.enigma.qerispay.utils.constant.ApiUrlConstant;
import com.enigma.qerispay.utils.customResponse.ResponseFile;
import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(ApiUrlConstant.PAYMENT_QR_PATH)
@AllArgsConstructor
@SecurityRequirement(name = "Authorization")
public class RequestPaymentQRController {

    private final FIleStorageService storageService;
    RequestPaymentService requestPaymentService;
    CustomerService customerService;

    @PostMapping
    public ResponseFile requestPayment(@RequestBody RequestPaymentMerchantDTO requestPaymentMerchantDTO) throws IOException, WriterException {
        return requestPaymentService.requestPayment(requestPaymentMerchantDTO);
    }

    @PostMapping("/pay")
    public void makePayment(@RequestParam("file") MultipartFile file,
                            @RequestParam String customerID) throws IOException {

        FileStorage storedFile = storageService.store(file);
        String id = storedFile.getId();

        RequestPaymentCustomerDTO requestPaymentCustomerDto = new RequestPaymentCustomerDTO(
                customerService.getCustomerById(customerID), storageService.getFileFromDb(id)
        );

        requestPaymentService.makePayment(requestPaymentCustomerDto);
    }
}
