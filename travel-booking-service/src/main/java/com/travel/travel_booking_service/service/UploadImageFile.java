package com.travel.travel_booking_service.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.travel.travel_booking_service.dto.response.CloudinaryUploadResponse;

public interface UploadImageFile {
    CloudinaryUploadResponse upLoadImage(MultipartFile file) throws IOException;
}
