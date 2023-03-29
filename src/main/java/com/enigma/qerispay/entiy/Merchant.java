package com.enigma.qerispay.entiy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "mst_merchant")
@DiscriminatorValue("merchant")
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

    @Column(name = "merchant_NIB", unique = true)
    private String merchantNIB;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_wallet")
    private Wallet wallet;
}
