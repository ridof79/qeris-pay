package com.enigma.qerispay.dto.transaction;

import com.enigma.qerispay.entiy.Reward;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardDTO {
    private String customerId;
    private Reward reward;
}
