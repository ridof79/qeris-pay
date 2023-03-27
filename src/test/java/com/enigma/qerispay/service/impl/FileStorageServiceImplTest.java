package com.enigma.qerispay.service.impl;

import com.enigma.qerispay.entiy.storage.FileStorage;
import com.enigma.qerispay.repository.storage.FileStorageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileStorageServiceImplTest {

    @Mock
    FileStorageRepository fileStorageRepository;

    @InjectMocks
    FileStorageServiceImpl fileStorageService;

    private FileStorage file;

    @BeforeEach
    void setUp() {
        String fileName = "test-file.txt";
        String contentType = "text/plain";
        String ID = "A01";
        byte[] data = "This is a test file.".getBytes();
        file = new FileStorage(ID,fileName, contentType, data);
    }

    @AfterEach
    void cleanUp (){
        file = new FileStorage();
    }

    @Test
    void getFile() {

    }
}