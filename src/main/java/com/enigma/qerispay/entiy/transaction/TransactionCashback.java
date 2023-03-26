package com.enigma.qerispay.entiy.transaction;

import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Merchant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name =  "trx_transaction_cashback")
@Getter
@Setter
@NoArgsConstructor
public class TransactionCashback {
    @Id
    @Column(name = "id_transaction_cashback")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_merchant")
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    private Integer qerisCointAmount;
}
