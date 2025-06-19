package com.travel.travel_booking_service.controller;

import com.cloudinary.Api;
import com.travel.travel_booking_service.dto.request.FavoriteRequest;
import com.travel.travel_booking_service.dto.response.*;
import com.travel.travel_booking_service.service.FavoriteService;
import com.travel.travel_booking_service.service.TourService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavoriteController {

    FavoriteService favoriteService;


    @GetMapping
    public ResponseEntity<ApiResponse<List<FavoriteResponse>>> getFavorites() {
        //TODO: Trả về danh sách tour yêu thích của user hiện tại
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<FavoriteResponse>>builder()
                        .data(favoriteService.getFavorites())
                        .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FavoriteResponse>> addFavorite(@RequestBody FavoriteRequest request) {
        // Thêm tour vào danh sách yêu thích
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<FavoriteResponse>builder()
                        .data(favoriteService.addToFavorites(request))
                        .build());
    }

    @DeleteMapping()
    public ResponseEntity<Void> removeFavorite(@RequestBody FavoriteRequest request) {
        // Xóa tour khỏi danh sách yêu thích
        favoriteService.removeFromFavorites(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<List<CustomerFavoriteViewResponse>>> getFavoritesByUserId(@PathVariable Long userId) {
        // Trả về danh sách tour yêu thích của user theo userId
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<CustomerFavoriteViewResponse>>builder()
                        .data(favoriteService.getFavoritesByUserId(userId))
                        .build());
    }
}
