package com.enigma.qerispay.service.transaction;

import com.enigma.qerispay.dto.transaction.TopUpDTO;
import com.enigma.qerispay.dto.transaction.TopUpMerchantDTO;

public interface TopUpService {
    TopUpDTO customerTopUp(TopUpDTO topUpDTO);
    TopUpMerchantDTO merchantTopUp(TopUpMerchantDTO topUpMerchantDTO);
}
