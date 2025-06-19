package com.travel.travel_booking_service.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.travel.travel_booking_service.dto.request.CancelBookingRequest;
import com.travel.travel_booking_service.dto.response.*;
import com.travel.travel_booking_service.entity.*;
import com.travel.travel_booking_service.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.travel_booking_service.dto.request.BookingRequest;
import com.travel.travel_booking_service.enums.*;
import com.travel.travel_booking_service.exception.AppException;
import com.travel.travel_booking_service.service.BookingService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingServiceImpl implements BookingService {

    TourDetailRepository tourDetailRepository;
    UserRepository userRepository;
    BookingRepository bookingRepository;
    BookingPassengerRepository bookingPassengerRepository;
    private final TourRepository tourRepository;

    @Override
    public List<BookingResponse> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();

        List<BookingResponse> bookingResponses = new ArrayList<>();

        for (Booking booking : bookings) {
            BookingResponse bookingResponse = BookingResponse.builder()
                    .id(booking.getId())
                    .tourDetailId(booking.getTourDetail().getId())
                    .customerId(booking.getCustomer().getId())
                    .fullName(booking.getFullName())
                    .email(booking.getEmail())
                    .phoneNumber(booking.getPhoneNumber())
                    .address(booking.getAddress())
                    .adultCount(booking.getAdultCount())
                    .childrenCount(booking.getChildrenCount())
                    .childCount(booking.getChildCount())
                    .babyCount(booking.getBabyCount())
                    .totalPeople(booking.getTotalPeople())
                    .singleRoomCount(booking.getSingleRoomCount())
                    .subtotal(booking.getSubtotal())
                    .discountAmount(booking.getDiscountAmount())
                    .totalAmount(booking.getTotalAmount())
                    .note(booking.getNote())
                    .bookingStatus(booking.getBookingStatus())
                    .paymentStatus(booking.getPaymentStatus())
                    .confirmedAt(booking.getConfirmedAt())
                    .cancelledAt(booking.getCancelledAt())
                    .cancellationReason(booking.getCancellationReason())
                    .cancelledBy(booking.getCancelledBy())
                    .transactionId(booking.getTransactionId())
                    .paymentDate(booking.getPaymentDate())
                    .paymentDescription(booking.getPaymentDescription())
                    .refundAmount(booking.getRefundAmount())
                    .refundPercent(booking.getRefundPercent())
                    .refundDate(booking.getRefundDate())
                    .refundStatus(booking.getRefundStatus())
                    .refundTransactionId(booking.getRefundTransactionId())
                    .refundNote(booking.getRefundNote())
                    .createdDate(booking.getCreatedDate())
                    .modifiedDate(booking.getModifiedDate())
                    .createdBy(booking.getCreatedBy())
                    .modifiedBy(booking.getModifiedBy())
                    .build();

            bookingResponses.add(bookingResponse);
        }

        return bookingResponses;
    }

    @Override
    public BookingResponse getBookingDetail(Long id) {
        Booking booking = bookingRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));

        TourDetail tourDetail = booking.getTourDetail();

        BookingResponse bookingResponse = BookingResponse.builder()
                .id(booking.getId())
                .tourId(tourDetail.getTour().getId())
                .tourDetailId(booking.getTourDetail().getId())
                .customerId(booking.getCustomer().getId())
                .fullName(booking.getFullName())
                .email(booking.getEmail())
                .phoneNumber(booking.getPhoneNumber())
                .address(booking.getAddress())
                .adultCount(booking.getAdultCount())
                .childrenCount(booking.getChildrenCount())
                .childCount(booking.getChildCount())
                .babyCount(booking.getBabyCount())
                .totalPeople(booking.getTotalPeople())
                .singleRoomCount(booking.getSingleRoomCount())
                .adultPrice(tourDetail.getAdultPrice())
                .childrenPrice(tourDetail.getChildrenPrice())
                .childPrice(tourDetail.getChildPrice())
                .babyPrice(tourDetail.getBabyPrice())
                .subtotal(booking.getSubtotal())
                .discountAmount(booking.getDiscountAmount())
                .totalAmount(booking.getTotalAmount())
                .note(booking.getNote())
                .bookingStatus(booking.getBookingStatus())
                .paymentStatus(booking.getPaymentStatus())
                .confirmedAt(booking.getConfirmedAt())
                .cancelledAt(booking.getCancelledAt())
                .cancellationReason(booking.getCancellationReason())
                .cancelledBy(booking.getCancelledBy())
                .transactionId(booking.getTransactionId())
                .paymentDate(booking.getPaymentDate())
                .paymentDescription(booking.getPaymentDescription())
                .refundAmount(booking.getRefundAmount())
                .refundPercent(booking.getRefundPercent())
                .refundDate(booking.getRefundDate())
                .refundStatus(booking.getRefundStatus())
                .refundTransactionId(booking.getRefundTransactionId())
                .refundNote(booking.getRefundNote())
                .createdDate(booking.getCreatedDate())
                .modifiedDate(booking.getModifiedDate())
                .createdBy(booking.getCreatedBy())
                .modifiedBy(booking.getModifiedBy())
                .build();

        return bookingResponse;
    }

    @Override
    public String createPendingBooking(BookingRequest bookingRequest) {

        // Validate tour detail exists
        TourDetail tourDetail = tourDetailRepository
                .findByIdForUpdate(bookingRequest.getTourDetailId())
                .orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));

        //        Kiểm tra số slot còn trống
        int remainingSlots = tourDetail.getRemainingSlots();
        int totalPassengers = bookingRequest.getAdultQuantity()
                + bookingRequest.getChildrenQuantity()
                + bookingRequest.getChildQuantity()
                + bookingRequest.getBabyQuantity();

        if (totalPassengers > remainingSlots) {
            throw new AppException(ErrorCode.NOT_ENOUGH_SLOTS);
        }

        tourDetail.setBookedSlots(tourDetail.getBookedSlots() + totalPassengers);
        tourDetailRepository.save(tourDetail);

        // Validate user exists
        User customer = userRepository
                .findById(bookingRequest.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        try {
            // Tạo booking với trạng thái PENDING
            Booking booking = Booking.builder()
                    .tourDetail(tourDetail)
                    .customer(customer)
                    .fullName(bookingRequest.getFullname())
                    .email(bookingRequest.getEmail())
                    .phoneNumber(bookingRequest.getPhone())
                    .address(bookingRequest.getAddress())
                    .adultCount(bookingRequest.getAdultQuantity())
                    .childrenCount(bookingRequest.getChildrenQuantity())
                    .childCount(bookingRequest.getChildQuantity())
                    .babyCount(bookingRequest.getBabyQuantity())
                    .singleRoomCount(bookingRequest.getSingleRoomSupplementQuantity())
                    .subtotal(bookingRequest.getOriginalPrice())
                    .discountAmount(bookingRequest.getDiscountAmount())
                    .totalAmount(bookingRequest.getFinalPrice())
                    .note(bookingRequest.getNote())
                    .bookingStatus(BookingStatus.PENDING.name())
                    .paymentStatus(PaymentStatus.PENDING.name())
                    .refundStatus(RefundStatus.NOT_APPLICABLE.name())
                    .build();

            Booking savedBooking = bookingRepository.save(booking);

            // Lưu passengers với proper validation
            if (bookingRequest.getPassengers() != null
                    && !bookingRequest.getPassengers().isEmpty()) {
                List<BookingPassenger> passengers = bookingRequest.getPassengers().stream()
                        .filter(dto -> dto.getFullname() != null
                                && !dto.getFullname().trim().isEmpty())
                        .map(dto -> {
                            LocalDate birthDate = null;
                            if (dto.getBirthDate() != null
                                    && !dto.getBirthDate().trim().isEmpty()) {
                                try {
                                    birthDate = LocalDate.parse(dto.getBirthDate());
                                } catch (Exception e) {
                                    // Log error but continue - birth date might be optional for some passenger types
                                    log.warn("Error parsing birth date: {}", dto.getBirthDate(), e);
                                }
                            }

                            return BookingPassenger.builder()
                                    .booking(savedBooking)
                                    .fullName(dto.getFullname())
                                    .dateOfBirth(birthDate)
                                    .gender(Gender.valueOf(dto.getGender().toUpperCase())
                                            .getDescription())
                                    .passengerType(birthDate != null ? calculatePassengerType(birthDate) : "ADULT")
                                    .build();
                        })
                        .collect(Collectors.toList());

                bookingPassengerRepository.saveAll(passengers);
            }

            return savedBooking.getId().toString();
        } catch (Exception e) {
            // Nếu có lỗi, rollback số slot đã đặt
            tourDetail.setBookedSlots(tourDetail.getBookedSlots() - totalPassengers);
            tourDetailRepository.save(tourDetail);
            throw e;
        }
    }

    @Override
    public void updateBookingStatus(Long id, String status, String transactionId) {
        Booking booking = bookingRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        TourDetail tourDetail = booking.getTourDetail();
        int totalPassengers = booking.getTotalPeople();

        booking.setPaymentStatus(status);

        if ("SUCCESS".equals(status)) {
            booking.setBookingStatus(BookingStatus.CONFIRMED.name());
            booking.setPaymentStatus(PaymentStatus.PAID.name());
            booking.setConfirmedAt(LocalDateTime.now());
            booking.setPaymentDate(LocalDateTime.now());
            booking.setTransactionId(transactionId);
        } else if ("FAILED".equals(status)) {
            booking.setBookingStatus(BookingStatus.CANCELLED.name());
            booking.setPaymentStatus(PaymentStatus.FAILED.name());
            booking.setCancelledAt(LocalDateTime.now());
            booking.setCancellationReason("Payment failed");

            // Rollback số slot đã đặt nếu thanh toán thất bại
            tourDetail.setBookedSlots(tourDetail.getBookedSlots() - totalPassengers);
            tourDetailRepository.save(tourDetail);
        }

        bookingRepository.save(booking);
    }

    private String calculatePassengerType(LocalDate birthDate) {
        if (birthDate == null) {
            return PassengerType.ADULT.getDescription();
        }

        LocalDate today = LocalDate.now();
        int age = today.getYear() - birthDate.getYear();

        // Điều chỉnh tuổi nếu chưa đến sinh nhật
        if (birthDate.getDayOfYear() > today.getDayOfYear()) {
            age--;
        }

        if (age >= 12) {
            return PassengerType.ADULT.getDescription();
        } else if (age >= 5) {
            return PassengerType.CHILDREN.getDescription();
        } else if (age >= 2) {
            return PassengerType.CHILD.getDescription();
        } else {
            return PassengerType.BABY.getDescription();
        }
    }

    @Override
    public CustomerViewBookingResponse getBookingById(Long id) {
        Booking booking =
                bookingRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));

        TourDetail tourDetail = booking.getTourDetail();

        CustomerViewBookingResponse customerViewBookingResponse = CustomerViewBookingResponse.builder()
                .id(id)
                .customerName(booking.getFullName())
                .customerEmail(booking.getEmail())
                .customerPhone(booking.getPhoneNumber())
                .customerAddress(booking.getAddress())
                .tourName(tourDetail.getTour().getTitle())
                .departure(tourDetail.getTour().getDeparture().getName())
                .destination(tourDetail.getTour().getDestination().getName())
                .dayStart(tourDetail.getDayStart().toString())
                .dayReturn(tourDetail.getDayReturn().toString())
                .adultQuantity(booking.getAdultCount())
                .childrenQuantity(booking.getChildrenCount())
                .childQuantity(booking.getChildCount())
                .babyQuantity(booking.getBabyCount())
                .totalPeople(booking.getTotalPeople())
                .totalAmount(booking.getTotalAmount())
                .note(booking.getNote())
                .bookingStatus(BookingStatus.valueOf(booking.getBookingStatus()).getDescription())
                .paymentDate(PaymentStatus.valueOf(booking.getPaymentStatus()).getDescription())
                .transcationId(booking.getTransactionId())
                .build();

        return customerViewBookingResponse;
    }

    @Override
    public BookingStatisticResponse getBookingStatistics() {
        List<Booking> bookings = bookingRepository.findAll();

        // Sử dụng Map để nhóm dữ liệu theo (month, year)
        Map<String, List<Booking>> groupedByMonthYear = bookings.stream()
                .filter(b -> b.getCreatedDate() != null)
                .collect(Collectors.groupingBy(b -> {
                    LocalDateTime createdAt = b.getCreatedDate();
                    return createdAt.getMonthValue() + "-" + createdAt.getYear(); // key: "6-2025"
                }));

        List<RevenueStatistic> revenueStatistics = new ArrayList<>();
        List<CancelRateStatistic> cancelRateStatistics = new ArrayList<>();
        List<BookingCountStatistic> bookingCountStatistics = new ArrayList<>();

        for (Map.Entry<String, List<Booking>> entry : groupedByMonthYear.entrySet()) {
            String[] parts = entry.getKey().split("-");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]);
            List<Booking> group = entry.getValue();

            long totalRevenue = group.stream()
                    .filter(b -> BookingStatus.CONFIRMED.name().equalsIgnoreCase(b.getBookingStatus()) || PaymentStatus.PAID.name().equalsIgnoreCase(b.getPaymentStatus()))
                    .mapToLong(Booking::getTotalAmount)
                    .sum();

            long totalBookings = group.size();

            long cancelledBookings = group.stream()
                    .filter(b -> BookingStatus.CANCELLED.name().equalsIgnoreCase(b.getBookingStatus()))
                    .count();

            double cancelRate = totalBookings > 0 ? (double) cancelledBookings * 100 / totalBookings : 0;

            revenueStatistics.add(RevenueStatistic.builder()
                    .month(month).year(year).totalRevenue(totalRevenue).build());

            cancelRateStatistics.add(CancelRateStatistic.builder()
                    .month(month).year(year).cancelRate(cancelRate).build());

            bookingCountStatistics.add(BookingCountStatistic.builder()
                    .month(month).year(year).totalBookings(totalBookings).build());
        }

        Long pendingBookings = bookings.stream()
                .filter(b -> BookingStatus.PENDING.name().equalsIgnoreCase(b.getBookingStatus()))
                .count();

        Long confirmedBookings = bookings.stream()
                .filter(b -> BookingStatus.CONFIRMED.name().equalsIgnoreCase(b.getBookingStatus()))
                .count();

        Long cancelledBookings = bookings.stream()
                .filter(b -> BookingStatus.CANCELLED.name().equalsIgnoreCase(b.getBookingStatus()))
                .count();

        return BookingStatisticResponse.builder()
                .revenueStatistics(revenueStatistics)
                .cancelRateStatistics(cancelRateStatistics)
                .bookingCountStatistics(bookingCountStatistics)
                .pendingBookings(pendingBookings)
                .confirmedBookings(confirmedBookings)
                .cancelledBookings(cancelledBookings)
                .build();
    }

    @Override
    public List<BookingResponse> getBookingsByUserId(Long id, String status) {
        List<Booking> bookings;

        if (status != null && !status.isBlank()) {
            bookings = bookingRepository.findByCustomer_IdAndBookingStatus(id, status.toUpperCase());
        } else {
            bookings = bookingRepository.findByCustomer_Id(id);
        }

        List<BookingResponse> bookingResponses = new ArrayList<>();
        if (bookings != null && !bookings.isEmpty()) {
            for (Booking booking : bookings) {
                BookingResponse bookingResponse = BookingResponse.builder()
                        .id(booking.getId())
                        .tourDetailId(booking.getTourDetail().getId())
                        .tourName(booking.getTourDetail().getTour().getTitle())
                        .customerId(booking.getCustomer().getId())
                        .fullName(booking.getFullName())
                        .email(booking.getEmail())
                        .phoneNumber(booking.getPhoneNumber())
                        .address(booking.getAddress())
                        .adultCount(booking.getAdultCount())
                        .childrenCount(booking.getChildrenCount())
                        .childCount(booking.getChildCount())
                        .babyCount(booking.getBabyCount())
                        .totalPeople(booking.getTotalPeople())
                        .singleRoomCount(booking.getSingleRoomCount())
                        .subtotal(booking.getSubtotal())
                        .discountAmount(booking.getDiscountAmount())
                        .totalAmount(booking.getTotalAmount())
                        .note(booking.getNote())
                        .bookingStatus(booking.getBookingStatus())
                        .paymentStatus(booking.getPaymentStatus())
                        .confirmedAt(booking.getConfirmedAt())
                        .cancelledAt(booking.getCancelledAt())
                        .cancellationReason(booking.getCancellationReason())
                        .cancelledBy(booking.getCancelledBy())
                        .transactionId(booking.getTransactionId())
                        .paymentDate(booking.getPaymentDate())
                        .paymentDescription(booking.getPaymentDescription())
                        .refundAmount(booking.getRefundAmount())
                        .refundPercent(booking.getRefundPercent())
                        .refundDate(booking.getRefundDate())
                        .refundStatus(booking.getRefundStatus())
                        .refundTransactionId(booking.getRefundTransactionId())
                        .refundNote(booking.getRefundNote())
                        .createdDate(booking.getCreatedDate())
                        .modifiedDate(booking.getModifiedDate())
                        .createdBy(booking.getCreatedBy())
                        .modifiedBy(booking.getModifiedBy())
                        .startDate(booking.getTourDetail().getDayStart())
                        .returnDate(booking.getTourDetail().getDayReturn())
                        .build();

                bookingResponses.add(bookingResponse);
            }
        }
        return bookingResponses;
    }



    @Override
    public void cancelBooking(Long bookingId, CancelBookingRequest cancelBookingRequest) {
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));


        // Lấy ngày hôm nay và ngày bắt đầu tour
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startDate = booking.getTourDetail().getDayStart();

        if (startDate == null) {
            throw new AppException(ErrorCode.INVALID_TOUR_START_DATE);
        }

        long daysLeft = ChronoUnit.DAYS.between(today, startDate);
        int refundPercent;
        long totalAmount = booking.getTotalAmount() != null ? booking.getTotalAmount() : 0;
        long refundAmount;

        // Tính toán chính sách hủy
        if (daysLeft < 7) {
            refundPercent = 0;
            refundAmount = 0;
        } else if (daysLeft < 15) {
            refundPercent = 60;
            refundAmount = (long) (totalAmount * 0.6);
        } else {
            refundPercent = 80;
            refundAmount = (long) (totalAmount * 0.8);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String username = authentication.getName();

        // Cập nhật thông tin hủy đơn
        booking.setBookingStatus(BookingStatus.CANCELLED.name());
        booking.setCancelledAt(LocalDateTime.now());
        booking.setCancellationReason(cancelBookingRequest.getCancelReason());
        booking.setCancelledBy(username); // Ví dụ: "USER", "ADMIN"
        booking.setRefundPercent(refundPercent);
        booking.setRefundAmount(refundAmount);
        booking.setRefundStatus(refundAmount > 0 ? "PENDING" : "NO_REFUND");
        booking.setRefundDate(refundAmount > 0 ? LocalDateTime.now() : null);
        booking.setRefundNote("Chính sách hủy tự động áp dụng theo số ngày trước ngày khởi hành.");

        bookingRepository.save(booking);
    }
}
