package com.example.shop_java.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileStorageService {

    String storeFile(MultipartFile file);

    List<String> storeFiles(MultipartFile[] files) throws JsonProcessingException;

    byte[] showFile(String fileName);

}
