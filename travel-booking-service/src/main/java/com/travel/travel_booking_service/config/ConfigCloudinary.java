package com.travel.travel_booking_service.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class ConfigCloudinary {

    @Bean
    public Cloudinary configKey() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dqxbfkkww");
        config.put("api_key", "231635746185497");
        config.put("api_secret", "5JddtEb0mnaW6MrqNkTo4NSZUBk");
        Cloudinary cloudinary = new Cloudinary(config);
        return cloudinary;
    }
}
