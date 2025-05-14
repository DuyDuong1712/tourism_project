-- Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS tour_management;
USE tour_management;

-- Bảng user
CREATE TABLE `user` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `fullname` VARCHAR(255) NOT NULL,
    `phone` VARCHAR(255) DEFAULT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `address` VARCHAR(255) DEFAULT NULL,
    `dateofbirth` DATE DEFAULT NULL,
    `gender` VARCHAR(50) DEFAULT NULL,
    `profileimg` VARCHAR(255) DEFAULT NULL,
    `inActive` TINYINT DEFAULT 1, -- 1: active, 0: inactive
    `isDelete` TINYINT DEFAULT 0, -- 0: not deleted, 1: deleted
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `username_unique` (`username`),
    INDEX `idx_user_email` (`email`) -- Thêm index cho tìm kiếm email
);

-- Bảng role
CREATE TABLE `role` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `code` VARCHAR(255) NOT NULL,
    `inActive` TINYINT DEFAULT 1, -- 1: active, 0: inactive
    `isDelete` TINYINT DEFAULT 0, -- 0: not deleted, 1: deleted
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `code_unique` (`code`)
);

-- Bảng user_role (liên kết user và role)
CREATE TABLE `user_role` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `role_id` INTEGER NOT NULL,
    `user_id` INTEGER NOT NULL,
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (`role_id`) REFERENCES `role`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    UNIQUE KEY `user_role_unique` (`user_id`, `role_id`) -- Đảm bảo không trùng lặp
);

-- Tạo bảng permission
CREATE TABLE `permission` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `code` VARCHAR(255) NOT NULL,
    `inActive` TINYINT DEFAULT 1, -- 1: active, 0: inactive
    `isDelete` TINYINT DEFAULT 0, -- 0: not deleted, 1: deleted
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `code_unique` (`code`),
    INDEX `idx_permission_name` (`name`)
);

-- Bảng role_permission (liên kết role và permission)
CREATE TABLE `role_permission` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `role_id` INTEGER NOT NULL,
    `permission_id` INTEGER NOT NULL,
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`role_id`) REFERENCES `role`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (`permission_id`) REFERENCES `permission`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    UNIQUE KEY `role_permission_unique` (`role_id`, `permission_id`)
);

-- Bảng categories
CREATE TABLE `categories` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `description` TEXT DEFAULT NULL,
    `image` VARCHAR(255) DEFAULT NULL,
    `inActive` TINYINT DEFAULT 1, -- 1: active, 0: inactive
    `isDelete` TINYINT DEFAULT 0, -- 0: not deleted, 1: deleted
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name_unique` (`name`)
);

-- Bảng tour
CREATE TABLE `tour` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255) NOT NULL,
    `description` TEXT NOT NULL,
    `price` BIGINT NOT NULL CHECK (`price` >= 0),
    `duration` VARCHAR(255) DEFAULT NULL,
    `max_slots` INTEGER DEFAULT NULL CHECK (`max_slots` >= 0),
    `inActive` TINYINT DEFAULT 1, -- 1: active, 0: inactive
    `isDelete` TINYINT DEFAULT 0, -- 0: not deleted, 1: deleted
    `category_id` INTEGER NOT NULL,
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`category_id`) REFERENCES `categories`(`id`) ON DELETE RESTRICT ON UPDATE NO ACTION,
    INDEX `idx_tour_category` (`category_id`)
);

-- Bảng trip (chuyến đi cụ thể của tour)
CREATE TABLE `trip` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `tour_id` INTEGER NOT NULL,
    `guide_id` INTEGER,
    `startdate` DATETIME NOT NULL,
    `enddate` DATETIME NOT NULL,
    `actual_price` BIGINT DEFAULT NULL,
    `available_slots` INTEGER DEFAULT NULL,
    `meeting_point` VARCHAR(255) DEFAULT NULL,
    `meeting_time` TIME DEFAULT NULL,
    `status` VARCHAR(50) DEFAULT 'SCHEDULED' COMMENT 'SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED',
    `inActive` TINYINT DEFAULT 1, -- 1: active, 0: inactive
    `isDelete` TINYINT DEFAULT 0, -- 0: not deleted, 1: deleted
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`tour_id`) REFERENCES `tour`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (`guide_id`) REFERENCES `user`(`id`) ON DELETE SET NULL ON UPDATE NO ACTION,
    INDEX `idx_trip_tour` (`tour_id`),
    INDEX `idx_trip_dates` (`startdate`, `enddate`)
);

ALTER TABLE `trip`
ADD CONSTRAINT `chk_trip_dates` CHECK (`enddate` > `startdate`),
ADD CONSTRAINT `chk_available_slots` CHECK (`available_slots` >= 0);

-- Bảng destinations
CREATE TABLE `destinations` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `city` VARCHAR(255) NOT NULL,
    `country` VARCHAR(255) NOT NULL,
    `description` TEXT DEFAULT NULL,
    `inActive` TINYINT DEFAULT 1, -- 1: active, 0: inactive
    `isDelete` TINYINT DEFAULT 0, -- 0: not deleted, 1: deleted
    `image` VARCHAR(255) DEFAULT NULL,
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `destination_unique` (`name`, `city`, `country`)
);

-- Bảng tour_destinations
CREATE TABLE `tour_destinations` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `tour_id` INTEGER NOT NULL,
    `destination_id` INTEGER NOT NULL,
    `visit_order` TINYINT NOT NULL CHECK (`visit_order` >= 1),
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`tour_id`) REFERENCES `tour`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (`destination_id`) REFERENCES `destinations`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    UNIQUE KEY `tour_destination_unique` (`tour_id`, `destination_id`)
);

-- Bảng hotel
CREATE TABLE `hotel` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `address` TEXT NOT NULL,
    `city` VARCHAR(255) NOT NULL,
    `country` VARCHAR(255) NOT NULL,
    `star_rating` TINYINT CHECK (`star_rating` BETWEEN 1 AND 5),
    `description` TEXT DEFAULT NULL,
    `image` VARCHAR(255) DEFAULT NULL,
    `inActive` TINYINT DEFAULT 1, -- 1: active, 0: inactive
    `isDelete` TINYINT DEFAULT 0, -- 0: not deleted, 1: deleted
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `hotel_unique` (`name`, `city`, `country`)
);

-- Bảng transports
CREATE TABLE `transports` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `type` VARCHAR(50) NOT NULL,
    `description` TEXT DEFAULT NULL,
    `image` VARCHAR(255) DEFAULT NULL,
    `inActive` TINYINT DEFAULT 1, -- 1: active, 0: inactive
    `isDelete` TINYINT DEFAULT 0, -- 0: not deleted, 1: deleted
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

-- Bảng tour_hotels
CREATE TABLE `tour_hotels` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `hotel_id` INTEGER NOT NULL,
    `tour_id` INTEGER NOT NULL,
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`hotel_id`) REFERENCES `hotel`(`id`) ON DELETE RESTRICT ON UPDATE NO ACTION,
    FOREIGN KEY (`tour_id`) REFERENCES `tour`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    UNIQUE KEY `tour_hotel_unique` (`hotel_id`, `tour_id`)
);

-- Bảng tour_transports
CREATE TABLE `tour_transports` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `tour_id` INTEGER NOT NULL,
    `transport_id` INTEGER NOT NULL,
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`tour_id`) REFERENCES `tour`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (`transport_id`) REFERENCES `transports`(`id`) ON DELETE RESTRICT ON UPDATE NO ACTION,
    UNIQUE KEY `tour_transport_unique` (`tour_id`, `transport_id`)
);

-- Bảng tour_images
CREATE TABLE `tour_images` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `tour_id` INTEGER NOT NULL,
    `cloudinary_public_id` VARCHAR(255) NOT NULL COMMENT 'Public ID của hình ảnh trên Cloudinary',
    `cloudinary_url` VARCHAR(255) NOT NULL COMMENT 'URL đầy đủ của hình ảnh từ Cloudinary',
    `is_thumbnail` TINYINT DEFAULT 0 COMMENT 'Hình ảnh đại diện của tour',
    `alt_text` VARCHAR(255) DEFAULT NULL COMMENT 'Văn bản thay thế cho hình ảnh (tốt cho SEO)',
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`tour_id`) REFERENCES `tour`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    INDEX `idx_tour_images_tour` (`tour_id`)
);

-- Bảng bookings
CREATE TABLE `bookings` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `customer_id` INTEGER NOT NULL,
    `trip_id` INTEGER NOT NULL,
    `booking_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `adults` TINYINT NOT NULL DEFAULT 1 CHECK (`adults` >= 1),
    `children` TINYINT DEFAULT 0 CHECK (`children` >= 0),
    `number_of_people` TINYINT NOT NULL DEFAULT 1 CHECK (`number_of_people` >= 0),
    `total_price` BIGINT NOT NULL,
    `booking_status` VARCHAR(50) DEFAULT 'PENDING' COMMENT 'PENDING, CONFIRMED, PROCESSING, COMPLETED, CANCELLED, INACTIVE',
    `isDelete` TINYINT DEFAULT 0, -- 0: not deleted, 1: deleted
    `payment_status` VARCHAR(50) DEFAULT 'PENDING' COMMENT 'PENDING, PAID, REFUNDED, FAILED',
    `cancellation_reason` TEXT DEFAULT NULL,
    `notes` TEXT DEFAULT NULL,
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`customer_id`) REFERENCES `user`(`id`) ON DELETE RESTRICT ON UPDATE NO ACTION,
    FOREIGN KEY (`trip_id`) REFERENCES `trip`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    INDEX `idx_bookings_trip` (`trip_id`)
);

-- Bảng reviews
CREATE TABLE `reviews` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `user_id` INTEGER NOT NULL,
    `trip_id` INTEGER NOT NULL,
    `booking_id` INTEGER NOT NULL,
    `rating` TINYINT NOT NULL CHECK (`rating` BETWEEN 1 AND 5),
    `comment` TEXT DEFAULT NULL,
    `inActive` TINYINT DEFAULT 1, -- 1: active, 0: inactive
    `isDelete` TINYINT DEFAULT 0, -- 0: not deleted, 1: deleted
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE RESTRICT ON UPDATE NO ACTION,
    FOREIGN KEY (`trip_id`) REFERENCES `trip`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (`booking_id`) REFERENCES `bookings`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    UNIQUE KEY `booking_review_unique` (`booking_id`),
    INDEX `idx_reviews_trip` (`trip_id`)
);

-- Bảng review_images
CREATE TABLE `review_images` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `review_id` INTEGER NOT NULL,
    `cloudinary_public_id` VARCHAR(255) NOT NULL,
    `cloudinary_url` VARCHAR(255) NOT NULL,
    `alt_text` VARCHAR(255) DEFAULT NULL,
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`review_id`) REFERENCES `reviews`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    INDEX `idx_review_images_review` (`review_id`)
);

-- Bảng booking_management
CREATE TABLE `booking_management` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `booking_id` INTEGER NOT NULL,
    `staff_id` INTEGER NOT NULL,
    `notes` TEXT DEFAULT NULL,
    `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `createdby` VARCHAR(255) DEFAULT NULL,
    `modifiedby` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`booking_id`) REFERENCES `bookings`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (`staff_id`) REFERENCES `user`(`id`) ON DELETE RESTRICT ON UPDATE NO ACTION,
    INDEX `idx_booking_management_booking` (`booking_id`)
);

-- Notification table
CREATE TABLE notifications (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `user_id` INTEGER NOT NULL,
  `booking_id` INTEGER,
  `message` TEXT NOT NULL,
  `type` VARCHAR(50) NOT NULL,
  `read_status` TINYINT NOT NULL DEFAULT 0,
  `createddate` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `modifieddate` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `createdby` VARCHAR(255) DEFAULT NULL,
  `modifiedby` VARCHAR(255) DEFAULT NULL,
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE
);

-- Index để tăng tốc truy vấn
CREATE INDEX idx_notifications_user ON notifications(user_id);
CREATE INDEX idx_notifications_user_read ON notifications(user_id, read_status);
CREATE INDEX idx_notifications_created_date ON notifications(createddate);