package com.enigma.qerispay.controller;

import com.enigma.qerispay.dto.SendBalanceDTO;
import com.enigma.qerispay.dto.TopUpDTO;
import com.enigma.qerispay.service.CustomerSendBalanceService;
import com.enigma.qerispay.utils.constant.ApiUrlConstant;
import com.enigma.qerispay.utils.constant.InsertDataConstant;
import com.enigma.qerispay.utils.constant.TransactionConstant;
import com.enigma.qerispay.utils.customResponse.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrlConstant.SEND_BALANCE_PATH)
@AllArgsConstructor
public class SendBalanceController {
    CustomerSendBalanceService sendBalanceService;

    @PostMapping
    public ResponseEntity<Response<SendBalanceDTO>> saveMerchant(@RequestBody SendBalanceDTO sendBalanceDTO) {
        Response<SendBalanceDTO> response = new Response<>();
        response.setMessage(TransactionConstant.TRANSFER_BALANCE_SUCCESS);
        response.setData(sendBalanceService.sendBalance(sendBalanceDTO));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
