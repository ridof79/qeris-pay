package com.enigma.qerispay.entiy.transaction;

import com.enigma.qerispay.dto.ETransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name =  "mst_transaction_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionType {
    @Id
    @Column(name = "id_transaction_type")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Enumerated(EnumType.STRING)
    private ETransactionType transactionType;
}
