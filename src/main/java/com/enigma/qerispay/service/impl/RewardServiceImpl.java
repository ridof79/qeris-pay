package com.enigma.qerispay.service.impl;

import com.enigma.qerispay.entiy.Reward;
import com.enigma.qerispay.repository.RewardRepository;
import com.enigma.qerispay.service.RewardService;
import com.enigma.qerispay.utils.constant.NotFoundConstant;
import com.enigma.qerispay.utils.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RewardServiceImpl implements RewardService {

    RewardRepository rewardRepository;

    @Override
    public Reward saveReward(Reward reward) {
        return rewardRepository.save(reward);
    }

    @Override
    public Reward updateReward(Reward reward) {
        if (rewardRepository.findById(reward.getId()).isPresent()) {
            return rewardRepository.save(reward);
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.REWARD_NOT_FOUND, reward.getId()));
        }
    }

    @Override
    public List<Reward> getAllReward() {
        return rewardRepository.findAll();
    }

    @Override
    public Reward getRewardById(String id) {
        if (rewardRepository.findById(id).isPresent()) {
            return rewardRepository.findById(id).get();
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.REWARD_NOT_FOUND, id));
        }
    }

    @Override
    public void deleteReward(String id) {
        if (rewardRepository.findById(id).isPresent()) {
            rewardRepository.deleteById(id);
        } else {
            throw new DataNotFoundException(String.format(NotFoundConstant.REWARD_NOT_FOUND, id));
        }
    }
}
