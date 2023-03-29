package com.enigma.qerispay.service.transaction;

import com.enigma.qerispay.dto.transaction.RewardDTO;

public interface TransactionRewardService {
    RewardDTO claimReward(RewardDTO rewardDTO);
}
