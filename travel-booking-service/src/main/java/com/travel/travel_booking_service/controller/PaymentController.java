package com.travel.travel_booking_service.controller;

import java.io.UnsupportedEncodingException;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.dto.request.PaymentRequest;
import com.travel.travel_booking_service.dto.response.PaymentResponse;
import com.travel.travel_booking_service.service.BookingService;
import com.travel.travel_booking_service.service.VNPAYService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {

    BookingService bookingService;
    VNPAYService vnpayService;

    @PostMapping("/create_payment")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentRequest, HttpServletRequest req)
            throws UnsupportedEncodingException {
        try {
            String paymentUrl = vnpayService.createPaymentUrl(paymentRequest, req);

            return ResponseEntity.ok(PaymentResponse.builder()
                    .message("Payment URL created successfully")
                    .status(HttpStatus.OK.name())
                    .url(paymentUrl)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(PaymentResponse.builder()
                            .message("Error creating payment URL: " + e.getMessage())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                            .build());
        }
    }

    //    @GetMapping("/payment-info")
    //    public ResponseEntity<TransactionStatus> handlePaymentCallback(HttpServletRequest request) {
    //        try {
    //            // Lấy các tham số từ VNPay
    //            Map<String, String> fields = new HashMap<>();
    //            for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
    //                String fieldName = params.nextElement();
    //                String fieldValue = request.getParameter(fieldName);
    //                if ((fieldValue != null) && (fieldValue.length() > 0)) {
    //                    fields.put(fieldName, fieldValue);
    //                }
    //            }
    //
    //            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
    //            fields.remove("vnp_SecureHashType");
    //            fields.remove("vnp_SecureHash");
    //
    //            // Kiểm tra chữ ký bảo mật
    //            String signValue = VNPAYConfig.hashAllFields(fields);
    //            TransactionStatus transactionStatus = new TransactionStatus();
    //
    //            if (signValue.equals(vnp_SecureHash)) {
    //                String responseCode = request.getParameter("vnp_ResponseCode");
    //                String orderInfo = request.getParameter("vnp_OrderInfo");
    //                String amount = request.getParameter("vnp_Amount");
    //                String bankCode = request.getParameter("vnp_BankCode");
    //                String payDate = request.getParameter("vnp_PayDate");
    //                String txnRef = request.getParameter("vnp_TxnRef");
    //
    //                if ("00".equals(responseCode)) {
    //                    // Thanh toán thành công
    //                    try {
    //                        // Lấy bookingId từ orderInfo (định dạng "Thanh toan don hang:bookingId")
    //                        String bookingId = orderInfo.replace("Thanh toan don hang:", "").trim();
    //                        bookingService.updateBookingStatus(Long.parseLong(bookingId), "SUCCESS");
    //
    //                        transactionStatus.setStatus("success");
    //                        transactionStatus.setMessage("Payment successful");
    //                        transactionStatus.setData("Booking ID: " + bookingId + ", Amount: " +
    // (Long.parseLong(amount) / 100) + " VND, Transaction: " + txnRef);
    //                    } catch (NumberFormatException e) {
    //                        transactionStatus.setStatus("failed");
    //                        transactionStatus.setMessage("Invalid booking ID format");
    //                        transactionStatus.setData("");
    //                    }
    //                } else {
    //                    // Thanh toán thất bại
    //                    try {
    //                        String bookingId = orderInfo.replace("Thanh toan don hang:", "").trim();
    //                        bookingService.updateBookingStatus(Long.parseLong(bookingId), "FAILED");
    //                    } catch (Exception e) {
    //                        // Log error but continue
    //                        System.err.println("Error updating failed booking status: " + e.getMessage());
    //                    }
    //
    //                    transactionStatus.setStatus("failed");
    //                    transactionStatus.setMessage("Payment failed with code: " + responseCode);
    //                    transactionStatus.setData("Transaction Ref: " + txnRef);
    //                }
    //            } else {
    //                // Chữ ký không hợp lệ
    //                transactionStatus.setStatus("failed");
    //                transactionStatus.setMessage("Invalid signature");
    //                transactionStatus.setData("");
    //            }
    //
    //            return ResponseEntity.ok(transactionStatus);
    //        } catch (Exception e) {
    //            TransactionStatus errorStatus = new TransactionStatus();
    //            errorStatus.setStatus("error");
    //            errorStatus.setMessage("Error processing payment callback: " + e.getMessage());
    //            errorStatus.setData("");
    //            return ResponseEntity.ok(errorStatus);
    //        }
    //    }
}
