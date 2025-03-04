package com.example.shop_java.web.controller;

import com.example.shop_java.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final FileStorageService fileStorageService;

    @GetMapping("/show/{fileName}")
    public ResponseEntity<byte[]> showImage(@PathVariable String fileName) {
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", MediaType.IMAGE_JPEG_VALUE)
                .body(
                        fileStorageService.showFile(fileName)
                );
    }
}
