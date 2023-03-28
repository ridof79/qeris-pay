package com.enigma.qerispay.controller;

import com.enigma.qerispay.dto.TopUpDTO;
import com.enigma.qerispay.dto.TopUpMerchantDTO;
import com.enigma.qerispay.service.TopUpService;
import com.enigma.qerispay.utils.constant.ApiUrlConstant;
import com.enigma.qerispay.utils.constant.InsertDataConstant;
import com.enigma.qerispay.utils.customResponse.Response;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrlConstant.TOP_UP_PATH)
@AllArgsConstructor
@SecurityRequirement(name = "Authorization")
public class TopUpController {
    TopUpService topUpService;

    @PostMapping("/customer")
    public ResponseEntity<Response<TopUpDTO>> saveMerchant(@RequestBody TopUpDTO topUpDTO) {
        Response<TopUpDTO> response = new Response<>();
        response.setMessage(InsertDataConstant.INSERT_BALANCE_SUCCES);
        response.setData(topUpService.customerTopUp(topUpDTO));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/merchant")
    public ResponseEntity<Response<TopUpMerchantDTO>> saveMerchant(@RequestBody TopUpMerchantDTO topUpMerchantDTO) {
        Response<TopUpMerchantDTO> response = new Response<>();
        response.setMessage(InsertDataConstant.INSERT_BALANCE_SUCCES);
        response.setData(topUpService.merchantTopUp(topUpMerchantDTO));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
