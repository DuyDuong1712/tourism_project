-- ================================
-- DỮ LIỆU MẪU CHO HỆ THỐNG QUẢN LÝ TOUR
-- ================================

USE tour_management_adv;

-- 1. Dữ liệu cho bảng roles
INSERT INTO `roles` (`code`, `description`, `created_by`) VALUES
('ADMIN', 'Quản trị viên hệ thống', 'system'),
('MANAGER', 'Quản lý tour', 'system'),
('GUIDE', 'Hướng dẫn viên du lịch', 'system'),
('CUSTOMER', 'Khách hàng', 'system'),
('STAFF', 'Nhân viên', 'system');

-- 2. Dữ liệu cho bảng permissions
-- Insert dữ liệu cho bảng permissions
-- Các quyền được sắp xếp theo từng nhóm chức năng

-- Quyền liên quan đến Tour
INSERT INTO `permissions` (`code`, `description`, `in_active`, `is_deleted`, `created_by`) VALUES
('READ_TOUR', 'Quyền xem danh sách và chi tiết tour', 1, 0, 'system'),
('CREATE_TOUR', 'Quyền tạo mới tour', 1, 0, 'system'),
('UPDATE_TOUR', 'Quyền cập nhật thông tin tour', 1, 0, 'system'),
('DELETE_TOUR', 'Quyền xóa tour', 1, 0, 'system');

-- Quyền liên quan đến Category
INSERT INTO `permissions` (`code`, `description`, `in_active`, `is_deleted`, `created_by`) VALUES
('READ_CATEGORY', 'Quyền xem danh sách và chi tiết danh mục', 1, 0, 'system'),
('CREATE_CATEGORY', 'Quyền tạo mới danh mục', 1, 0, 'system'),
('UPDATE_CATEGORY', 'Quyền cập nhật thông tin danh mục', 1, 0, 'system'),
('DELETE_CATEGORY', 'Quyền xóa danh mục', 1, 0, 'system');

-- Quyền liên quan đến Departure
INSERT INTO `permissions` (`code`, `description`, `in_active`, `is_deleted`, `created_by`) VALUES
('READ_DEPARTURE', 'Quyền xem danh sách và chi tiết điểm khởi hành', 1, 0, 'system'),
('CREATE_DEPARTURE', 'Quyền tạo mới điểm khởi hành', 1, 0, 'system'),
('UPDATE_DEPARTURE', 'Quyền cập nhật thông tin điểm khởi hành', 1, 0, 'system'),
('DELETE_DEPARTURE', 'Quyền xóa điểm khởi hành', 1, 0, 'system');

-- Quyền liên quan đến Destination
INSERT INTO `permissions` (`code`, `description`, `in_active`, `is_deleted`, `created_by`) VALUES
('READ_DESTINATION', 'Quyền xem danh sách và chi tiết điểm đến', 1, 0, 'system'),
('CREATE_DESTINATION', 'Quyền tạo mới điểm đến', 1, 0, 'system'),
('UPDATE_DESTINATION', 'Quyền cập nhật thông tin điểm đến', 1, 0, 'system'),
('DELETE_DESTINATION', 'Quyền xóa điểm đến', 1, 0, 'system');

-- Quyền liên quan đến Transportation
INSERT INTO `permissions` (`code`, `description`, `in_active`, `is_deleted`, `created_by`) VALUES
('READ_TRANSPORTATION', 'Quyền xem danh sách và chi tiết phương tiện di chuyển', 1, 0, 'system'),
('CREATE_TRANSPORTATION', 'Quyền tạo mới phương tiện di chuyển', 1, 0, 'system'),
('UPDATE_TRANSPORTATION', 'Quyền cập nhật thông tin phương tiện di chuyển', 1, 0, 'system'),
('DELETE_TRANSPORTATION', 'Quyền xóa phương tiện di chuyển', 1, 0, 'system');

-- Quyền liên quan đến Order
INSERT INTO `permissions` (`code`, `description`, `in_active`, `is_deleted`, `created_by`) VALUES
('READ_ORDER', 'Quyền xem danh sách và chi tiết đơn hàng', 1, 0, 'system'),
('UPDATE_ORDER', 'Quyền cập nhật trạng thái và thông tin đơn hàng', 1, 0, 'system');

-- Quyền liên quan đến Roles
INSERT INTO `permissions` (`code`, `description`, `in_active`, `is_deleted`, `created_by`) VALUES
('READ_ROLES', 'Quyền xem danh sách và chi tiết vai trò', 1, 0, 'system'),
('CREATE_ROLES', 'Quyền tạo mới vai trò', 1, 0, 'system'),
('UPDATE_ROLES', 'Quyền cập nhật thông tin vai trò', 1, 0, 'system'),
('DELETE_ROLES', 'Quyền xóa vai trò', 1, 0, 'system');

-- Quyền liên quan đến Permissions
INSERT INTO `permissions` (`code`, `description`, `in_active`, `is_deleted`, `created_by`) VALUES
('READ_PERMISSIONS', 'Quyền xem danh sách quyền hạn trong hệ thống', 1, 0, 'system');

-- Quyền liên quan đến Admin Account
INSERT INTO `permissions` (`code`, `description`, `in_active`, `is_deleted`, `created_by`) VALUES
('READ_ADMIN', 'Quyền xem danh sách và thông tin tài khoản quản trị', 1, 0, 'system');

-- Quyền liên quan đến Dashboard
INSERT INTO `permissions` (`code`, `description`, `in_active`, `is_deleted`, `created_by`) VALUES
('READ_DASHBOARD', 'Quyền truy cập và xem thông tin tổng quan trên Dashboard', 1, 0, 'system');

-- 3. Phân quyền role_permission
INSERT INTO `role_permission` (`role_id`, `permission_id`, `created_by`) VALUES
-- ADMIN có tất cả quyền
(1, 1, 'system'), (1, 2, 'system'), (1, 3, 'system'), (1, 4, 'system'),
(1, 5, 'system'), (1, 6, 'system'), (1, 7, 'system'), (1, 8, 'system'),
(1, 9, 'system'), (1, 10, 'system'), (1, 11, 'system'), (1, 12, 'system'),
(1, 13, 'system'), (1, 14, 'system'), (1, 15, 'system'),
-- MANAGER
(2, 1, 'system'), (2, 2, 'system'), (2, 3, 'system'),
(2, 5, 'system'), (2, 6, 'system'), (2, 7, 'system'),
(2, 9, 'system'), (2, 10, 'system'), (2, 11, 'system'), (2, 12, 'system'),
(2, 13, 'system'), (2, 14, 'system'), (2, 15, 'system'),
-- GUIDE
(3, 5, 'system'), (3, 9, 'system'), (3, 11, 'system'),
-- CUSTOMER
(4, 5, 'system'), (4, 10, 'system'),
-- STAFF
(5, 1, 'system'), (5, 5, 'system'), (5, 9, 'system'), (5, 11, 'system'), (5, 13, 'system');

-- 4. Dữ liệu cho bảng categories
INSERT INTO `categories` (`name`, `description`, `created_by`) VALUES
('CAO CẤP', 'Dòng tour cao cấp đỉnh nóc kịch trần bay phấp phới', 'admin'),
('TIÊU CHUẨN',	'Dòng sản phẩm thế mạnh và chủ lực của Vietravel. Du Khách sẽ hoàn toàn an tâm và hài lòng với chất lượng dịch vụ chọn lọc, những điểm đến hấp dẫn, trải nghiệm đáng nhớ. Sản phẩm được thiết kế cùng đội ngũ luôn tạo sự mới lạ và khác biệt trên thị trường và tương xứng với giá trị mà Du Khách đã bỏ ra.', 'admin'),
('TIẾT KIỆM',	'Dòng tour này Vietravel hướng đến mục tiêu bù lỗ! Du Khách nào cũng có cơ hội du lịch với mức chi phí tiết kiệm nhất. Các điểm tham quan và dịch vụ chọn lọc phù hợp và ngân sách của Du Khách nhưng vẫn đảm bảo hành trình du lịch đầy đủ và thú vị.', 'admin'),
('GIÁ TỐT',	'Dòng tour có mức giá hấp dẫn nhất thị trường do kết hợp các ưu đãi từ Đối Tác Vàng của Vietravel... suốt hành trình là những trải nghiệm và điểm tham quan cơ bản tại từng điểm đến và dịch vụ trong mức tiêu chuẩn tương xứng và chi phí.', 'admin');

-- 5. Dữ liệu cho bảng destinations
INSERT INTO `destinations` (`name`, `code`, `description`, `created_by`) VALUES
('Hà Nội', 'HN', 'Thủ đô ngàn năm văn hiến', 'admin'),
('Hồ Chí Minh', 'HCM', 'Thành phố năng động nhất Việt Nam', 'admin'),
('Đà Nẵng', 'DN', 'Thành phố đáng sống bên bờ biển', 'admin'),
('Hạ Long', 'HL', 'Vịnh Hạ Long kỳ quan thế giới', 'admin'),
('Sapa', 'SP', 'Thị trấn trong mây với ruộng bậc thang', 'admin'),
('Hội An', 'HO', 'Phố cổ với kiến trúc độc đáo', 'admin'),
('Phú Quốc', 'PQ', 'Đảo ngọc của Việt Nam', 'admin'),
('Nha Trang', 'NT', 'Thành phố biển xinh đẹp', 'admin'),
('Đà Lạt', 'DL', 'Thành phố ngàn hoa', 'admin');

-- 6. Dữ liệu cho bảng departures
INSERT INTO `departures` (`name`, `code`, `address`, `created_by`) VALUES
('Hà Nội', 'HN_DEP', '123 Đường Láng, Đống Đa, Hà Nội', 'admin'),
('Hồ Chí Minh', 'HCM_DEP', '456 Nguyễn Huệ, Quận 1, TP.HCM', 'admin'),
('Đà Nẵng', 'DN_DEP' '789 Bạch Đằng, Hải Châu, Đà Nẵng', 'admin');

-- 7. Dữ liệu cho bảng transports
INSERT INTO `transports` (`name`, `type`, `brand`, `description`, `created_by`) VALUES
('Xe khách 45 chỗ', 'bus', 'Hyundai', 'Xe khách cao cấp điều hòa', 'admin'),
('Xe limousine 16 chỗ', 'limousine', 'Ford', 'Xe limousine sang trọng', 'admin'),
('Máy bay', 'plane', 'Vietnam Airlines', 'Máy bay thương mại', 'admin'),
('Tàu hỏa', 'train', 'VNR', 'Tàu hỏa Việt Nam', 'admin'),
('Xe 7 chỗ', 'car', 'Toyota', 'Xe gia đình 7 chỗ', 'admin'),
('Tàu thủy', 'boat', 'Shipco', 'Tàu du lịch cao cấp', 'admin');

-- 8. Dữ liệu cho bảng users
INSERT INTO `users` (`role_id`, `username`, `password`, `fullname`, `phone`, `email`, `address`, `date_of_birth`, `gender`, `created_by`) VALUES
-- Admin
(1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9tYjKUiFjLMyInG', 'Quản trị viên', '0123456789', 'admin@tourmanagement.com', 'Hà Nội', '1990-01-01', 'male', 'system'),
-- Manager
(2, 'manager1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9tYjKUiFjLMyInG', 'Nguyễn Văn Quản', '0987654321', 'manager1@tourmanagement.com', 'TP.HCM', '1985-05-15', 'male', 'admin'),
-- Guide
(3, 'guide1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9tYjKUiFjLMyInG', 'Trần Thị Hương', '0912345678', 'guide1@tourmanagement.com', 'Đà Nẵng', '1992-03-20', 'female', 'admin'),
(3, 'guide2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9tYjKUiFjLMyInG', 'Lê Văn Minh', '0908765432', 'guide2@tourmanagement.com', 'Hà Nội', '1988-12-10', 'male', 'admin'),
-- Customers
(4, 'customer1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9tYjKUiFjLMyInG', 'Phạm Thị Lan', '0901234567', 'customer1@gmail.com', 'Hà Nội', '1995-07-25', 'female', 'system'),
(4, 'customer2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9tYjKUiFjLMyInG', 'Hoàng Văn Nam', '0934567890', 'customer2@gmail.com', 'TP.HCM', '1990-11-15', 'male', 'system'),
-- Staff
(5, 'staff1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9tYjKUiFjLMyInG', 'Vũ Thị Mai', '0945678901', 'staff1@tourmanagement.com', 'Đà Nẵng', '1993-09-08', 'female', 'admin');

-- 9. Dữ liệu cho bảng tours
INSERT INTO `tours` (`title`, `description`, `summary`, `category_id`, `departure_id`, `destination_id`, `transport_id`, `duration`, `min_slots`, `max_slots`, `created_by`) VALUES
('Tour Hạ Long - Sapa 4N3Đ', 
'Khám phá vẻ đẹp hùng vĩ của Vịnh Hạ Long với hàng nghìn đảo đá vôi kỳ thú và thị trấn Sapa trong mây với ruộng bậc thang tuyệt đẹp. Tour kết hợp tham quan di sản thiên nhiên thế giới và văn hóa đặc sắc của các dân tộc miền núi.',
'Tour kết hợp Hạ Long - Sapa 4 ngày 3 đêm với nhiều trải nghiệm thú vị',
3, 1, 4, 1, '4 ngày 3 đêm', 10, 35, 'admin'),

('Tour Phú Quốc 3N2Đ', 
'Nghỉ dưỡng tại đảo ngọc Phú Quốc với những bãi biển đẹp nhất Việt Nam. Trải nghiệm lặn ngắm san hô, thưởng thức hải sản tươi ngon và khám phá rừng quốc gia. Tour bao gồm vé máy bay khứ hồi từ TP.HCM.',
'Tour nghỉ dưỡng biển Phú Quốc 3 ngày 2 đêm',
1, 2, 7, 3, '3 ngày 2 đêm', 8, 25, 'admin'),

('Tour Hội An - Đà Nẵng 2N1Đ', 
'Khám phá phố cổ Hội An với kiến trúc độc đáo, đèn lồng rực rỡ và ẩm thực đặc sắc. Tham quan Đà Nẵng với cầu Vàng nổi tiếng, Bà Nà Hills và những bãi biển tuyệt đẹp.',
'Tour khám phá Hội An - Đà Nẵng 2 ngày 1 đêm',
3, 1, 6, 2, '2 ngày 1 đêm', 12, 30, 'admin'),

('Tour Đà Lạt 2N1Đ', 
'Khám phá thành phố ngàn hoa Đà Lạt với khí hậu mát mẻ quanh năm. Tham quan các vườn hoa, thác nước, hồ Xuân Hương và thưởng thức các đặc sản địa phương như bánh tráng nướng, nem nướng.',
'Tour Đà Lạt thành phố ngàn hoa 2 ngày 1 đêm',
4, 2, 9, 1, '2 ngày 1 đêm', 15, 40, 'admin');

-- 10. Dữ liệu cho bảng tour_information
INSERT INTO `tour_information` (`tour_id`, `attractions`, `cuisine`, `suitable_object`, `ideal_time`, `what_included`, `what_excluded`, `important_notes`, `created_by`) VALUES
(1, 
'Vịnh Hạ Long, Đảo Titop, Hang Sửng Sốt, Thị trấn Sapa, Ruộng bậc thang Mường Hoa, Chợ tình Sapa', 
'Hải sản tươi sống, Thắng cố, Cá nướng lá chuối, Xôi ngũ sắc', 
'Phù hợp mọi lứa tuổi, gia đình có trẻ em', 
'Quanh năm, đẹp nhất từ tháng 3-5 và tháng 9-11', 
'Xe đưa đón, HDV, Vé tham quan, Bữa ăn theo chương trình, Khách sạn 3*', 
'Vé máy bay, Bảo hiểm, Chi phí cá nhân, Tip HDV', 
'Mang theo áo ấm khi đi Sapa, giày thoải mái để đi bộ', 
'admin'),

(2, 
'Bãi Sao, Cáp treo Hòn Thơm, Công viên Safari, Dinh Cậu, Chợ đêm Dương Đông', 
'Hải sản tươi sống, Bánh kẹo sim, Nước mắm Phú Quốc, Cà phê chồn', 
'Gia đình, cặp đôi, nhóm bạn trẻ', 
'Tháng 11 đến tháng 4 (mùa khô)', 
'Vé máy bay khứ hồi, Resort 4*, Xe đưa đón, HDV, Bữa ăn theo chương trình', 
'Bảo hiểm, Chi phí cá nhân, Hoạt động thể thao nước', 
'Mang theo kem chống nắng, đồ bơi, thuốc cá nhân', 
'admin'),

(3, 
'Phố cổ Hội An, Chùa Cầu, Cầu Vàng Ba Na Hills, Ngũ Hành Sơn, Bãi biển Mỹ Khê', 
'Cao lầu, Bánh mì Hội An, Chè bưởi, Mì Quảng, Bánh xèo', 
'Mọi lứa tuổi, đặc biệt phù hợp gia đình', 
'Quanh năm, tránh mùa mưa lũ tháng 9-12', 
'Khách sạn 3*, Xe đưa đón, HDV, Bữa ăn, Vé tham quan', 
'Vé máy bay, Bảo hiểm, Chi phí mua sắm cá nhân', 
'Mang theo áo mưa, giày đi bộ thoải mái', 
'admin'),

(4, 
'Hồ Xuân Hương, Dinh Bảo Đại, Thác Elephant, Đồi Cù, Valley of Love, Crazy House', 
'Bánh tráng nướng, Nem nướng, Dâu tây, Artichoke, Cà phê', 
'Gia đình, cặp đôi, nhóm bạn', 
'Quanh năm, đẹp nhất từ tháng 12-3', 
'Khách sạn 3*, Xe đưa đón, HDV, Bữa ăn theo chương trình, Vé tham quan', 
'Vé máy bay, Bảo hiểm, Chi phí cá nhân', 
'Mang theo áo ấm vì Đà Lạt mát quanh năm', 
'admin');

-- 11. Dữ liệu cho bảng tour_schedules
INSERT INTO `tour_schedules` (`tour_id`, `day`, `title`, `activities`, `created_by`) VALUES
-- Tour Hạ Long - Sapa
(1, 1, 'Hà Nội - Hạ Long', 'Đón khách tại Hà Nội - Di chuyển đến Hạ Long - Lên tàu du lịch - Thăm hang Sửng Sốt - Đảo Titop - Nghỉ đêm trên tàu'),
(1, 2, 'Hạ Long - Sapa', 'Ngắm bình minh trên vịnh - Về bến - Di chuyển lên Sapa - Check in khách sạn - Tự do khám phá chợ Sapa'),
(1, 3, 'Sapa - Bản Cát Cát', 'Trekking bản Cát Cát - Thác Bạc - Ruộng bậc thang Mường Hoa - Thăm nhà dân tộc H\'Mông - Về khách sạn nghỉ ngơi'),
(1, 4, 'Sapa - Hà Nội', 'Tham quan chợ tình Sapa (nếu đúng thứ 7) - Mua đặc sản - Về Hà Nội - Kết thúc tour'),

-- Tour Phú Quốc
(2, 1, 'TP.HCM - Phú Quốc', 'Bay từ TP.HCM đến Phú Quốc - Đón tại sân bay - Check in resort - Nghỉ ngơi - Thăm chợ đêm Dương Đông'),
(2, 2, 'Tour 4 đảo', 'Tour câu cá - lặn ngắm san hô 4 đảo - Hòn Thơm - Hòn Móng Tay - Hòn Gầm Ghì - BBQ hải sản trên biển'),
(2, 3, 'Tham quan đảo', 'Safari Phú Quốc - Cáp treo Hòn Thơm dài nhất thế giới - Bãi Sao - Dinh Cậu - Ra sân bay về TP.HCM'),

-- Tour Hội An - Đà Nẵng  
(3, 1, 'Hà Nội - Đà Nẵng - Hội An', 'Bay từ Hà Nội - Đến Đà Nẵng - Thăm Ngũ Hành Sơn - Về Hội An - Phố cổ Hội An - Chùa Cầu'),
(3, 2, 'Ba Na Hills - Đà Nẵng', 'Tham quan Ba Na Hills - Cầu Vàng - Làng Pháp cổ - Về sân bay Đà Nẵng - Bay về Hà Nội'),

-- Tour Đà Lạt
(4, 1, 'TP.HCM - Đà Lạt', 'Khởi hành từ TP.HCM - Đến Đà Lạt - Thăm hồ Xuân Hương - Dinh Bảo Đại - Chợ đêm Đà Lạt'),
(4, 2, 'Tham quan Đà Lạt', 'Valley of Love - Thác Elephant - Crazy House - Đồi Cù - Mua đặc sản - Về TP.HCM');

-- 12. Dữ liệu cho bảng tour_departures
INSERT INTO `tour_departures` (`tour_id`, `guide_id`, `departure_date`, `return_date`, `meeting_point`, `meeting_time`, `available_slots`, `status`, `created_by`) VALUES
(1, 3, '2025-07-15 06:00:00', '2025-07-18 18:00:00', '123 Đường Láng, Đống Đa, Hà Nội', '06:00:00', 35, 'confirmed', 'admin'),
(1, 4, '2025-08-10 06:00:00', '2025-08-13 18:00:00', '123 Đường Láng, Đống Đa, Hà Nội', '06:00:00', 35, 'scheduled', 'admin'),
(2, 3, '2025-07-20 08:00:00', '2025-07-22 20:00:00', 'Sân bay Tân Sơn Nhất', '08:00:00', 25, 'confirmed', 'admin'),
(3, 4, '2025-07-25 07:00:00', '2025-07-26 19:00:00', 'Sân bay Nội Bài', '07:00:00', 30, 'scheduled', 'admin'),
(4, 3, '2025-08-01 07:30:00', '2025-08-02 18:30:00', '456 Nguyễn Huệ, Quận 1, TP.HCM', '07:30:00', 40, 'confirmed', 'admin');

-- 13. Dữ liệu cho bảng tour_pricing
INSERT INTO `tour_pricing` (`tour_departure_id`, `adult_price`, `children_price`, `baby_price`, `infant_price`, `single_room_supplement`, `discount_percent`, `created_by`) VALUES
(1, 4500000, 3500000, 2000000, 500000, 800000, 10, 'admin'),
(2, 4500000, 3500000, 2000000, 500000, 800000, 5, 'admin'),
(3, 6500000, 5000000, 3000000, 800000, 1200000, 15, 'admin'),
(4, 2800000, 2200000, 1500000, 400000, 600000, 0, 'admin'),
(5, 2200000, 1800000, 1200000, 300000, 500000, 8, 'admin');

-- 14. Dữ liệu cho bảng bookings
INSERT INTO `bookings` (`tour_departure_id`, `customer_id`, `adult_count`, `children_count`, `baby_count`, `infant_count`, `total_people`, `single_room_count`, `subtotal`, `discount_amount`, `tax_amount`, `total_amount`, `booking_status`, `payment_status`, `payment_method`, `special_requests`, `created_by`) VALUES
(1, 5, 2, 1, 0, 0, 3, 1, 12500000.00, 1250000.00, 0.00, 11250000.00, 'confirmed', 'paid', 'bank_transfer', 'Yêu cầu phòng tầng cao, view đẹp', 'customer1'),
(3, 6, 2, 0, 0, 0, 2, 0, 13000000.00, 1950000.00, 0.00, 11050000.00, 'confirmed', 'paid', 'cash', 'Không có yêu cầu đặc biệt', 'customer2'),
(5, 5, 4, 0, 0, 0, 4, 2, 8800000.00, 704000.00, 0.00, 8096000.00, 'pending', 'pending', NULL, 'Cần hỗ trợ đưa đón tại nhà', 'customer1');

-- 15. Dữ liệu cho bảng booking_passengers
INSERT INTO `booking_passengers` (`booking_id`, `full_name`, `date_of_birth`, `gender`, `passenger_type`, `created_by`) VALUES
-- Booking 1
(1, 'Phạm Thị Lan', '1995-07-25', 'female', 'adult', 'customer1'),
(1, 'Nguyễn Văn Hùng', '1990-03-10', 'male', 'adult', 'customer1'),
(1, 'Nguyễn Thị Hoa', '2015-05-20', 'female', 'children', 'customer1'),
-- Booking 2
(2, 'Hoàng Văn Nam', '1990-11-15', 'male', 'adult', 'customer2'),
(2, 'Trần Thị Thu', '1992-08-22', 'female', 'adult', 'customer2'),
-- Booking 3
(3, 'Phạm Thị Lan', '1995-07-25', 'female', 'adult', 'customer1'),
(3, 'Phạm Văn Long', '1993-12-01', 'male', 'adult', 'customer1'),
(3, 'Phạm Thị Mai', '1965-04-15', 'female', 'adult', 'customer1'),
(3, 'Phạm Văn Cường', '1960-01-20', 'male', 'adult', 'customer1');

-- 16. Dữ liệu cho bảng payments
INSERT INTO `payments` (`booking_id`, `payment_code`, `amount`, `payment_method`, `payment_status`, `payment_date`, `description`, `processed_by`, `created_by`) VALUES
(1, 'PAY20250615001', 11250000.00, 'bank_transfer', 'completed', '2025-06-15 10:30:00', 'Thanh toán tour Hạ Long - Sapa 4N3Đ', 'staff1', 'customer1'),
(2, 'PAY20250616001', 11050000.00, 'cash', 'completed', '2025-06-16 14:20:00', 'Thanh toán tour Phú Quốc 3N2Đ', 'staff1', 'customer2');

-- 17. Dữ liệu cho bảng reviews (chỉ cho các booking đã hoàn thành)
INSERT INTO `reviews` (`booking_id`, `customer_id`, `tour_id`, `rating`, `title`, `comment`, `created_by`) VALUES
(1, 5, 1, 5, 'Tour tuyệt vời!', 'Hạ Long và Sapa đều rất đẹp. Hướng dẫn viên nhiệt tình, chu đáo. Sẽ giới thiệu bạn bè tham gia.', 'customer1'),
(2, 6, 2, 4, 'Phú Quốc đẹp tuyệt', 'Biển xanh, cát trắng, hải sản ngon. Chỉ tiếc là thời gian hơi ngắn. Mong có tour dài ngày hơn.', 'customer2');

-- 18. Dữ liệu cho bảng notifications
INSERT INTO `notifications` (`user_id`, `title`, `message`, `type`) VALUES
(5, 'Xác nhận đặt tour', 'Tour Hạ Long - Sapa 4N3Đ của bạn đã được xác nhận. Ngày khởi hành: 15/07/2025', 'booking'),
(5, 'Thanh toán thành công', 'Thanh toán tour Hạ Long - Sapa 4N3Đ đã được xử lý thành công. Mã thanh toán: PAY20250615001', 'payment'),
(6, 'Xác nhận đặt tour', 'Tour Phú Quốc 3N2Đ của bạn đã được xác nhận. Ngày khởi hành: 20/07/2025', 'booking'),
(6, 'Thanh toán thành công', 'Thanh toán tour Phú Quốc 3N2Đ đã được xử lý thành công. Mã thanh toán: PAY20250616001', 'payment'),
(5, 'Đặt tour mới', 'Bạn đã đặt tour Đà Lạt 2N1Đ thành công. Vui lòng thanh toán để hoàn tất đặt tour.', 'booking'),
(3, 'Phân công hướng dẫn', 'Bạn được phân công hướng dẫn tour Hạ Long - Sapa khởi hành ngày 15/07/2025', 'system'),
(4, 'Phân công hướng dẫn', 'Bạn được phân công hướng dẫn tour Hạ Long - Sapa khởi hành ngày 10/08/2025', 'system');

-- 19. Dữ liệu cho bảng tour_images (URLs mẫu)
INSERT INTO `tour_images` (`tour_id`, `cloudinary_url`, `alt_text`, `is_primary`, `created_by`) VALUES
-- Tour Hạ Long - Sapa
(1, 'https://res.cloudinary.com/demo/image/upload/v1/halong1.jpg', 'Vịnh Hạ Long kỳ vĩ', 1, 'admin'),
(1, 'https://res.cloudinary.com/demo/image/upload/v1/sapa1.jpg', 'Ruộng bậc thang Sapa', 0, 'admin'),
(1, 'https://res.cloudinary.com/demo/image/upload/v1/halong2.jpg', 'Hang Sửng Sốt', 0, 'admin'),
-- Tour Phú Quốc
(2, 'https://res.cloudinary.com/demo/image/upload/v1/phuquoc1.jpg', 'Bãi biển Phú Quốc', 1, 'admin'),
(2, 'https://res.cloudinary.com/demo/image/upload/v1/phuquoc2.jpg', 'Cáp treo Hòn Thơm', 0, 'admin'),
-- Tour Hội An - Đà Nẵng
(3, 'https://res.cloudinary.com/demo/image/upload/v1/hoian1.jpg', 'Phố cổ Hội An về đêm', 1, 'admin'),
(3, 'https://res.cloudinary.com/demo/image/upload/v1/danang1.jpg', 'Cầu Vàng Ba Na Hills', 0, 'admin'),
-- Tour Đà Lạt
(4, 'https://res.cloudinary.com/demo/image/upload/v1/dalat1.jpg', 'Hồ Xuân Hương Đà Lạt', 1, 'admin'),
(4, 'https://res.cloudinary.com/demo/image/upload/v1/dalat2.jpg', 'Valley of Love', 0, 'admin');

-- 20. Dữ liệu cho bảng review_images
INSERT INTO `review_images` (`review_id`, `cloudinary_public_id`, `cloudinary_url`, `alt_text`, `created_by`) VALUES
(1, 'review_halong_1', 'https://res.cloudinary.com/demo/image/upload/v1/review_halong_1.jpg', 'Ảnh du khách tại Hạ Long', 'customer1'),
(1, 'review_sapa_1', 'https://res.cloudinary.com/demo/image/upload/v1/review_sapa_1.jpg', 'Ảnh du khách tại Sapa', 'customer1'),
(2, 'review_phuquoc_1', 'https://res.cloudinary.com/demo/image/upload/v1/review_phuquoc_1.jpg', 'Ảnh du khách tại Phú Quốc', 'customer2');

-- ================================
-- THỐNG KÊ DỮ LIỆU ĐÃ TẠO
-- ================================

SELECT 'Số lượng dữ liệu đã tạo:' as 'THỐNG KÊ DỮ LIỆU';
SELECT 
    'Roles' as 'Bảng', 
    COUNT(*) as 'Số bản ghi' 
FROM roles
UNION ALL
SELECT 'Permissions', COUNT(*) FROM permissions
UNION ALL
SELECT 'Role-Permission', COUNT(*) FROM role_permission
UNION ALL
SELECT 'Users', COUNT(*) FROM users
UNION ALL
SELECT 'Categories', COUNT(*) FROM categories
UNION ALL
SELECT 'Destinations', COUNT(*) FROM destinations
UNION ALL
SELECT 'Departures', COUNT(*) FROM departures
UNION ALL
SELECT 'Transports', COUNT(*) FROM transports
UNION ALL
SELECT 'Tours', COUNT(*) FROM tours
UNION ALL
SELECT 'Tour Information', COUNT(*) FROM tour_information
UNION ALL
SELECT 'Tour Schedules', COUNT(*) FROM tour_schedules
UNION ALL
SELECT 'Tour Departures', COUNT(*) FROM tour_departures
UNION ALL
SELECT 'Tour Pricing', COUNT(*) FROM tour_pricing
UNION ALL
SELECT 'Bookings', COUNT(*) FROM bookings
UNION ALL
SELECT 'Booking Passengers', COUNT(*) FROM booking_passengers
UNION ALL
SELECT 'Payments', COUNT(*) FROM payments
UNION ALL
SELECT 'Reviews', COUNT(*) FROM reviews
UNION ALL
SELECT 'Tour Images', COUNT(*) FROM tour_images
UNION ALL
SELECT 'Review Images', COUNT(*) FROM review_images
UNION ALL
SELECT 'Notifications', COUNT(*) FROM notifications;