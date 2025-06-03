package com.travel.travel_booking_service.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.travel_booking_service.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    //    private final PaymentRepository paymentRepository;
    //    private final BookingRepository bookingRepository;
    //    private final PaymentMapper paymentMapper;
    //
    //    @Override
    //    public PaymentDTO processPayment(PaymentRequestDTO paymentRequest) {
    //        validatePayment(paymentRequest);
    //
    //        Booking booking = bookingRepository.findById(paymentRequest.getBookingId())
    //                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", paymentRequest.getBookingId()));
    //
    //        if (!"PENDING".equals(booking.getStatus())) {
    //            throw new BusinessException(ErrorCode.INVALID_BOOKING_PAYMENT_STATUS);
    //        }
    //
    //        Payment payment = paymentMapper.toEntity(paymentRequest);
    //        payment.setBooking(booking);
    //        payment.setStatus("PENDING");
    //        payment.setCreatedAt(LocalDateTime.now());
    //
    //        // Process payment through payment gateway
    //        boolean paymentSuccess = processPaymentGateway(payment);
    //
    //        if (paymentSuccess) {
    //            payment.setStatus("SUCCESS");
    //            booking.setStatus("CONFIRMED");
    //            bookingRepository.save(booking);
    //        } else {
    //            payment.setStatus("FAILED");
    //        }
    //
    //        Payment savedPayment = paymentRepository.save(payment);
    //        return paymentMapper.toDTO(savedPayment);
    //    }
    //
    //    @Override
    //    @Transactional(readOnly = true)
    //    public PaymentDTO getPaymentById(Long id) {
    //        Payment payment = paymentRepository.findById(id)
    //                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
    //        return paymentMapper.toDTO(payment);
    //    }
    //
    //    @Override
    //    @Transactional(readOnly = true)
    //    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
    //        return paymentRepository.findAll(pageable)
    //                .map(paymentMapper::toDTO);
    //    }
    //
    //    @Override
    //    @Transactional(readOnly = true)
    //    public Page<PaymentDTO> getPaymentsByBookingId(Long bookingId, Pageable pageable) {
    //        return paymentRepository.findByBookingId(bookingId, pageable)
    //                .map(paymentMapper::toDTO);
    //    }
    //
    //    @Override
    //    @Transactional(readOnly = true)
    //    public Page<PaymentDTO> getPaymentsByUserId(Long userId, Pageable pageable) {
    //        return paymentRepository.findByUserId(userId, pageable)
    //                .map(paymentMapper::toDTO);
    //    }
    //
    //    @Override
    //    public void refundPayment(Long id) {
    //        Payment payment = paymentRepository.findById(id)
    //                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
    //
    //        if (!"SUCCESS".equals(payment.getStatus())) {
    //            throw new BusinessException(ErrorCode.INVALID_BOOKING_REFUND);
    //        }
    //
    //        // Process refund through payment gateway
    //        boolean refundSuccess = processRefundGateway(payment);
    //
    //        if (refundSuccess) {
    //            payment.setStatus("REFUNDED");
    //            payment.getBooking().setStatus("CANCELLED");
    //            paymentRepository.save(payment);
    //        } else {
    //            throw new BusinessException(ErrorCode.REFUND_FAILED);
    //        }
    //    }
    //
    //    @Override
    //    public void cancelPayment(Long id) {
    //        Payment payment = paymentRepository.findById(id)
    //                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
    //
    //        if (!"PENDING".equals(payment.getStatus())) {
    //            throw new BusinessException(ErrorCode.PAYMENT_ALREADY_PROCESSED);
    //        }
    //
    //        payment.setStatus("CANCELLED");
    //        paymentRepository.save(payment);
    //    }
    //
    //    @Override
    //    public void updatePaymentStatus(Long id, String status) {
    //        Payment payment = paymentRepository.findById(id)
    //                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
    //        payment.setStatus(status);
    //        paymentRepository.save(payment);
    //    }
    //
    //    @Override
    //    @Transactional(readOnly = true)
    //    public List<PaymentDTO> getPendingPayments() {
    //        return paymentRepository.findByStatus("PENDING").stream()
    //                .map(paymentMapper::toDTO)
    //                .collect(Collectors.toList());
    //    }
    //
    //    @Override
    //    @Transactional(readOnly = true)
    //    public List<PaymentDTO> getFailedPayments() {
    //        return paymentRepository.findByStatus("FAILED").stream()
    //                .map(paymentMapper::toDTO)
    //                .collect(Collectors.toList());
    //    }
    //
    //    @Override
    //    @Transactional(readOnly = true)
    //    public boolean isPaymentSuccessful(Long id) {
    //        Payment payment = paymentRepository.findById(id)
    //                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
    //        return "SUCCESS".equals(payment.getStatus());
    //    }
    //
    //    @Override
    //    public void validatePayment(PaymentRequestDTO paymentRequest) {
    //        if (paymentRequest.getAmount() <= 0) {
    //            throw new BusinessException(ErrorCode.INVALID_PAYMENT_AMOUNT);
    //        }
    //
    //        if (paymentRequest.getPaymentMethod() == null || paymentRequest.getPaymentMethod().isEmpty()) {
    //            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Payment method is required");
    //        }
    //    }
    //
    //    private boolean processPaymentGateway(Payment payment) {
    //        // Implement payment gateway integration
    //        return true; // Placeholder
    //    }
    //
    //    private boolean processRefundGateway(Payment payment) {
    //        // Implement refund gateway integration
    //        return true; // Placeholder
    //    }
}
