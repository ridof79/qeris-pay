package com.enigma.qerispay.service.impl;

import com.enigma.qerispay.entiy.storage.FileStorage;
import com.enigma.qerispay.repository.storage.FileStorageRepository;
import com.enigma.qerispay.service.FIleStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class FileStorageServiceImpl implements FIleStorageService {

    FileStorageRepository fileStorageRepository;

    @Override
    public FileStorage store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileStorage fileStorage = new FileStorage(fileName, file.getContentType(), file.getBytes());

        return fileStorageRepository.save(fileStorage);
    }

    @Override
    public FileStorage getFile(String id) {
        return fileStorageRepository.findById(id).get();
    }

    @Override
    public Stream<FileStorage> getAllFiles() {
        return fileStorageRepository.findAll().stream();
    }

    @Override
    public FileStorage addFile(FileStorage file) throws IOException {
        return fileStorageRepository.save(file);
    }

    @Override
    public File getFileFromDb(String id) throws IOException {
        FileStorage fileStorage = fileStorageRepository.findById(id).orElseThrow(() -> new FileNotFoundException("File not found with id " + id));
        String filename = fileStorage.getName();
        String tempFileName = System.getProperty("java.io.tmpdir") + "/" + filename;
        File file = new File(tempFileName);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(fileStorage.getData());
        }
        return file;

    }
}
