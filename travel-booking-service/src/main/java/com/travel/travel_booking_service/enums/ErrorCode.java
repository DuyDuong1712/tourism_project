package com.travel.travel_booking_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    // Mã lỗi chung
    UNKNOWN_ERROR(1000, "Lỗi không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR(1001, "Lỗi validation", HttpStatus.BAD_REQUEST),
    DATABASE_ERROR(1002, "Lỗi cơ sở dữ liệu", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED_ACCESS(1003, "Không có quyền truy cập", HttpStatus.FORBIDDEN),
    RESOURCE_NOT_FOUND(1004, "Tài nguyên không tồn tại", HttpStatus.NOT_FOUND),
    DUPLICATE_RESOURCE(1005, "Tài nguyên đã tồn tại", HttpStatus.CONFLICT),
    OPERATION_FAILED(1006, "Thao tác thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TOKEN(1007, "Token không hợp lệ hoặc đã hết hạn", HttpStatus.UNAUTHORIZED),
    EXPIRED_SESSION(1008, "Phiên làm việc đã hết hạn", HttpStatus.UNAUTHORIZED),
    SYSTEM_MAINTENANCE(1009, "Hệ thống đang bảo trì", HttpStatus.SERVICE_UNAVAILABLE),

    // Mã lỗi liên quan đến User
    USER_NOT_FOUND(2000, "Không tìm thấy người dùng", HttpStatus.NOT_FOUND),
    USER_EXISTS(2001, "Tên đăng nhập đã tồn tại", HttpStatus.CONFLICT),
    USER_INACTIVE(2002, "Tài khoản không hoạt động", HttpStatus.FORBIDDEN),
    INVALID_PASSWORD(2003, "Mật khẩu không đúng", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS(2004, "Email đã được sử dụng", HttpStatus.CONFLICT),
    INVALID_ROLE_ASSIGNMENT(2005, "Gán quyền không hợp lệ", HttpStatus.BAD_REQUEST),
    PROFILE_UPDATE_FAILED(2006, "Cập nhật thông tin cá nhân thất bại", HttpStatus.INTERNAL_SERVER_ERROR),

    // Mã lỗi liên quan đến Tour
    TOUR_NOT_FOUND(3000, "Không tìm thấy tour", HttpStatus.NOT_FOUND),
    TOUR_CREATION_FAILED(3001, "Tạo tour thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_UPDATE_FAILED(3002, "Cập nhật tour thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_DELETION_FAILED(3003, "Xóa tour thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_FULL(3004, "Tour đã đầy chỗ", HttpStatus.CONFLICT),
    TOUR_EXPIRED(3005, "Tour đã hết hạn", HttpStatus.BAD_REQUEST),
    TOUR_UNAVAILABLE(3006, "Tour không khả dụng", HttpStatus.CONFLICT),
    INVALID_TOUR_DATE(3007, "Ngày tour không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_TOUR_DURATION(3008, "Thời gian tour không hợp lệ", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_SLOTS(3009, "Không đủ chỗ cho tour", HttpStatus.CONFLICT),
    INVALID_TOUR_CATEGORY(3010, "Danh mục tour không hợp lệ", HttpStatus.BAD_REQUEST),

    // Mã lỗi liên quan đến Booking
    BOOKING_NOT_FOUND(4000, "Không tìm thấy đơn đặt tour", HttpStatus.NOT_FOUND),
    BOOKING_CREATION_FAILED(4001, "Tạo đơn đặt tour thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOKING_UPDATE_FAILED(4002, "Cập nhật đơn đặt tour thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOKING_DELETION_FAILED(4003, "Xóa đơn đặt tour thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOKING_EXPIRED(4004, "Đơn đặt tour đã hết hạn", HttpStatus.BAD_REQUEST),
    BOOKING_ALREADY_CANCELLED(4005, "Đơn đặt tour đã được hủy trước đó", HttpStatus.CONFLICT),
    BOOKING_ALREADY_CONFIRMED(4006, "Đơn đặt tour đã được xác nhận trước đó", HttpStatus.CONFLICT),
    INVALID_BOOKING_STATUS_CHANGE(4007, "Thay đổi trạng thái đơn đặt tour không hợp lệ", HttpStatus.BAD_REQUEST),
    DUPLICATE_BOOKING(4008, "Đã tồn tại đơn đặt tour cho người dùng này", HttpStatus.CONFLICT),
    INVALID_BOOKING_DATE(4009, "Ngày đặt tour không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_BOOKING_PARTICIPANTS(4010, "Số người tham gia không hợp lệ", HttpStatus.BAD_REQUEST),

    // Mã lỗi liên quan đến Categories
    CATEGORY_NOT_FOUND(5000, "Không tìm thấy danh mục", HttpStatus.NOT_FOUND),
    CATEGORY_EXISTS(5001, "Danh mục đã tồn tại", HttpStatus.CONFLICT),
    CATEGORY_CREATION_FAILED(5002, "Tạo danh mục thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_UPDATE_FAILED(5003, "Cập nhật danh mục thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_DELETION_FAILED(5004, "Xóa danh mục thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_IN_USE(5005, "Danh mục đang được sử dụng", HttpStatus.CONFLICT),

    // Mã lỗi liên quan đến Destinations
    DESTINATION_NOT_FOUND(6000, "Không tìm thấy điểm đến", HttpStatus.NOT_FOUND),
    DESTINATION_EXISTS(6001, "Điểm đến đã tồn tại", HttpStatus.CONFLICT),
    DESTINATION_CREATION_FAILED(6002, "Tạo điểm đến thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    DESTINATION_UPDATE_FAILED(6003, "Cập nhật điểm đến thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    DESTINATION_DELETION_FAILED(6004, "Xóa điểm đến thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    DESTINATION_IN_USE(6005, "Điểm đến đang được sử dụng", HttpStatus.CONFLICT),
    INVALID_DESTINATION_ORDER(6006, "Thứ tự điểm đến không hợp lệ", HttpStatus.BAD_REQUEST),

    // Mã lỗi liên quan đến Hotel
    HOTEL_NOT_FOUND(7000, "Không tìm thấy khách sạn", HttpStatus.NOT_FOUND),
    HOTEL_EXISTS(7001, "Khách sạn đã tồn tại", HttpStatus.CONFLICT),
    HOTEL_CREATION_FAILED(7002, "Tạo khách sạn thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    HOTEL_UPDATE_FAILED(7003, "Cập nhật khách sạn thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    HOTEL_DELETION_FAILED(7004, "Xóa khách sạn thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    HOTEL_IN_USE(7005, "Khách sạn đang được sử dụng", HttpStatus.CONFLICT),
    INVALID_HOTEL_RATING(7006, "Xếp hạng khách sạn không hợp lệ", HttpStatus.BAD_REQUEST),

    // Mã lỗi liên quan đến Transport
    TRANSPORT_NOT_FOUND(8000, "Không tìm thấy phương tiện", HttpStatus.NOT_FOUND),
    TRANSPORT_EXISTS(8001, "Phương tiện đã tồn tại", HttpStatus.CONFLICT),
    TRANSPORT_CREATION_FAILED(8002, "Tạo phương tiện thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSPORT_UPDATE_FAILED(8003, "Cập nhật phương tiện thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSPORT_DELETION_FAILED(8004, "Xóa phương tiện thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSPORT_IN_USE(8005, "Phương tiện đang được sử dụng", HttpStatus.CONFLICT),
    INVALID_TRANSPORT_TYPE(8006, "Loại phương tiện không hợp lệ", HttpStatus.BAD_REQUEST),

    // Mã lỗi liên quan đến Reviews
    REVIEW_NOT_FOUND(9000, "Không tìm thấy đánh giá", HttpStatus.NOT_FOUND),
    REVIEW_CREATION_FAILED(9001, "Tạo đánh giá thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    REVIEW_UPDATE_FAILED(9002, "Cập nhật đánh giá thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    REVIEW_DELETION_FAILED(9003, "Xóa đánh giá thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    REVIEW_EXISTS(9004, "Đã tồn tại đánh giá cho booking này", HttpStatus.CONFLICT),
    INVALID_REVIEW_RATING(9005, "Điểm đánh giá không hợp lệ", HttpStatus.BAD_REQUEST),
    REVIEW_NOT_ALLOWED(9006, "Không được phép đánh giá (chưa hoàn thành tour)", HttpStatus.FORBIDDEN),

    // Mã lỗi liên quan đến Images
    IMAGE_UPLOAD_FAILED(10000, "Tải lên hình ảnh thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    IMAGE_DELETION_FAILED(10001, "Xóa hình ảnh thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    IMAGE_NOT_FOUND(10002, "Không tìm thấy hình ảnh", HttpStatus.NOT_FOUND),
    INVALID_IMAGE_FORMAT(10003, "Định dạng hình ảnh không hợp lệ", HttpStatus.BAD_REQUEST),
    IMAGE_SIZE_EXCEEDED(10004, "Kích thước hình ảnh vượt quá giới hạn", HttpStatus.BAD_REQUEST),
    CLOUDINARY_ERROR(10005, "Lỗi tương tác với Cloudinary", HttpStatus.SERVICE_UNAVAILABLE),

    // Mã lỗi liên quan đến Assignment và Notification
    ASSIGNMENT_NOT_FOUND(11000, "Không tìm thấy phân công", HttpStatus.NOT_FOUND),
    ASSIGNMENT_EXISTS(11001, "Phân công đã tồn tại", HttpStatus.CONFLICT),
    ASSIGNMENT_CREATION_FAILED(11002, "Tạo phân công thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    ASSIGNMENT_UPDATE_FAILED(11003, "Cập nhật phân công thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    GUIDE_UNAVAILABLE(11004, "Hướng dẫn viên không khả dụng cho thời gian này", HttpStatus.CONFLICT),
    NOTIFICATION_FAILED(11005, "Gửi thông báo thất bại", HttpStatus.INTERNAL_SERVER_ERROR),

    // Mã lỗi liên quan đến Payment
    PAYMENT_FAILED(12000, "Thanh toán thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    PAYMENT_EXPIRED(12001, "Thanh toán đã hết hạn", HttpStatus.BAD_REQUEST),
    PAYMENT_ALREADY_PROCESSED(12002, "Thanh toán đã được xử lý trước đó", HttpStatus.CONFLICT),
    REFUND_FAILED(12003, "Hoàn tiền thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PAYMENT_AMOUNT(12004, "Số tiền thanh toán không hợp lệ", HttpStatus.BAD_REQUEST),
    PAYMENT_GATEWAY_ERROR(12005, "Lỗi cổng thanh toán", HttpStatus.SERVICE_UNAVAILABLE),

    // Mã lỗi liên quan đến Booking bổ sung
    INVALID_BOOKING_PAYMENT_STATUS(4011, "Trạng thái thanh toán không hợp lệ", HttpStatus.BAD_REQUEST),
    BOOKING_CANCELLATION_NOT_ALLOWED(4012, "Không thể hủy đơn đặt tour", HttpStatus.FORBIDDEN),
    BOOKING_MODIFICATION_NOT_ALLOWED(4013, "Không thể chỉnh sửa đơn đặt tour", HttpStatus.FORBIDDEN),
    INVALID_BOOKING_REFUND(4014, "Yêu cầu hoàn tiền không hợp lệ", HttpStatus.BAD_REQUEST),
    BOOKING_CONFIRMATION_FAILED(4015, "Xác nhận đơn đặt tour thất bại", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
