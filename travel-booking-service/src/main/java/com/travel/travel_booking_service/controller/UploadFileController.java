package com.travel.travel_booking_service.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.travel.travel_booking_service.dto.response.CloudinaryUploadResponse;
import com.travel.travel_booking_service.service.UploadImageFile;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("api/upload")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadFileController {
    UploadImageFile uploadImageFile;

    @PostMapping("/images")
    public CloudinaryUploadResponse uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        CloudinaryUploadResponse cloudinaryUploadResponse = uploadImageFile.upLoadImage(file);
        return cloudinaryUploadResponse;
    }
}
