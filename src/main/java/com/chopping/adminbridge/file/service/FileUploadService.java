package com.chopping.adminbridge.file.service;

import com.chopping.adminbridge.file.entity.Attachment;
import com.chopping.adminbridge.file.repository.AttachmentRepository;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import java.io.InputStream;


@Service
public class FileUploadService {

    @Value("${ftp.server}")
    private String ftpServer;

    @Value("${ftp.port}")
    private int ftpPort;

    @Value("${ftp.user}")
    private String ftpUser;

    @Value("${ftp.password}")
    private String ftpPassword;


    private final String uploadDir = "/Users/alankang/Documents/images/imagerepo";

    private final AttachmentRepository attachmentRepository;

    public FileUploadService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public ResponseEntity<?>  uploadAndSaveFile(MultipartFile file) throws IOException {
        // 파일명 생성 (UUID + 확장자)
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + extension;
        InputStream inputStream = null;

        try {
            // 로컬 디렉토리에 저장
            File dest = new File(uploadDir + uniqueFileName);
            file.transferTo(dest);

            // 응답으로 이미지 URL 반환
            String fileUrl = uploadDir + uniqueFileName; // 예제 URL
            // DB 저장
            // 파일 정보 DB 저장
            Attachment att = new Attachment();
            att.setFileOriginalName(originalFilename);
            att.setFileChangeName(uniqueFileName);
            att.setFileSize(String.valueOf(file.getSize()));
            att.setFileType(file.getContentType());
            att.setFileDt(LocalDate.now());
            att.setFilePath(uploadDir + uniqueFileName);

            // jpa 를 이용하여 save 기능 추가
            attachmentRepository.save(att);

            return ResponseEntity.ok(Map.of("url", fileUrl));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "File upload failed"));
        }

//        FTPClient ftpClient = new FTPClient();
//        try {
//            inputStream = file.getInputStream();
//
//            ftpClient.connect(ftpServer, ftpPort); // FTP 서버 주소와 포트
//            ftpClient.login(ftpUser, ftpPassword);      // 계정 로그인
//            ftpClient.enterLocalPassiveMode();              // 패시브 모드
//            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);    // 바이너리 파일 전송
//
//            // 디렉토리 이동
//            boolean cd1 = ftpClient.changeWorkingDirectory("/HDD1");
//            System.out.println("📂 HDD1 접근: " + cd1);
//
//            boolean cd2 = ftpClient.changeWorkingDirectory("/HDD1/upload");
//            System.out.println("📂 HDD1/upload 접근: " + cd2);
//
//            // 파일 업로드 (저장 경로 + 파일명)
//            boolean success = ftpClient.storeFile("/HDD1/upload/" + uniqueFileName, inputStream);
//            int replyCode = ftpClient.getReplyCode();
//            System.out.println("📨 서버 응답 코드: " + replyCode);
//
//            if (!success) {
//                throw new IOException("FTP 파일 업로드 실패");
//            } else {
//                System.out.println("✅ FTP 파일 업로드 성공");
//            }
//
//            String fileUrl = uploadDir + uniqueFileName; // 예제 URL
//
//            // DB 저장
//            // 파일 정보 DB 저장
//            Attachment att = new Attachment();
//            att.setFileOriginalName(originalFilename);
//            att.setFileChangeName(uniqueFileName);
//            att.setFileSize(String.valueOf(file.getSize()));
//            att.setFileType(file.getContentType());
//            att.setFileDt(LocalDate.now());
//            att.setFilePath("/HDD1/upload/" + uniqueFileName);
//
//            // jpa 를 이용하여 save 기능 추가
//            attachmentRepository.save(att);
//
//            return ResponseEntity.ok(Map.of("fileName", uniqueFileName));
//
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "File upload failed"));
//        } finally {
//            try {
//                if (inputStream != null) inputStream.close();
//                if (ftpClient.isConnected()) {
//                    ftpClient.logout();
//                    ftpClient.disconnect();
//                }
//            } catch (IOException ex) {
//                System.err.println("⚠️ FTP 연결 종료 실패: " + ex.getMessage());
//            }
//        }
    }
}