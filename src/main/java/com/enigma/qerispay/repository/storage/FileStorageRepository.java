package com.enigma.qerispay.repository.storage;

import com.enigma.qerispay.entiy.storage.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileStorageRepository extends JpaRepository<FileStorage, String> {
}
