package com.enigma.qerispay.service;

import com.enigma.qerispay.dto.RewardDTO;

public interface TransactionRewardService {
    void claimReward(RewardDTO rewardDTO);
}
