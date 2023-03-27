package com.enigma.qerispay.entiy.transaction;

import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Reward;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name =  "trx_transaction_qeris")
@Getter
@Setter
@NoArgsConstructor
public class TransactionQeris {
    @Id
    @Column(name = "id_transaction_qeris")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @Column
    private Date date;

    @ManyToOne
    @JoinColumn(name = "id_reward")
    private Reward reward;

    private Integer serviceTransactionPrice;
}
