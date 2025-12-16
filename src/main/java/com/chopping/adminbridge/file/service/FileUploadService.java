package com.chopping.adminbridge.file.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.spi.IIORegistry;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.webp.WebpWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chopping.adminbridge.file.entity.Attachment;
import com.chopping.adminbridge.file.repository.AttachmentRepository;


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
    
    @Value("${file.upload.local-path}")
    private String localPath;
    
    @Value("${file.upload.docker-path}")
    private String dockerPath;
    
    @Value("${file.upload.active}")
    private String activeEnvironment;

    private final AttachmentRepository attachmentRepository;

    public FileUploadService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;

    }
    
    /**
     * 현재 환경에 따른 업로드 경로 반환
     */
    private String getUploadDir() {
        return "local".equals(activeEnvironment) ? localPath : dockerPath;
    }

    public ResponseEntity<?>  uploadAndSaveFile(MultipartFile file, String filePath) throws IOException {
        // 파일명 생성 (UUID + 확장자)
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + extension;
        InputStream inputStream = null;

        try {
            // 현재 환경에 따른 업로드 경로 사용
            String uploadDir = getUploadDir();


            // 1. 파일이 저장될 '디렉터리 경로'만으로 Path 객체를 생성합니다.
            Path directoryPath = Paths.get(uploadDir + filePath);

            // 2. 디렉터리를 생성합니다. (이미 존재하면 아무 일도 하지 않고, 없으면 새로 만듭니다)
            Files.createDirectories(directoryPath);
            
            // 로컬 디렉토리에 저장
            // 3. 이제 디렉터리가 존재하므로 안심하고 파일을 저장합니다.
            File dest = new File(directoryPath.toString() + "/" + uniqueFileName);
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
            att.setFilePath(directoryPath.toString() + "/" + uniqueFileName);

            // jpa 를 이용하여 save 기능 추가
            attachmentRepository.save(att);
            System.out.println("###### file save ok  : " + uniqueFileName);
            return ResponseEntity.ok(Map.of("fileName", uniqueFileName));
        } catch (IOException e) {
            System.out.println("###### file save error : " + e.getMessage());
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

//    public String convertToWebP(MultipartFile file, String targetDir) throws IOException {
//        // 파일명 null 체크
//        String originalFilename = file.getOriginalFilename();
//        if (originalFilename == null || originalFilename.isEmpty()) {
//            throw new IOException("파일명이 없습니다.");
//        }
//
//        // 원본 이미지 읽기
//        BufferedImage image = ImageIO.read(file.getInputStream());
//        if (image == null) {
//            throw new IOException("이미지 파일이 아닙니다.");
//        }
//
//        // 저장 경로 준비
//        Path dir = Paths.get(targetDir);
//        if (!Files.exists(dir)) {
//            Files.createDirectories(dir);
//        }
//
//        // 파일명 변환 (확장자 제거 후 .webp 추가)
//        String baseName = originalFilename;
//        int lastDotIndex = originalFilename.lastIndexOf('.');
//        if (lastDotIndex > 0) {
//            baseName = originalFilename.substring(0, lastDotIndex);
//        }
//        String outputName = baseName + ".webp";
//        Path outputPath = dir.resolve(outputName);
//
//        // WebP로 저장 (ImageWriter 명시적 사용)
//        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("webp");
////        if (!writers.hasNext()) {
////            throw new IOException("WebP writer를 찾을 수 없습니다. imageio-webp 라이브러리가 클래스패스에 있는지 확인하세요.");
////        }
//
//        Thumbnails.of(file.getInputStream())
//                .scale(1.0)
//                .outputQuality(0.85)
//                .outputFormat("webp")
//                .toFile(outputName);
//
//        ImageWriter writer = writers.next();
//        ImageWriteParam writeParam = writer.getDefaultWriteParam();
//
//        // 품질 설정 (0.0 ~ 1.0)
//        if (writeParam.canWriteCompressed()) {
//            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//            writeParam.setCompressionQuality(0.85f);
//        }
//
//        try (ImageOutputStream outputStream = ImageIO.createImageOutputStream(outputPath.toFile())) {
//            writer.setOutput(outputStream);
//            IIOImage iioImage = new IIOImage(image, null, null);
//            writer.write(null, iioImage, writeParam);
//        } finally {
//            writer.dispose();
//        }
//
//        return outputPath.toString();
//    }

    public String convertToWebpWithScrimage(MultipartFile file, String targetDir, boolean lossless) throws IOException {
        // 1. 디렉터리 준비
        Path dir = Paths.get(targetDir);
        Files.createDirectories(dir);

        // 2. 임시 파일 또는 최종 원본 파일 저장
        String baseName = Optional.ofNullable(file.getOriginalFilename())
                .orElse("image")
                .replaceAll("\\.(?=[^.]+$)", "");
        Path originalPath = dir.resolve(baseName + "_original");
        Files.copy(file.getInputStream(), originalPath);

        // 3. Scrimage로 WebP 변환
        File originalFile = originalPath.toFile();
        File outputFile = dir.resolve(baseName + ".webp").toFile();

        ImmutableImage image = ImmutableImage.loader().fromFile(originalFile);
        WebpWriter writer = lossless
                ? WebpWriter.DEFAULT.withLossless()
                : WebpWriter.DEFAULT;

        image.output(writer, outputFile);

        return outputFile.getAbsolutePath();
    }


}