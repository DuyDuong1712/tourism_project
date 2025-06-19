package com.travel.travel_booking_service.service.impl;

import com.travel.travel_booking_service.dto.request.FavoriteRequest;
import com.travel.travel_booking_service.dto.response.CustomerFavoriteViewResponse;
import com.travel.travel_booking_service.dto.response.CustomerTourViewResponse;
import com.travel.travel_booking_service.dto.response.FavoriteResponse;
import com.travel.travel_booking_service.entity.Favorite;
import com.travel.travel_booking_service.entity.Tour;
import com.travel.travel_booking_service.entity.User;
import com.travel.travel_booking_service.enums.ErrorCode;
import com.travel.travel_booking_service.exception.AppException;
import com.travel.travel_booking_service.mapper.FavoriteMapper;
import com.travel.travel_booking_service.repository.FavoriteRepository;
import com.travel.travel_booking_service.repository.TourRepository;
import com.travel.travel_booking_service.repository.UserRepository;
import com.travel.travel_booking_service.service.FavoriteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavoriteServiceImpl implements FavoriteService {


    TourRepository tourRepository;
    UserRepository userRepository;
    FavoriteRepository favoriteRepository;
    FavoriteMapper favoriteMapper;

    @Override
    public FavoriteResponse addToFavorites(FavoriteRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String username = authentication.getName();
        // Tìm user để lấy userId
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Tour tour = tourRepository.findById(request.getTourId())
                .orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));

        if (favoriteRepository.existsByCustomer_IdAndTourId(user.getId(), request.getTourId())) {
            throw new AppException(ErrorCode.TOUR_ALREADY_FAVORITED);
        }

        Favorite favorite = Favorite.builder()
                .customer(user)
                .tour(tour)
                .build();
        favoriteRepository.save(favorite);

        return favoriteMapper.toFavoriteResponse(favorite);
    }

    @Override
    public void removeFromFavorites(FavoriteRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String username = authentication.getName();
        // Tìm user để lấy userId
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Tour tour = tourRepository.findById(request.getTourId())
                .orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));

        Favorite favorite = favoriteRepository
                .findByCustomer_IdAndTour_Id(user.getId(), request.getTourId()).orElseThrow(() -> new AppException(ErrorCode.FAVORITE_NOT_FOUND));

        favoriteRepository.delete(favorite);
    }

    @Override
    public List<FavoriteResponse> getFavorites() {
        // Lấy thông tin người dùng từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String username = authentication.getName();
        // Tìm user để lấy userId
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Lấy danh sách tour yêu thích
        List<Favorite> favorites = favoriteRepository.findByCustomer_IdWithTour(user.getId());
        List<FavoriteResponse> favoriteResponses = new ArrayList<>();
        for (Favorite favorite : favorites) {
            FavoriteResponse favoriteResponse = FavoriteResponse.builder().customerId(favorite.getCustomer().getId())
                    .tourId(favorite.getTour().getId())
                    .build();

            favoriteResponses.add(favoriteResponse);
        }

        return favoriteResponses;
    }

    @Override
    public List<CustomerFavoriteViewResponse> getFavoritesByUserId(Long userId) {
        List<Favorite> favorites = favoriteRepository.findByCustomer_Id(userId);

        List<CustomerFavoriteViewResponse> tourResponses = new ArrayList<>();
        for (Favorite favorite : favorites) {
            CustomerFavoriteViewResponse favoriteResponse = CustomerFavoriteViewResponse.builder()
                    .id(favorite.getId())
                    .tourId(favorite.getTour().getId())
                    .title(favorite.getTour().getTitle())
                    .category(favorite.getTour().getCategory().getName())
                    .departure(favorite.getTour().getDeparture().getName())
                    .destination(favorite.getTour().getDestination().getName())
                    .price(favorite.getTour().getTourDetails().get(0).getAdultPrice())
                    .tourImages(favorite.getTour().getTourImages().get(0).getCloudinaryUrl())
                    .build();

            tourResponses.add(favoriteResponse);
        }

        return tourResponses;
    }

    @Override
    public boolean isTourFavoritedByUser(Long userId, Long tourId) {
        return false;
    }
}
