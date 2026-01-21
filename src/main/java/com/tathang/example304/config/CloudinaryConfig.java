package com.tathang.example304.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        System.out.println("☁️ CLOUDINARY CONFIG:");
        System.out.println("cloud_name=" + System.getenv("CLOUDINARY_CLOUD_NAME"));
        System.out.println("api_key=" + System.getenv("CLOUDINARY_API_KEY"));
        System.out.println("api_secret=" + System.getenv("CLOUDINARY_API_SECRET"));

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", System.getenv("CLOUDINARY_CLOUD_NAME"));
        config.put("api_key", System.getenv("CLOUDINARY_API_KEY"));
        config.put("api_secret", System.getenv("CLOUDINARY_API_SECRET"));
        return new Cloudinary(config);
    }
}
