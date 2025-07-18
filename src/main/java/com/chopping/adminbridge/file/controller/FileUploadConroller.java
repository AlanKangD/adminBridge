package com.chopping.adminbridge.file.controller;

import com.chopping.adminbridge.file.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class FileUploadConroller {



    private final FileUploadService fileUploadService;

    public FileUploadConroller(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, InputStream inputStream) {
        try {
            ResponseEntity<?> path = fileUploadService.uploadAndSaveFile(file);
            return ResponseEntity.ok().body(path);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("파일 업로드 실패: " + e.getMessage());
        }
    }

    @GetMapping("/showImage")
    @ResponseBody
    public ResponseEntity<Resource> showImage(@RequestParam String fileName) throws IOException {
        Path imagePath = Paths.get("/Users/alankang/Documents/images/imagerepo", fileName);
        Resource resource = new UrlResource(imagePath.toUri());

        System.out.println("######## url : " + resource);

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // 필요 시 이미지 타입 판단 로직 추가
                .body(resource);
    }
}
