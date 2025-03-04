package com.example.shop_java.service.impl;

import com.example.shop_java.exception.FileStorageException;
import com.example.shop_java.service.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path rootLocation;

    public static final String UPLOAD_DIRECTORY = "upload";

    public FileStorageServiceImpl() {
        try {
            this.rootLocation = Paths.get(UPLOAD_DIRECTORY);
            Files.createDirectories(this.rootLocation);
        } catch (IOException e) {
            throw new FileStorageException("Error while creating the root location", e);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            String fileName = UUID.randomUUID() + "." + getExtension(file);
            Path destinationFile = this.rootLocation.resolve(fileName);

            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        } catch (IOException e) {
            throw new FileStorageException("Error store file", e);
        } catch (IllegalArgumentException e) {
            throw new FileStorageException(e.getMessage(), e);
        }
    }

    @Override
    public List<String> storeFiles(MultipartFile[] files) {
        List<String> fileNames = new ArrayList<>();

        for (MultipartFile file : files) {
            fileNames.add(storeFile(file));
        }

        return fileNames;
    }


    @Override
    public byte[] showFile(String fileName) {
        try {
            Path filePath = Paths.get(FileStorageServiceImpl.UPLOAD_DIRECTORY, fileName);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new FileStorageException("Error reading file: " + fileName, e);
        }
    }

    private String getExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
