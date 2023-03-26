package com.enigma.qerispay.service;

import com.enigma.qerispay.dto.TopUpDTO;
import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.transaction.Transaction;

public interface TopUpService {
    TopUpDTO customerTopUp(TopUpDTO topUpDTO);
}
