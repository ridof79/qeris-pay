package com.enigma.qerispay.dto.transaction;

import com.enigma.qerispay.entiy.transaction.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO {
    private String customerName;
    private String merchantName;
    private Integer amount;
    private Integer cashback;

    public PaymentDTO(Transaction transaction) {
        this.customerName = transaction.getCustomer().getCustomerName();
        this.merchantName = transaction.getMerchant().getMerchantName();
        this.amount = transaction.getAmount();
        if (transaction.getCashback() == null) {
            this.cashback =  0;
        } else {
            this.cashback = transaction.getCashback().getQerisCointAmount();
        }
    }
}
