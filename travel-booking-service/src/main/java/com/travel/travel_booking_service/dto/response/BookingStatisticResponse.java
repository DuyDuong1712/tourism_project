package com.travel.travel_booking_service.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingStatisticResponse {
    private List<RevenueStatistic> revenueStatistics;
    private List<CancelRateStatistic> cancelRateStatistics;
    private List<BookingCountStatistic> bookingCountStatistics;
    private Long pendingBookings;
    private Long confirmedBookings;
    private Long cancelledBookings;
}
