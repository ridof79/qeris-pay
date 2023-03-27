package com.enigma.qerispay.entiy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "mst_reward")
@Getter
@Setter
@NoArgsConstructor
public class Reward {
    @Id
    @Column(name = "id_reward")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column
    private String rewardName;

    @Column
    private Integer rewardPrice;

}
