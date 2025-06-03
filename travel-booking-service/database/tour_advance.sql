--  Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS tour_management_adv;
USE tour_management_adv;

--  ================================
--  1. HỆ THỐNG USER & PHÂN QUYỀN
--  ================================

--  Bảng role
CREATE TABLE `roles` (
                         `id` BIGINT NOT NULL AUTO_INCREMENT,
                         `code` VARCHAR(100) NOT NULL,
                         `description` TEXT DEFAULT NULL,
                         `in_active` TINYINT(1) DEFAULT 1, --  1: active, 0: inactive
                         `is_deleted` TINYINT(1) DEFAULT 0, --  0: not deleted, 1: deleted
                         `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                         `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                         `created_by` VARCHAR(255) DEFAULT NULL,
                         `modified_by` VARCHAR(255) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `code_unique` (`code`)
);

--  Tạo bảng permission
CREATE TABLE `permissions` (
                               `id` BIGINT NOT NULL AUTO_INCREMENT,
                               `code` VARCHAR(100) NOT NULL,
                               `description` TEXT DEFAULT NULL,
                               `in_active` TINYINT(1) DEFAULT 1, --  1: active, 0: inactive
                               `is_deleted` TINYINT(1) DEFAULT 0, --  0: not deleted, 1: deleted
                               `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                               `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                               `created_by` VARCHAR(255) DEFAULT NULL,
                               `modified_by` VARCHAR(255) DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `code_unique` (`code`)
);

--  Bảng role_permission (liên kết role và permission)
CREATE TABLE `role_permission` (
                                   `id` BIGINT NOT NULL AUTO_INCREMENT,
                                   `role_id` BIGINT NOT NULL,
                                   `permission_id` BIGINT NOT NULL,
                                   `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                                   `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                   `created_by` VARCHAR(255) DEFAULT NULL,
                                   `modified_by` VARCHAR(255) DEFAULT NULL,
                                   PRIMARY KEY (`id`),
                                   FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
                                   FOREIGN KEY (`permission_id`) REFERENCES `permissions`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
                                   UNIQUE KEY `role_permission_unique` (`role_id`, `permission_id`)
);

--  Bảng user
CREATE TABLE `users` (
                         `id` BIGINT NOT NULL AUTO_INCREMENT,
                         `role_id` BIGINT NOT NULL,
                         `username` VARCHAR(255) NOT NULL,
                         `password` VARCHAR(255) NOT NULL,
                         `fullname` VARCHAR(255) NOT NULL,
                         `phone` VARCHAR(255) DEFAULT NULL,
                         `email` VARCHAR(255) DEFAULT NULL,
                         `address` VARCHAR(255) DEFAULT NULL,
                         `date_of_birth` DATE DEFAULT NULL,
                         `gender` VARCHAR(50) DEFAULT NULL,
                         `profile_img` VARCHAR(255) DEFAULT NULL,
                         `in_active` TINYINT(1) DEFAULT 1, --  1: active, 0: inactive
                         `is_deleted` TINYINT(1) DEFAULT 0, --  0: not deleted, 1: deleted
                         `email_verified` TINYINT DEFAULT 0,
                         `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                         `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                         `created_by` VARCHAR(255) DEFAULT NULL,
                         `modified_by` VARCHAR(255) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `username_unique` (`username`),
                         UNIQUE KEY `email_unique` (`email`),
                         FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE
);

--  ================================
--  2. HỆ THỐNG DANH MỤC & ĐỊA ĐIỂM
--  ================================


--  Bảng categories
CREATE TABLE `categories` (
                              `id` BIGINT NOT NULL AUTO_INCREMENT,
                              `name` VARCHAR(255) NOT NULL,
                              `description` TEXT DEFAULT NULL,
                              `in_active` TINYINT(1) DEFAULT 1, --  1: active, 0: inactive
                              `is_deleted` TINYINT(1) DEFAULT 0, --  0: not deleted, 1: deleted
                              `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                              `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                              `created_by` VARCHAR(255) DEFAULT NULL,
                              `modified_by` VARCHAR(255) DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `name_unique` (`name`)
);

--  Bảng destinations
CREATE TABLE `destinations` (
                                `id` BIGINT NOT NULL AUTO_INCREMENT,
                                `name` VARCHAR(255) NOT NULL,
                                `code` VARCHAR(255) NOT NULL,
                                `description` TEXT DEFAULT NULL,
                                `image` VARCHAR(255) DEFAULT NULL,
                                `in_active` TINYINT(1) DEFAULT 1, --  1: active, 0: inactive
                                `is_deleted` TINYINT(1) DEFAULT 0, --  0: not deleted, 1: deleted
                                `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                                `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                `created_by` VARCHAR(255) DEFAULT NULL,
                                `modified_by` VARCHAR(255) DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `destination_unique` (`name`, `code`)
);

--  Bảng departures (điểm khởi hành)
CREATE TABLE `departures` (
                              `id` BIGINT NOT NULL AUTO_INCREMENT,
                              `name` VARCHAR(255) NOT NULL,
                              `code` VARCHAR(255) NOT NULL,
                              `address` TEXT NOT NULL,
                              `in_active` TINYINT(1) DEFAULT 1, --  1: active, 0: inactive
                              `is_deleted` TINYINT(1) DEFAULT 0,
                              `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                              `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                              `created_by` VARCHAR(255) DEFAULT NULL,
                              `modified_by` VARCHAR(255) DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `departures_unique` (`name`, `code`)
);

--  Bảng transports
CREATE TABLE `transports` (
                              `id` BIGINT NOT NULL AUTO_INCREMENT,
                              `name` VARCHAR(255) NOT NULL,
                              `type` VARCHAR(50) NOT NULL,
                              `brand` VARCHAR(50) NOT NULL,
                              `description` TEXT DEFAULT NULL,
                              `in_active` TINYINT(1) DEFAULT 1, --  1: active, 0: inactive
                              `is_deleted` TINYINT(1) DEFAULT 0, --  0: not deleted, 1: deleted
                              `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                              `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                              `created_by` VARCHAR(255) DEFAULT NULL,
                              `modified_by` VARCHAR(255) DEFAULT NULL,
                              PRIMARY KEY (`id`)
);

--  ================================
--  3. HỆ THỐNG TOUR
--  ================================

--  Bảng tours (thông tin cơ bản về tour)
CREATE TABLE `tours` (
                         `id` BIGINT NOT NULL AUTO_INCREMENT,
                         `title` TEXT NOT NULL,
                         `description` TEXT NOT NULL,
                         `summary` TEXT DEFAULT NULL,
                         `category_id` BIGINT NOT NULL,
                         `departure_id` BIGINT NOT NULL,
                         `destination_id` BIGINT NOT NULL,
                         `transport_id` BIGINT NOT NULL,
                         `duration` VARCHAR(255) DEFAULT NULL,
                         `min_slots` INT DEFAULT 1,
                         `max_slots` INT DEFAULT NULL,
                         `in_active` TINYINT(1) DEFAULT 1, --  1: active, 0: inactive
                         `is_deleted` TINYINT(1) DEFAULT 0,
                         `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                         `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                         `created_by` VARCHAR(255) DEFAULT NULL,
                         `modified_by` VARCHAR(255) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         FOREIGN KEY (`category_id`) REFERENCES `categories`(`id`) ON DELETE RESTRICT,
                         FOREIGN KEY (`departure_id`) REFERENCES `departures`(`id`) ON DELETE RESTRICT,
                         FOREIGN KEY (`destination_id`) REFERENCES `destinations`(`id`) ON DELETE RESTRICT,
                         FOREIGN KEY (`transport_id`) REFERENCES `transports`(`id`) ON DELETE RESTRICT
);

--  Bảng tour_information (thông tin chi tiết tour)
CREATE TABLE `tour_information` (
                                    `id` BIGINT NOT NULL AUTO_INCREMENT,
                                    `tour_id` BIGINT NOT NULL,
                                    `attractions` TEXT DEFAULT NULL,
                                    `cuisine` TEXT DEFAULT NULL,
                                    `suitable_object` TEXT DEFAULT NULL,
                                    `ideal_time` TEXT DEFAULT NULL,
                                    `what_included` TEXT DEFAULT NULL,
                                    `what_excluded` TEXT DEFAULT NULL,
                                    `important_notes` TEXT DEFAULT NULL,
                                    `terms_conditions` TEXT DEFAULT NULL,
                                    `cancellation_policy` TEXT DEFAULT NULL,
                                    `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                                    `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                    `created_by` VARCHAR(255) DEFAULT NULL,
                                    `modified_by` VARCHAR(255) DEFAULT NULL,
                                    PRIMARY KEY (`id`),
                                    FOREIGN KEY (`tour_id`) REFERENCES `tours`(`id`) ON DELETE CASCADE,
                                    UNIQUE KEY `tour_info_unique` (`tour_id`)
);

--  Bảng tour_schedules (lịch trình tour)
CREATE TABLE `tour_schedules` (
                                  `id` BIGINT NOT NULL AUTO_INCREMENT,
                                  `tour_id` BIGINT NOT NULL,
                                  `day` INT NOT NULL,
                                  `title` VARCHAR(255) NOT NULL,
                                  `activities` LONGTEXT DEFAULT NULL,
                                  `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                  `created_by` VARCHAR(255) DEFAULT NULL,
                                  `modified_by` VARCHAR(255) DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  FOREIGN KEY (`tour_id`) REFERENCES `tours`(`id`) ON DELETE CASCADE
);

--  Bảng tour_images (hình ảnh tour)
CREATE TABLE `tour_images` (
                               `id` BIGINT NOT NULL AUTO_INCREMENT,
                               `tour_id` BIGINT NOT NULL,
                               `cloudinary_public_id` VARCHAR(255) DEFAULT NULL,
                               `cloudinary_url` VARCHAR(500) NOT NULL,
                               `alt_text` VARCHAR(255) DEFAULT NULL,
                               `is_primary` TINYINT(1) DEFAULT 0,
                               `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                               `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                               `created_by` VARCHAR(255) DEFAULT NULL,
                               `modified_by` VARCHAR(255) DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               FOREIGN KEY (`tour_id`) REFERENCES `tours`(`id`) ON DELETE CASCADE
);

--  ================================
--  4. HỆ THỐNG LỊCH TRÌNH & GIÁ
--  ================================

--  Bảng tour_departures (các chuyến khởi hành cụ thể của tour)
CREATE TABLE `tour_departures` (
                                   `id` BIGINT NOT NULL AUTO_INCREMENT,
                                   `tour_id`BIGINT NOT NULL,
                                   `departure_date` DATETIME NOT NULL,
                                   `return_date` DATETIME NOT NULL,
                                   `meeting_point` VARCHAR(255) DEFAULT NULL,
                                   `meeting_time` TIME DEFAULT NULL,
                                   `available_slots` INT NOT NULL DEFAULT 0,
                                   `booked_slots` INT DEFAULT 0,
                                   `status` ENUM('scheduled', 'confirmed', 'in_progress', 'completed', 'cancelled') DEFAULT 'scheduled',
                                   `notes` TEXT DEFAULT NULL,
                                   `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                                   `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                   `created_by` VARCHAR(255) DEFAULT NULL,
                                   `modified_by` VARCHAR(255) DEFAULT NULL,
                                   PRIMARY KEY (`id`),
                                   FOREIGN KEY (`tour_id`) REFERENCES `tours`(`id`) ON DELETE CASCADE,
                                   CONSTRAINT `chk_departure_dates` CHECK (`return_date` > `departure_date`),
                                   CONSTRAINT `chk_departure_slots` CHECK (`available_slots` >= `booked_slots`)
);

--  Bảng tour_pricing (bảng giá tour)
CREATE TABLE `tour_pricing` (
                                `id` BIGINT NOT NULL AUTO_INCREMENT,
                                `tour_departure_id` BIGINT NOT NULL,
                                `adult_price` BIGINT NOT NULL DEFAULT 0, --  12 trở lên
                                `children_price` BIGINT DEFAULT 0, --  5-11 tuổi
                                `baby_price` BIGINT DEFAULT 0, --  2-4 tuổi
                                `infant_price` BIGINT DEFAULT 0, --  0-2 tuổi
                                `single_room_supplement` BIGINT DEFAULT 0,
                                `discount_percent` INT DEFAULT 0, --  Phần trăm giảm giá chung (vd: 10 nghĩa là giảm 10%)
                                `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                                `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                `created_by` VARCHAR(255) DEFAULT NULL,
                                `modified_by` VARCHAR(255) DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                FOREIGN KEY (`tour_departure_id`) REFERENCES `tour_departures`(`id`) ON DELETE CASCADE
);


--  Bảng bookings (đặt tour)
CREATE TABLE `bookings` (
                            `id` BIGINT NOT NULL AUTO_INCREMENT,
                            `tour_departure_id`BIGINT NOT NULL,
                            `customer_id`BIGINT NOT NULL,
                            `adult_count` INT DEFAULT 1,
                            `children_count` INT DEFAULT 0,
                            `baby_count` INT DEFAULT 0,
                            `infant_count` INT DEFAULT 0,
                            `total_people` INT NOT NULL DEFAULT 1,
                            `single_room_count` INT DEFAULT 0,
                            `subtotal` DECIMAL(15,2) NOT NULL DEFAULT 0, -- Tổng tiền chưa tính thuế và giảm giá.
                            `discount_amount` DECIMAL(15,2) DEFAULT 0, -- Tổng tiền giảm giá được áp dụng.
                            `tax_amount` DECIMAL(15,2) DEFAULT 0, -- Tiền thuế phải trả.
                            `total_amount` DECIMAL(15,2) NOT NULL, -- Tổng tiền cuối cùng khách phải trả (sau giảm giá + thuế).
                            `booking_status` ENUM('pending', 'confirmed', 'processing', 'completed', 'cancelled', 'refunded') DEFAULT 'pending',
                            `payment_status` ENUM('pending', 'paid', 'refunded', 'failed') DEFAULT 'pending',
                            `payment_method` ENUM('cash', 'bank_transfer') DEFAULT NULL,
                            `special_requests` TEXT DEFAULT NULL, --  Yêu cầu đặc biệt của khách hàng.
                            `internal_notes` TEXT DEFAULT NULL, -- Ghi chú nội bộ của nhân viên.
                            `confirmed_at` TIMESTAMP NULL DEFAULT NULL, -- Thời điểm xác nhận đặt tour.
                            `cancelled_at` TIMESTAMP NULL DEFAULT NULL, -- Thời điểm hủy tour.
                            `cancellation_reason` TEXT DEFAULT NULL, -- 	Lý do hủy tour, nếu có.
                            `cancelled_by` TEXT  DEFAULT NULL, -- 	Người hủy tour (nhân viên hoặc khách).

                            `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                            `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                            `created_by` VARCHAR(255) DEFAULT NULL,
                            `modified_by` VARCHAR(255) DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            FOREIGN KEY (`tour_departure_id`) REFERENCES `tour_departures`(`id`) ON DELETE RESTRICT,
                            FOREIGN KEY (`customer_id`) REFERENCES `users`(`id`) ON DELETE RESTRICT
);


--  Bảng booking_passengers (thông tin hành khách)
CREATE TABLE `booking_passengers` (
                                      `id` BIGINT NOT NULL AUTO_INCREMENT,
                                      `booking_id` BIGINT NOT NULL,
                                      `full_name` VARCHAR(255) NOT NULL,
                                      `date_of_birth` DATE DEFAULT NULL,
                                      `gender` ENUM('male', 'female', 'other') DEFAULT NULL,
                                      `passenger_type` ENUM('adult', 'children', 'baby', 'infant') NOT NULL,
                                      `special_requirements` TEXT DEFAULT NULL,
                                      `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                                      `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                      `created_by` VARCHAR(255) DEFAULT NULL,
                                      `modified_by` VARCHAR(255) DEFAULT NULL,
                                      PRIMARY KEY (`id`),
                                      FOREIGN KEY (`booking_id`) REFERENCES `bookings`(`id`) ON DELETE CASCADE
);


--  ================================
--  6. HỆ THỐNG THANH TOÁN
--  ================================

--  Bảng payments (thanh toán)
CREATE TABLE `payments` (
                            `id` BIGINT NOT NULL AUTO_INCREMENT,
                            `booking_id` BIGINT NOT NULL,
                            `payment_code` VARCHAR(50) NOT NULL,
                            `amount` DECIMAL(15,2) NOT NULL,
                            `payment_method` ENUM('cash', 'bank_transfer') NOT NULL,
                            `payment_gateway` VARCHAR(100) DEFAULT NULL, --  VNPay, Momo, etc.
                            `transaction_id` VARCHAR(255) DEFAULT NULL, --  Mã giao dịch VNPAY, Momo trả về
                            `payment_status` ENUM('pending', 'processing', 'completed', 'failed', 'cancelled', 'refunded') DEFAULT 'pending',
                            `payment_date` TIMESTAMP NULL DEFAULT NULL,
                            `description` TEXT DEFAULT NULL,
                            `payment_proof` VARCHAR(500) DEFAULT NULL, --  File chứng từ thanh toán
                            `processed_by` VARCHAR(255) DEFAULT NULL, -- Nhân viên xử lý thanh toán (ID user).
                            `notes` TEXT DEFAULT NULL,

                            `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                            `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                            `created_by` VARCHAR(255) DEFAULT NULL,
                            `modified_by` VARCHAR(255) DEFAULT NULL,

                            PRIMARY KEY (`id`),
                            FOREIGN KEY (`booking_id`) REFERENCES `bookings`(`id`) ON DELETE CASCADE,
                            UNIQUE KEY `payment_code_unique` (`payment_code`)

);

--  ================================
--  7. HỆ THỐNG ĐÁNH GIÁ
--  ================================

--  Bảng reviews (đánh giá tour)
CREATE TABLE `reviews` (
                           `id` BIGINT NOT NULL AUTO_INCREMENT,
                           `booking_id` BIGINT NOT NULL,
                           `customer_id` BIGINT NOT NULL,
                           `tour_id` BIGINT NOT NULL,
                           `rating` TINYINT NOT NULL CHECK (`rating` BETWEEN 1 AND 5),
                           `title` VARCHAR(255) DEFAULT NULL,
                           `comment` TEXT DEFAULT NULL,
                           `status` ENUM( 'approved', 'hidden') DEFAULT 'approved',
                           `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                           `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                           `created_by` VARCHAR(255) DEFAULT NULL,
                           `modified_by` VARCHAR(255) DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           FOREIGN KEY (`booking_id`) REFERENCES `bookings`(`id`) ON DELETE CASCADE,
                           FOREIGN KEY (`customer_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
                           FOREIGN KEY (`tour_id`) REFERENCES `tours`(`id`) ON DELETE CASCADE,
                           UNIQUE KEY `booking_review_unique` (`booking_id`)
);

--  Bảng review_images
CREATE TABLE `review_images` (
                                 `id` BIGINT NOT NULL AUTO_INCREMENT,
                                 `review_id` BIGINT NOT NULL,
                                 `cloudinary_public_id` VARCHAR(255) NOT NULL,
                                 `cloudinary_url` VARCHAR(255) NOT NULL,
                                 `alt_text` VARCHAR(255) DEFAULT NULL,
                                 `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                                 `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                 `created_by` VARCHAR(255) DEFAULT NULL,
                                 `modified_by` VARCHAR(255) DEFAULT NULL,
                                 PRIMARY KEY (`id`),
                                 FOREIGN KEY (`review_id`) REFERENCES `reviews`(`id`) ON DELETE CASCADE ON UPDATE NO ACTION
);

--  ================================
--  8. HỆ THỐNG THÔNG BÁO
--  ================================

CREATE TABLE notifications (
                               `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                               `user_id` BIGINT NOT NULL,
                               `title` VARCHAR(255) NOT NULL,
                               `message` TEXT NOT NULL,
                               `type` ENUM('booking', 'payment', 'system', 'promotion', 'reminder') NOT NULL,
                               `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                               `modified_date` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                               FOREIGN KEY (user_id) REFERENCES users(id)
);
