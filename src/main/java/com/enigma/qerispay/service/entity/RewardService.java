package com.enigma.qerispay.service.entity;

import com.enigma.qerispay.entiy.Reward;

import java.util.List;

public interface RewardService {
    Reward saveReward(Reward reward);
    Reward updateReward(Reward reward);
    List<Reward> getAllReward();
    Reward getRewardById(String id);
    void deleteReward(String id);
}
