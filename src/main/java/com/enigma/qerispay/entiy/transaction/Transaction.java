package com.enigma.qerispay.entiy.transaction;

import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Merchant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name =  "trx_transaction")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @Column(name = "id_transaction")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column
    private Date date;

    @OneToOne
    @JoinColumn(name = "id_transaction_type")
    private TransactionType type;

    @OneToOne
    @JoinColumn(name = "id_transaction_cashback")
    private TransactionCashback cashback;

    @Column
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "id_merchant")
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @Column
    private String description;
}
