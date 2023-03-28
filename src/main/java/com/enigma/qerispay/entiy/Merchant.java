package com.enigma.qerispay.entiy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

@Entity
@DiscriminatorValue("mst_merchant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Merchant extends User {

    @Column
    private String merchantName;

    @Column(unique = true)
    private String merchantEmail;

    @Column
    private String merchantAddress;

    @Column(unique = true)
    private String merchantPhone;

    @Column(unique = true)
    private String merchantNIB;

    @OneToOne
    @JoinColumn(name = "id_wallet")
    private Wallet wallet;
}
