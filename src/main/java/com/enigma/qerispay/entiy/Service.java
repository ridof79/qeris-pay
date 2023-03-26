package com.enigma.qerispay.entiy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "mst_service")
@Getter
@Setter
@NoArgsConstructor
public class Service {
    @Id
    @Column(name = "id_service")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column
    private String serviceName;

    @Column
    private Integer servicePrice;

}
