package com.tathang.example304.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    @Autowired
    private ImageUploadService imageUploadService;

    public String storeFile(MultipartFile file) {
        System.out.println("=== ☁️ UPLOAD IMAGE TO CLOUDINARY ===");

        if (file == null || file.isEmpty()) {
            System.out.println("⚠️ File is empty, returning null");
            return null;
        }

        String imageUrl = imageUploadService.uploadImage(file);

        System.out.println("✅ Image uploaded successfully");
        System.out.println("🔗 Image URL: " + imageUrl);
        System.out.println("====================================");

        return imageUrl;
    }
}
