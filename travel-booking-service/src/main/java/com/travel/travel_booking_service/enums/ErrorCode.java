package com.travel.travel_booking_service.enums;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // Mã lỗi chung
    UNKNOWN_ERROR(1000, "Lỗi không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR(1001, "Lỗi validation", HttpStatus.BAD_REQUEST),
    DATABASE_ERROR(1002, "Lỗi cơ sở dữ liệu", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(1003, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED_ACCESS(1004, "Không có quyền truy cập", HttpStatus.FORBIDDEN),
    RESOURCE_NOT_FOUND(1005, "Tài nguyên không tồn tại", HttpStatus.NOT_FOUND),
    DUPLICATE_RESOURCE(1006, "Tài nguyên đã tồn tại", HttpStatus.CONFLICT),
    OPERATION_FAILED(1007, "Thao tác thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TOKEN(1008, "Token không hợp lệ hoặc đã hết hạn", HttpStatus.UNAUTHORIZED),
    EXPIRED_SESSION(1009, "Phiên làm việc đã hết hạn", HttpStatus.UNAUTHORIZED),
    SYSTEM_MAINTENANCE(1010, "Hệ thống đang bảo trì", HttpStatus.SERVICE_UNAVAILABLE),
    INVALID_KEY(1011, "Không tìm thấy mã lỗi", HttpStatus.BAD_REQUEST),

    // Mã lỗi liên quan đến User
    USER_NOT_FOUND(2000, "Không tìm thấy người dùng", HttpStatus.NOT_FOUND),
    USER_EXISTS(2001, "Tên đăng nhập đã tồn tại", HttpStatus.CONFLICT),
    USER_INACTIVE(2002, "Tài khoản không hoạt động", HttpStatus.FORBIDDEN),
    INVALID_PASSWORD(2003, "Mật khẩu không đúng", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS(2004, "Email đã được sử dụng", HttpStatus.CONFLICT),
    INVALID_ROLE_ASSIGNMENT(2005, "Gán quyền không hợp lệ", HttpStatus.BAD_REQUEST),
    PROFILE_UPDATE_FAILED(2006, "Cập nhật thông tin cá nhân thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_USERNAME_LENGTH(2007, "Tên đăng nhập phải có độ dài từ 5 đến 30 ký tự", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_LENGTH(2008, "Mật khẩu phải có độ dài từ 5 ký tự", HttpStatus.BAD_REQUEST),
    INVALID_FULLNAME_LENGTH(2009, "Họ tên phải có độ dài từ 5 ký tự", HttpStatus.BAD_REQUEST),
    USERNAME_REQUIRED(2010, "Tên đăng nhập không được để trống", HttpStatus.BAD_REQUEST),
    PASSWORD_REQUIRED(2011, "Mật khẩu không được để trống", HttpStatus.BAD_REQUEST),
    FULLNAME_REQUIRED(2012, "Họ và tên không được để trống", HttpStatus.BAD_REQUEST),
    PHONENUMBER_REQUIRED(2013, "Số điện thoại không được để trống", HttpStatus.BAD_REQUEST),
    PHONENUMBER_INVALID(2014, "Số điện thoại không đúng định dạng", HttpStatus.BAD_REQUEST),
    EMAIL_REQUIRED(2015, "Email không được để trống", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(2016, "Email không đúng định dạng", HttpStatus.BAD_REQUEST),
    INVALID_DOB(2017, "Bạn phải đủ 18 tuổi", HttpStatus.BAD_REQUEST),
    RETYPEPASSWORD_NOT_MATCH(2018, "Nhập lại mật khẩu không trùng", HttpStatus.BAD_REQUEST),
    RETYPEMAIL_NOT_MATCH(2018, "Nhập lại email không trùng", HttpStatus.BAD_REQUEST),

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
    CATEGORY_NAME_REQUIRED(5006, "Tên danh mục không được để trống", HttpStatus.BAD_REQUEST),

    // Mã lỗi liên quan đến Destinations
    DESTINATION_NOT_FOUND(6000, "Không tìm thấy điểm đến", HttpStatus.NOT_FOUND),
    DESTINATION_EXISTS(6001, "Điểm đến đã tồn tại", HttpStatus.CONFLICT),
    DESTINATION_CREATION_FAILED(6002, "Tạo điểm đến thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    DESTINATION_UPDATE_FAILED(6003, "Cập nhật điểm đến thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    DESTINATION_DELETION_FAILED(6004, "Xóa điểm đến thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    DESTINATION_IN_USE(6005, "Điểm đến đang được sử dụng", HttpStatus.CONFLICT),
    DESTINATION_NAME_REQUIRED(6006, "Tên địa điểm không được để trống", HttpStatus.BAD_REQUEST),
    DESTINATION_CODE_REQUIRED(6007, "Mã địa điểm không được để trống", HttpStatus.BAD_REQUEST),
    INVALID_DESTINATION_NAME(6008, "Tên địa điểm không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_DESTINATION_CODE(6009, "Mã địa điểm không hợp lệ", HttpStatus.BAD_REQUEST),
    DESTINATION_CODE_EXISTS(6010, "Mã địa điểm đã tồn tại", HttpStatus.CONFLICT),
    DESTINATION_HAS_CHILDREN(
            6010, "Địa điểm này đang có các địa điểm con, vui lòng xóa các địa điểm con trước", HttpStatus.BAD_REQUEST),
    INVALID_PARENT_DESTINATION(6011, "Địa điểm cha không hợp lệ. Không thể gán địa điểm cha này.", HttpStatus.BAD_REQUEST),

    // Mã lỗi liên quan đến departure
    DEPARTURE_NOT_FOUND(6000, "Không tìm thấy điểm khởi hành", HttpStatus.NOT_FOUND),
    DEPARTURE_EXISTS(6001, "Điểm khởi hành đã tồn tại", HttpStatus.CONFLICT),
    DEPARTURE_CODE_EXISTS(6001, "Mã điểm khởi hành đã tồn tại", HttpStatus.CONFLICT),
    DEPARTURE_CREATION_FAILED(6002, "Tạo điểm khởi hành thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    DEPARTURE_UPDATE_FAILED(6003, "Cập nhật điểm khởi hành thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    DEPARTURE_DELETION_FAILED(6004, "Xóa điểm khởi hành thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    DEPARTURE_IN_USE(6005, "Điểm khởi hành đang được sử dụng", HttpStatus.CONFLICT),
    DEPARTURE_NAME_REQUIRED(6006, "Tên điểm khởi hành không được để trống", HttpStatus.BAD_REQUEST),
    DEPARTURE_CODE_REQUIRED(6007, "Mã điểm khởi hành không được để trống", HttpStatus.BAD_REQUEST),
    DEPARTURE_ADDRESS_REQUIRED(6007, "Mã điểm khởi hành không được để trống", HttpStatus.BAD_REQUEST),
    INVALID_DEPARTURE_NAME(6008, "Tên điểm khởi hành không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_DEPARTURE_CODE(6009, "Mã điểm khởi hành không hợp lệ", HttpStatus.BAD_REQUEST),

    // Mã lỗi liên quan đến Transport
    TRANSPORT_NOT_FOUND(8000, "Không tìm thấy phương tiện", HttpStatus.NOT_FOUND),
    TRANSPORT_EXISTS(8001, "Phương tiện đã tồn tại", HttpStatus.CONFLICT),
    TRANSPORT_CREATION_FAILED(8002, "Tạo phương tiện thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSPORT_UPDATE_FAILED(8003, "Cập nhật phương tiện thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSPORT_DELETION_FAILED(8004, "Xóa phương tiện thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSPORT_IN_USE(8005, "Phương tiện đang được sử dụng", HttpStatus.CONFLICT),
    INVALID_TRANSPORT_TYPE(8006, "Loại phương tiện không hợp lệ", HttpStatus.BAD_REQUEST),
    TRANSPORT_NAME_REQUIRED(8007, "Tên phương tiện không được để trống", HttpStatus.BAD_REQUEST),
    TRANSPORT_TYPE_REQUIRED(8008, "Loại phương tiện không được để trống", HttpStatus.BAD_REQUEST),
    TRANSPORT_BRAND_REQUIRED(8009, "Hãng phương tiện không được để trống", HttpStatus.BAD_REQUEST),

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

    // Mã lỗi liên quan đến Payment
    PAYMENT_FAILED(12000, "Thanh toán thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    PAYMENT_EXPIRED(12001, "Thanh toán đã hết hạn", HttpStatus.BAD_REQUEST),
    PAYMENT_ALREADY_PROCESSED(12002, "Thanh toán đã được xử lý trước đó", HttpStatus.CONFLICT),
    REFUND_FAILED(12003, "Hoàn tiền thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PAYMENT_AMOUNT(12004, "Số tiền thanh toán không hợp lệ", HttpStatus.BAD_REQUEST),
    PAYMENT_GATEWAY_ERROR(12005, "Lỗi cổng thanh toán", HttpStatus.SERVICE_UNAVAILABLE),

    // Mã lỗi liên quan đến Role
    ROLE_NOT_FOUND(9000, "Không tìm thấy vai trò", HttpStatus.NOT_FOUND),
    ROLE_ADMIN_CAN_NOT_DELETE(9000, "Không thể xóa admin", HttpStatus.NOT_FOUND),
    ROLE_EXISTS(9001, "Vai trò đã tồn tại", HttpStatus.CONFLICT),
    ROLE_CREATION_FAILED(9002, "Tạo vai trò thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    ROLE_UPDATE_FAILED(9003, "Cập nhật vai trò thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    ROLE_DELETION_FAILED(9004, "Xóa vai trò thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    ROLE_IN_USE(9005, "Vai trò đang được sử dụng", HttpStatus.CONFLICT),
    ROLE_NAME_REQUIRED(9006, "Tên vai trò không được để trống", HttpStatus.BAD_REQUEST),
    ROLE_CODE_REQUIRED(9007, "Mã vai trò không được để trống", HttpStatus.BAD_REQUEST),
    ROLE_CODE_INVALID_FORMAT(9012, "Định dạng mã vai trò không hợp lệ", HttpStatus.BAD_REQUEST),
    DEFAULT_ROLE_CANNOT_DELETE(9013, "Không thể xóa vai trò mặc định", HttpStatus.BAD_REQUEST),
    SYSTEM_ROLE_CANNOT_MODIFY(9014, "Không thể chỉnh sửa vai trò hệ thống", HttpStatus.BAD_REQUEST),
    ROLE_PERMISSION_ALREADY_EXISTS(9015, "Vai trò đã tồn tại quyền này", HttpStatus.BAD_REQUEST),

    // Mã lỗi liên quan đến Permission
    PERMISSION_NOT_FOUND(9100, "Không tìm thấy quyền", HttpStatus.NOT_FOUND),
    ROLE_ADMIN_PERMISSION_CAN_NOT_CHANGE(9100, "Quyền của admin không thể thay đổi", HttpStatus.NOT_FOUND),
    PERMISSION_EXISTS(9101, "Quyền đã tồn tại", HttpStatus.CONFLICT),
    PERMISSION_CREATION_FAILED(9102, "Tạo quyền thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    PERMISSION_UPDATE_FAILED(9103, "Cập nhật quyền thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    PERMISSION_DELETION_FAILED(9104, "Xóa quyền thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    PERMISSION_IN_USE(9105, "Quyền đang được sử dụng", HttpStatus.CONFLICT),
    PERMISSION_NAME_REQUIRED(9106, "Tên quyền không được để trống", HttpStatus.BAD_REQUEST),
    PERMISSION_CODE_REQUIRED(9107, "Mã quyền không được để trống", HttpStatus.BAD_REQUEST),
    PERMISSION_RESOURCE_REQUIRED(9108, "Tài nguyên quyền không được để trống", HttpStatus.BAD_REQUEST),
    PERMISSION_ACTION_REQUIRED(9109, "Hành động quyền không được để trống", HttpStatus.BAD_REQUEST),
    INVALID_PERMISSION_ACTION(9110, "Hành động quyền không hợp lệ", HttpStatus.BAD_REQUEST),
    PERMISSION_ACCESS_DENIED(9111, "Không có quyền truy cập", HttpStatus.FORBIDDEN),
    PERMISSION_NAME_TOO_LONG(9112, "Tên quyền quá dài", HttpStatus.BAD_REQUEST),
    PERMISSION_CODE_INVALID_FORMAT(9113, "Định dạng mã quyền không hợp lệ", HttpStatus.BAD_REQUEST),
    SYSTEM_PERMISSION_CANNOT_DELETE(9114, "Không thể xóa quyền hệ thống", HttpStatus.BAD_REQUEST),
    SYSTEM_PERMISSION_CANNOT_MODIFY(9115, "Không thể chỉnh sửa quyền hệ thống", HttpStatus.BAD_REQUEST),

    // Mã lỗi liên quan đến Role-Permission
    ROLE_PERMISSION_NOT_FOUND(9200, "Không tìm thấy phân quyền", HttpStatus.NOT_FOUND),
    ROLE_PERMISSION_EXISTS(9201, "Phân quyền đã tồn tại", HttpStatus.CONFLICT),
    ROLE_PERMISSION_ASSIGNMENT_FAILED(9202, "Gán quyền cho vai trò thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    ROLE_PERMISSION_REMOVAL_FAILED(9203, "Gỡ quyền khỏi vai trò thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_ROLE_PERMISSION_COMBINATION(9204, "Kết hợp vai trò và quyền không hợp lệ", HttpStatus.BAD_REQUEST),
    ROLE_PERMISSION_LIST_EMPTY(9205, "Danh sách phân quyền không được để trống", HttpStatus.BAD_REQUEST),
    DUPLICATE_PERMISSION_IN_ROLE(9206, "Quyền trùng lặp trong vai trò", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_PERMISSION(9207, "Không đủ quyền để thực hiện", HttpStatus.FORBIDDEN),

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
