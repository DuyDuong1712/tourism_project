package com.travel.travel_booking_service.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookinngStatusRequest {
    private String bookingId;
    private String status; // Ví dụ: "SUCCESS", "FAILED"
    private String transactionNo; // Mã giao dịch từ VNPay
    private int amount; // Số tiền thanh toán (VND)
    private String paymentMethod; // Ví dụ: "VNPAY"
}
