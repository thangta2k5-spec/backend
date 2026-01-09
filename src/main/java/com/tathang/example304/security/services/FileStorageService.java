package com.tathang.example304.security.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public void init() {
        System.out.println("📁 FileStorageService initialized");
        System.out.println("📁 Upload directory set to: " + uploadDir);
    }

    public String storeFile(MultipartFile file) throws IOException {
        System.out.println("=== 💾 FILE STORAGE SERVICE: storeFile ===");

        if (file.isEmpty()) {
            System.out.println("⚠️ File is empty, returning null");
            return null;
        }

        System.out.println("📄 File details:");
        System.out.println("  Original filename: " + file.getOriginalFilename());
        System.out.println("  Content type: " + file.getContentType());
        System.out.println("  Size: " + file.getSize() + " bytes");

        // Tạo thư mục nếu chưa tồn tại
        Path uploadPath = Paths.get(uploadDir);
        System.out.println("📁 Upload path: " + uploadPath.toAbsolutePath());

        if (!Files.exists(uploadPath)) {
            System.out.println("📁 Creating upload directory...");
            Files.createDirectories(uploadPath);
            System.out.println("✅ Directory created");
        } else {
            System.out.println("✅ Directory already exists");
        }

        // Tạo tên file unique
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            System.out.println("📝 File extension: " + fileExtension);
        }

        String fileName = UUID.randomUUID().toString() + fileExtension;
        Path filePath = uploadPath.resolve(fileName);

        System.out.println("💾 Saving file as: " + fileName);
        System.out.println("📁 Full path: " + filePath.toAbsolutePath());

        try {
            // Lưu file
            Files.copy(file.getInputStream(), filePath);
            System.out.println("✅ File saved successfully");

            String fileUrl = "/uploads/" + fileName;
            System.out.println("🔗 File URL: " + fileUrl);
            System.out.println("=========================================");

            return fileUrl;

        } catch (IOException e) {
            System.out.println("❌ File save failed: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}