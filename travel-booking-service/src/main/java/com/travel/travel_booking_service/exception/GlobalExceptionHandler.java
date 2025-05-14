package com.travel.travel_booking_service.exception;

import com.travel.travel_booking_service.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Xử lý lỗi validate (ví dụ: @Valid trong @RequestBody)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        List<String> errors = ex.getBindingResult()
//                .getAllErrors()
//                .stream()
//                .map(error -> {
//                    String field = (error instanceof FieldError) ? ((FieldError) error).getField() : "unknown";
//                    return field + ": " + error.getDefaultMessage();
//                })
//                .collect(Collectors.toList());
//
//        ApiResponse apiResponse = new ApiResponse();
//
//        apiResponse.setCode(errorCode.getCode());
//        apiResponse.setMessage(errorCode.getMessage());
//
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body();
//    }
//
//    // Xử lý các exception tùy chỉnh (ví dụ bạn tự định nghĩa)
//    @ExceptionHandler(MyCustomException.class)
//    public ResponseEntity<APIResponse<Void>> handleMyCustomException(MyCustomException ex) {
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(APIResponse.error(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
//    }
//
//    // Xử lý tất cả các exception còn lại
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception ex) {
//        ex.printStackTrace(); // in log nếu cần
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(ApiResponse.error("Lỗi hệ thống: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
//    }
}
