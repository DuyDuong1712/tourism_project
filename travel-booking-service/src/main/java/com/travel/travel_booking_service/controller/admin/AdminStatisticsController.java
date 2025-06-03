package com.travel.travel_booking_service.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.travel_booking_service.service.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/admin/statistics")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminStatisticsController {
    // Controller dùng để thống kê toàn bộ tour, destination, ... để hiện trên dashboard

    TourService tourService;
    CategoryService categoryService;
    DestinationService destinationService;
    TransportService transportService;
    BookingService bookingService;

    //    @GetMapping("/tours")
    //    public ResponseEntity<?> getTourStatistics() {
    //        return ResponseEntity.ok(tourService.getStatistics());
    //    }
    //
    //    @GetMapping("/categories")
    //    public ResponseEntity<?> getCategoryStatistics() {
    //        return ResponseEntity.ok(categoryService.getStatistics());
    //    }
    //
    //    @GetMapping("/destinations")
    //    public ResponseEntity<?> getDestinationStatistics() {
    //        return ResponseEntity.ok(destinationService.getStatistics());
    //    }
    //
    //    @GetMapping("/transports")
    //    public ResponseEntity<?> getTransportStatistics() {
    //        return ResponseEntity.ok(transportService.getStatistics());
    //    }

    //   @GetMapping("/booking")
    //    public ResponseEntity<?> getTransportStatistics() {
    //        return ResponseEntity.ok(transportService.getStatistics());
    //    }
    //

    //    @GetMapping
    //    public ResponseEntity<Map<String, Object>> getAllStatistics() {
    //        Map<String, Object> stats = new HashMap<>();
    //        stats.put("totalTours", tourRepository.countByIsDeleteFalse());
    //        stats.put("totalCategories", categoryRepository.countByIsDeleteFalse());
    //        stats.put("totalDestinations", destinationRepository.countByIsDeleteFalse());
    //        stats.put("totalTransports", transportRepository.countByIsDeleteFalse());
    //        return ResponseEntity.ok(stats);
    //    }
    //
    //    @GetMapping("/tours")
    //    public ResponseEntity<Map<String, Long>> getTourStats() {
    //        Map<String, Long> stats = new HashMap<>();
    //        stats.put("total", tourRepository.countByIsDeleteFalse());
    //        stats.put("active", tourRepository.countByInActiveAndIsDeleteFalse((byte) 1));
    //        stats.put("inactive", tourRepository.countByInActiveAndIsDeleteFalse((byte) 0));
    //        return ResponseEntity.ok(stats);
    //    }
    //
    //    @GetMapping("/categories")
    //    public ResponseEntity<Map<String, Long>> getCategoryStats() {
    //        Map<String, Long> stats = new HashMap<>();
    //        stats.put("total", categoryRepository.countByIsDeleteFalse());
    //        stats.put("active", categoryRepository.countByInActiveAndIsDeleteFalse((byte) 1));
    //        stats.put("inactive", categoryRepository.countByInActiveAndIsDeleteFalse((byte) 0));
    //        return ResponseEntity.ok(stats);
    //    }
    //
    //    @GetMapping("/destinations")
    //    public ResponseEntity<Map<String, Long>> getDestinationStats() {
    //        Map<String, Long> stats = new HashMap<>();
    //        stats.put("total", destinationRepository.countByIsDeleteFalse());
    //        stats.put("active", destinationRepository.countByInActiveAndIsDeleteFalse((byte) 1));
    //        stats.put("inactive", destinationRepository.countByInActiveAndIsDeleteFalse((byte) 0));
    //        return ResponseEntity.ok(stats);
    //    }
    //
    //    @GetMapping("/transports")
    //    public ResponseEntity<Map<String, Long>> getTransportStats() {
    //        Map<String, Long> stats = new HashMap<>();
    //        stats.put("total", transportRepository.countByIsDeleteFalse());
    //        stats.put("active", transportRepository.countByInActiveAndIsDeleteFalse((byte) 1));
    //        stats.put("inactive", transportRepository.countByInActiveAndIsDeleteFalse((byte) 0));
    //        return ResponseEntity.ok(stats);
    //    }

}
