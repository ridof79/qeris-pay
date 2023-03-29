package com.enigma.qerispay.controller.entity;

import com.enigma.qerispay.entiy.storage.FileStorage;
import com.enigma.qerispay.service.entity.FIleStorageService;
import com.enigma.qerispay.utils.constant.ApiUrlConstant;
import com.enigma.qerispay.utils.constant.ControllerConstant;
import com.enigma.qerispay.utils.customResponse.ResponseFile;
import com.enigma.qerispay.utils.customResponse.ResponseMessage;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApiUrlConstant.FILE_PATH)
@AllArgsConstructor
public class FileController {

    private final FIleStorageService storageService;

    @SecurityRequirement(name = "Authorization")
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            storageService.store(file);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage(String.format(
                            ControllerConstant.UPLOADED_THE_FILE_SUCCESSFULLY, file.getOriginalFilename()))
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseMessage(String.format(
                            ControllerConstant.COULD_NOT_UPLOAD_THE_FILE, file.getOriginalFilename()))
                    );
        }
    }

    @SecurityRequirement(name = "Authorization")
    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/download/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id, HttpServletResponse response) throws IOException {
        FileStorage fileStorage = storageService.getFile(id);

        ByteArrayResource resource = new ByteArrayResource(fileStorage.getData());
        response.setContentType(fileStorage.getType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileStorage.getName() + "\"");

        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
