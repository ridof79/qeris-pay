package com.enigma.qerispay.service;

import com.enigma.qerispay.entiy.storage.FileStorage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

public interface FIleStorageService {
    FileStorage store(MultipartFile file) throws IOException;
    FileStorage getFile(String id);
    Stream<FileStorage> getAllFiles();
}
