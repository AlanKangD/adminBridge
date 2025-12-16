package com.chopping.adminbridge.file.controller;

import com.chopping.adminbridge.file.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Tag(name= "File API", description = " 파일 관리 REST API ")
public class FileUploadRestController {

    @Value("${file.upload.local-path}")
    private String localPath;

    @Value("${file.upload.docker-path}")
    private String dockerPath;

    @Value("${file.upload.active}")
    private String activeEnvironment;

    private final FileUploadService fileUploadService;

    private String getImageRepoDir() {
        return "local".equalsIgnoreCase(activeEnvironment)
                ? localPath   // 예: /Users/alan/.../
                : dockerPath; // 예: /data/uploads/
    }

    @PostMapping("/image/webp")
    @Operation(summary = "파일 webP 변환", description = "webP 파일 형식으로 변경합니다. ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "webP 파일 변경 완료"),
            @ApiResponse(responseCode = "404", description = "파일을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<?> uploadAndConvertWebP(
            @RequestParam("file") MultipartFile file,
            @RequestParam("filePath") String filePath) {

        try {
            String targetDir = getImageRepoDir() + filePath;
            String savedWebP = fileUploadService.convertToWebpWithScrimage(file, targetDir, false);
            return ResponseEntity.ok(savedWebP);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("WebP 변환 실패: " + e.getMessage());
        }
    }


}
