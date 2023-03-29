package com.enigma.qerispay.service.impl.transaction;

import com.enigma.qerispay.dto.ETransactionType;
import com.enigma.qerispay.dto.transaction.RewardDTO;
import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Reward;
import com.enigma.qerispay.entiy.Wallet;
import com.enigma.qerispay.entiy.transaction.Transaction;
import com.enigma.qerispay.service.entity.CustomerService;
import com.enigma.qerispay.service.entity.RewardService;
import com.enigma.qerispay.service.entity.WalletService;
import com.enigma.qerispay.service.transaction.TransactionRewardService;
import com.enigma.qerispay.service.transaction.TransactionService;
import com.enigma.qerispay.service.transaction.TransactionTypeService;
import com.enigma.qerispay.utils.constant.TransactionConstant;
import com.enigma.qerispay.utils.exception.InsufficientBalanceException;
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
    public RewardDTO claimReward(RewardDTO rewardDTO) {
        Transaction clamReward = new Transaction();
        Customer customer = customerService.getCustomerById(rewardDTO.getCustomerId());
        Reward reward = rewardService.getRewardById(rewardDTO.getReward().getId());
        Wallet wallet = walletService.getWalletById(customer.getWallet().getId());

        if (!(wallet.getBalance() < reward.getRewardPrice())) {

            clamReward.setCustomer(customer);
            clamReward.setAmount(rewardDTO.getReward().getRewardPrice());
            clamReward.setType(transactionTypeService.getOrSave(ETransactionType.CLAIM));
            clamReward.setDescription(String.format(TransactionConstant.CLAIM_REWARD_SUCCESS, customer.getCustomerName(), reward.getRewardName()));
            transactionService.addTransaction(clamReward);

            wallet.setQerisCoin(wallet.getQerisCoin() - reward.getRewardPrice());
            walletService.updateWallet(wallet);

            return rewardDTO;
        } else {
            throw new InsufficientBalanceException(TransactionConstant.INSUFFICIENT_QERISCOIN_BALANCE);
        }
    }
}
