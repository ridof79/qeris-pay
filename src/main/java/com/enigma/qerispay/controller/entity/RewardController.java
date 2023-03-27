package com.enigma.qerispay.controller.entity;

import com.enigma.qerispay.dto.RewardDTO;
import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Merchant;
import com.enigma.qerispay.entiy.Reward;
import com.enigma.qerispay.service.RewardService;
import com.enigma.qerispay.service.TransactionRewardService;
import com.enigma.qerispay.utils.constant.ApiUrlConstant;
import com.enigma.qerispay.utils.constant.InsertDataConstant;
import com.enigma.qerispay.utils.customResponse.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.REWARD_PATH)
@AllArgsConstructor
public class RewardController {

    RewardService rewardService;
    TransactionRewardService transactionRewardService;

    @PostMapping
    public ResponseEntity<Response<Reward>> saveReward(@RequestBody Reward reward) {
        Response<Reward> response = new Response<>();
        response.setMessage(InsertDataConstant.INSERT_REWARD_SUCCES);
        response.setData(rewardService.saveReward(reward));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping
    public Reward updateReward(@RequestBody Reward reward) {
        return rewardService.updateReward(reward);
    }

    @DeleteMapping("/{id}")
    public void deleteRewardById(@PathVariable String id) {
        rewardService.deleteReward(id);
    }

    @GetMapping("/list")
    public List<Reward> getAllReward() {
        return rewardService.getAllReward();
    }

    @GetMapping("/{id}")
    public Reward getRewardById(@PathVariable String id) {
        return rewardService.getRewardById(id);
    }

    @PostMapping("/claim")
    public void claimReward(@RequestBody RewardDTO rewardDTO) {
        transactionRewardService.claimReward(rewardDTO);
    }
}
