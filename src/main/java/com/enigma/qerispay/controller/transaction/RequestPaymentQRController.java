package com.enigma.qerispay.controller.transaction;

import com.enigma.qerispay.dto.transaction.PaymentDTO;
import com.enigma.qerispay.dto.transaction.RequestPaymentCustomerDTO;
import com.enigma.qerispay.dto.transaction.RequestPaymentMerchantDTO;
import com.enigma.qerispay.entiy.storage.FileStorage;
import com.enigma.qerispay.service.entity.CustomerService;
import com.enigma.qerispay.service.entity.FIleStorageService;
import com.enigma.qerispay.service.transaction.RequestPaymentService;
import com.enigma.qerispay.utils.constant.ApiUrlConstant;
import com.enigma.qerispay.utils.constant.TransactionConstant;
import com.enigma.qerispay.utils.customResponse.Response;
import com.enigma.qerispay.utils.customResponse.ResponseFile;
import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Response<PaymentDTO>> makePayment(@RequestParam("file") MultipartFile file,
                                                            @RequestParam String customerID) throws IOException {
        Response<PaymentDTO> response = new Response<>();
        FileStorage storedFile = storageService.store(file);

        RequestPaymentCustomerDTO requestPaymentCustomerDto = new RequestPaymentCustomerDTO(
                customerService.getCustomerById(customerID), storageService.getFileFromDb(storedFile.getId())
        );

        response.setMessage(TransactionConstant.PAYMENT_SUCCESS);
        response.setData(requestPaymentService.makePayment(requestPaymentCustomerDto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
