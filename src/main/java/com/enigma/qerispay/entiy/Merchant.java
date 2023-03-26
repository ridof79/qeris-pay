package com.enigma.qerispay.entiy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "mst_merchant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {
    @Id
    @Column(name = "id_merchant")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false)
    private String merchantName;

    @Column(nullable = false, unique = true)
    private String merchantEmail;

    @Column(nullable = false)
    private String password;

    @Column
    private String merchantAddress;

    @Column
    private String merchantPhone;

    @Column
    private String merchantNIB;

    @OneToOne
    @JoinColumn(name = "id_wallet")
    private Wallet wallet;
}
