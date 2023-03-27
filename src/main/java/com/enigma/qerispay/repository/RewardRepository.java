package com.enigma.qerispay.repository;

import com.enigma.qerispay.entiy.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward, String> {
}
