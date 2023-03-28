package com.enigma.qerispay.entiy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

@Entity
@DiscriminatorValue("mst_customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User{

    @Column
    private String customerName;

    @Column(unique = true)
    private String customerEmail;

    @Column
    private String customerAddress;

    @Column(unique = true)
    private String customerPhone;

    @Column
    private Date birthDate;

    @OneToOne
    @JoinColumn(name = "id_wallet")
    private Wallet wallet;
}
