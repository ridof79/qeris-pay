package com.enigma.qerispay.entiy.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "mst_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileStorage {
    @Id
    @Column(name = "id_file")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String name;

    private String type;

    @Lob
    private byte[] data;

    public FileStorage(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
