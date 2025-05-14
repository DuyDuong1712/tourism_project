package com.travel.travel_booking_service.service;

import com.travel.travel_booking_service.dto.PaymentDTO;
import com.travel.travel_booking_service.dto.PaymentRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {
    PaymentDTO processPayment(PaymentRequestDTO paymentRequest);
    PaymentDTO getPaymentById(Long id);
    Page<PaymentDTO> getAllPayments(Pageable pageable);
    Page<PaymentDTO> getPaymentsByBookingId(Long bookingId, Pageable pageable);
    Page<PaymentDTO> getPaymentsByUserId(Long userId, Pageable pageable);
    void refundPayment(Long id);
    void cancelPayment(Long id);
    void updatePaymentStatus(Long id, String status);
    List<PaymentDTO> getPendingPayments();
    List<PaymentDTO> getFailedPayments();
    boolean isPaymentSuccessful(Long id);
    void validatePayment(PaymentRequestDTO paymentRequest);
} 