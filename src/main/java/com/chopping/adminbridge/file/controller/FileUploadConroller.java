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
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class FileUploadConroller {

    private final String imageRepoDir = "/app/uploaded_images/"; // Docker Compose에서 마운트한 컨테이너 내부 경로와 일치


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
    public ResponseEntity<Resource> showImage(@RequestParam String fileName) {
        try {
            // 호스트 경로 대신, 컨테이너 내부의 마운트된 경로를 사용
            Path imagePath = Paths.get(imageRepoDir, fileName);
            Resource resource = new UrlResource(imagePath.toUri());

            System.out.println("######## url : " + resource);

            if (!resource.exists() || !resource.isReadable()) { // isReadable() 추가 권장
                System.out.println("######## Image not found or not readable: " + fileName);
                return ResponseEntity.notFound().build();
            }

            // 파일 확장자를 기반으로 Content-Type을 동적으로 결정하는 것이 좋습니다.
            String contentType = null;
            try {
                contentType = Files.probeContentType(imagePath);
            } catch (IOException e) {
                // 파일 타입 감지 실패 시 기본값 또는 에러 처리
                System.err.println("Failed to determine content type for " + fileName + ": " + e.getMessage());
            }

            if (contentType == null) {
                // 기본 이미지 타입 (JPEG) 또는 더 일반적인 octet-stream
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType)) // 동적으로 결정된 타입 사용
                    .body(resource);

        } catch (MalformedURLException e) {
            System.err.println("Malformed URL for image: " + fileName + " - " + e.getMessage());
            return ResponseEntity.badRequest().build(); // URL 형식이 잘못된 경우
        } catch (IOException e) {
            System.err.println("IO Error while reading image: " + fileName + " - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 파일 읽기 오류
        }
    }
}
