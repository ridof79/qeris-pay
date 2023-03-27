package com.enigma.qerispay.service.impl;

import com.enigma.qerispay.dto.ETransactionType;
import com.enigma.qerispay.dto.RewardDTO;
import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Reward;
import com.enigma.qerispay.entiy.Wallet;
import com.enigma.qerispay.entiy.transaction.Transaction;
import com.enigma.qerispay.service.*;
import com.enigma.qerispay.utils.constant.TransactionConstant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionRewardServiceImpl implements TransactionRewardService {

    WalletService walletService;
    CustomerService customerService;
    TransactionService transactionService;
    TransactionTypeService transactionTypeService;
    RewardService rewardService;

    @Override
    public void claimReward(RewardDTO rewardDTO) {
        Transaction clamReward = new Transaction();
        Customer customer = customerService.getCustomerById(rewardDTO.getCustomer().getId());
        Reward reward = rewardService.getRewardById(rewardDTO.getReward().getId());
        Wallet wallet = walletService.getWalletById(customer.getWallet().getId());

        clamReward.setCustomer(customer);
        clamReward.setAmount(rewardDTO.getReward().getRewardPrice());
        clamReward.setType(transactionTypeService.getOrSave(ETransactionType.CLAIM));
        clamReward.setDescription(String.format(TransactionConstant.CLAIM_REWARD_SUCCES, customer.getCustomerName(), reward.getRewardName()));
        transactionService.addTransaction(clamReward);

        wallet.setQerisCoin(wallet.getQerisCoin()-reward.getRewardPrice());
        walletService.updateWallet(wallet);
    }
}
