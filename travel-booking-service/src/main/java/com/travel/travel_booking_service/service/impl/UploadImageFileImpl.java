package com.travel.travel_booking_service.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.travel.travel_booking_service.dto.response.CloudinaryUploadResponse;
import com.travel.travel_booking_service.service.UploadImageFile;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadImageFileImpl implements UploadImageFile {

    Cloudinary cloudinary;

    @Override
    public CloudinaryUploadResponse upLoadImage(MultipartFile file) throws IOException {
        assert file.getOriginalFilename() != null;
        String publicValue = generatePublicValue(file.getOriginalFilename());
        String extension = getFileName(file.getOriginalFilename())[1];
        File fileUpload = convert(file);
        //        cloudinary.uploader().upload(fileUpload, ObjectUtils.asMap("public_id", publicValue));
        Map uploadResult = cloudinary.uploader().upload(fileUpload, ObjectUtils.asMap("public_id", publicValue));
        cleanDisk(fileUpload);

        //        String filePath = cloudinary.url().generate(StringUtils.join(publicValue, ".", extension));

        // Lấy thông tin từ response
        String cloudinaryPublicId = (String) uploadResult.get("public_id");
        String cloudinaryUrl = (String) uploadResult.get("secure_url"); // Sử dụng secure_url cho HTTPS

        return new CloudinaryUploadResponse(cloudinaryPublicId, cloudinaryUrl);
    }

    private void cleanDisk(File file) {
        try {
            log.info("file.toPath() {}", file.toPath());
            Path filePath = file.toPath();
            Files.delete(filePath);
        } catch (IOException e) {
            log.error("Error");
        }
    }

    private File convert(MultipartFile file) throws IOException {
        assert file.getOriginalFilename() != null;
        File convFile = new File(StringUtils.join(
                generatePublicValue(file.getOriginalFilename()), getFileName(file.getOriginalFilename())[1]));
        try (InputStream is = file.getInputStream()) {
            Files.copy(is, convFile.toPath());
        }
        return convFile;
    }

    public String generatePublicValue(String originalFilename) {
        String fileName = getFileName(originalFilename)[0];
        return StringUtils.join(UUID.randomUUID().toString(), "_", fileName);
    }

    public String[] getFileName(String originalFilename) {
        return originalFilename.split("\\.");
    }
}
