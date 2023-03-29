package com.enigma.qerispay.controller.transaction;

import com.enigma.qerispay.auth.CustomUserDetails;
import com.enigma.qerispay.dto.transaction.PaymentDTO;
import com.enigma.qerispay.dto.transaction.PaymentCustomerDTO;
import com.enigma.qerispay.dto.transaction.PaymentMerchantDTO;
import com.enigma.qerispay.entiy.storage.FileStorage;
import com.enigma.qerispay.service.entity.CustomerService;
import com.enigma.qerispay.service.entity.FIleStorageService;
import com.enigma.qerispay.service.transaction.PaymentService;
import com.enigma.qerispay.utils.constant.ApiUrlConstant;
import com.enigma.qerispay.utils.constant.ControllerConstant;
import com.enigma.qerispay.utils.constant.TransactionConstant;
import com.enigma.qerispay.utils.customResponse.Response;
import com.enigma.qerispay.utils.customResponse.ResponseFile;
import com.enigma.qerispay.utils.exception.InsufficientBalanceException;
import com.enigma.qerispay.utils.exception.UnauthorizedException;
import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(ApiUrlConstant.PAYMENT_QR_PATH)
@AllArgsConstructor
@SecurityRequirement(name = "Authorization")
public class PaymentQRController {

    private final FIleStorageService storageService;
    PaymentService paymentService;
    CustomerService customerService;

    @PostMapping("/request")
    public ResponseFile requestPayment(@RequestBody PaymentMerchantDTO paymentMerchantDTO,
                                       Authentication authentication) throws IOException, WriterException {
        if (paymentMerchantDTO.getMerchant().getId().equals(((CustomUserDetails) authentication.getPrincipal()).getId())) {
            return paymentService.requestPayment(paymentMerchantDTO);
        } else {
            throw new UnauthorizedException(ControllerConstant.UNAUTHORIZED);
        }
    }

    @PostMapping("/pay")
    public ResponseEntity<Response<PaymentDTO>> makePayment(@RequestParam("file") MultipartFile file,
                                                            Authentication authentication) throws IOException, InsufficientBalanceException {
        String id = ((CustomUserDetails) authentication.getPrincipal()).getId();
        Response<PaymentDTO> response = new Response<>();
        FileStorage storedFile = storageService.store(file);

        PaymentCustomerDTO paymentCustomerDto = new PaymentCustomerDTO(
                customerService.getCustomerById(id), storageService.getFileFromDb(storedFile.getId())
        );

        response.setMessage(TransactionConstant.PAYMENT_SUCCESS);
        response.setData(paymentService.makePayment(paymentCustomerDto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
