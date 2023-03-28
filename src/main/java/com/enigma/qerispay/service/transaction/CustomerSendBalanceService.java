package com.enigma.qerispay.service.transaction;

import com.enigma.qerispay.dto.transaction.SendBalanceDTO;

public interface CustomerSendBalanceService {
    SendBalanceDTO sendBalance(SendBalanceDTO sendBalanceDTO);
}
