package com.enigma.qerispay.service;

import com.enigma.qerispay.entiy.storage.FileStorage;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public interface FIleStorageService {
    FileStorage store(MultipartFile file) throws IOException;
    FileStorage getFile(String id) throws IOException;
    Stream<FileStorage> getAllFiles();
    FileStorage addFile(FileStorage file) throws IOException;
    File getFileFromDb(String id) throws IOException;
}
