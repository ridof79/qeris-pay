package com.enigma.qerispay.service;

import com.enigma.qerispay.dto.SendBalanceDTO;

public interface CustomerSendBalanceService {
    SendBalanceDTO sendBalance(SendBalanceDTO sendBalanceDTO);
}
