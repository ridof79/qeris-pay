package com.enigma.qerispay.controller.transaction;

import com.enigma.qerispay.auth.CustomUserDetails;
import com.enigma.qerispay.dto.transaction.RewardDTO;
import com.enigma.qerispay.dto.transaction.SendBalanceDTO;
import com.enigma.qerispay.service.entity.CustomerService;
import com.enigma.qerispay.service.transaction.CustomerSendBalanceService;
import com.enigma.qerispay.service.transaction.TransactionRewardService;
import com.enigma.qerispay.utils.constant.ApiUrlConstant;
import com.enigma.qerispay.utils.constant.ControllerConstant;
import com.enigma.qerispay.utils.constant.TransactionConstant;
import com.enigma.qerispay.utils.customResponse.Response;
import com.enigma.qerispay.utils.exception.UnauthorizedException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrlConstant.SEND_BALANCE_PATH)
@AllArgsConstructor
@SecurityRequirement(name = "Authorization")
public class TransactionCustomerController {

    CustomerSendBalanceService sendBalanceService;
    TransactionRewardService transactionRewardService;
    CustomerService customerService;

    @PostMapping("/send-balance")
    public ResponseEntity<Response<SendBalanceDTO>> sendBalance(@RequestBody SendBalanceDTO sendBalanceDTO,
                                                                Authentication authentication) {
        if (sendBalanceDTO.getSenderId().equals(((CustomUserDetails) authentication.getPrincipal()).getId())) {
            Response<SendBalanceDTO> response = new Response<>();
            response.setMessage(TransactionConstant.TRANSFER_BALANCE_SUCCESS);
            response.setData(sendBalanceService.sendBalance(sendBalanceDTO));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
        else {
            throw new UnauthorizedException(ControllerConstant.UNAUTHORIZED);
        }
    }

    @PostMapping("/claim-reward")
    public ResponseEntity<?> claimReward(@RequestBody RewardDTO rewardDTO,
                            Authentication authentication) {
        if (rewardDTO.getCustomerId().equals(((CustomUserDetails) authentication.getPrincipal()).getId())) {
            RewardDTO responseReward = transactionRewardService.claimReward(rewardDTO);
            Response<?> response = new Response<>(String.format
                    (TransactionConstant.CLAIM_REWARD_SUCCESS,
                            rewardDTO.getCustomerId(),
                            rewardDTO.getReward().getRewardName()),
                    responseReward);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            throw new UnauthorizedException(ControllerConstant.UNAUTHORIZED);
        }
    }
}
