package com.travel.travel_booking_service.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.travel.travel_booking_service.dto.request.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.travel_booking_service.dto.response.CloudinaryUploadResponse;
import com.travel.travel_booking_service.dto.response.TourResponse;
import com.travel.travel_booking_service.entity.*;
import com.travel.travel_booking_service.enums.ErrorCode;
import com.travel.travel_booking_service.enums.TourDetailStatus;
import com.travel.travel_booking_service.exception.AppException;
import com.travel.travel_booking_service.mapper.TourMapper;
import com.travel.travel_booking_service.repository.*;
import com.travel.travel_booking_service.service.TourService;
import com.travel.travel_booking_service.service.UploadImageFile;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TourServiceImpl implements TourService {


    TourRepository tourRepository;
    CategoryRepository categoryRepository;
    DepartureRepository departureRepository;
    DestinationRepository destinationRepository;
    TransportRepository transportRepository;
    TourInformationRepository tourInformationRepository;
    TourSchedulesRepository tourSchedulesRepository;
    TourDetailRepository tourDetailRepository;
    UploadImageFile uploadImageFile;
    TourImageRepository tourImageRepository;
    TourMapper tourMapper;
    ObjectMapper objectMapper;

    @Override
    @Transactional
    public TourResponse createTour(
            String title,
            Boolean isFeatured,
            Long categoryId,
            Long destinationId,
            Long departureId,
            Long transportationId,
            String description,
            String informationJson,
            String scheduleJson,
            String tourDetailJson,
            List<MultipartFile> imageFiles)
            throws JsonProcessingException {

        // Parse JSON thành các đối tượng Java
        TourInfomationRequest information = objectMapper.readValue(informationJson, TourInfomationRequest.class);
        List<TourScheduleRequest> schedules =
                objectMapper.readValue(scheduleJson, new TypeReference<List<TourScheduleRequest>>() {});
        List<TourDetailRequest> tourDetails =
                objectMapper.readValue(tourDetailJson, new TypeReference<List<TourDetailRequest>>() {});

        // 1. Validate và lấy các entity liên quan
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Departure departure = departureRepository
                .findById(departureId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTURE_NOT_FOUND));

        Destination destination = destinationRepository
                .findById(destinationId)
                .orElseThrow(() -> new AppException(ErrorCode.DESTINATION_NOT_FOUND));

        Transport transportation = transportRepository
                .findById(transportationId)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSPORT_NOT_FOUND));

        // 2. Tạo đối tượng Tour
        Tour tour = Tour.builder()
                .title(title)
                .isFeatured(isFeatured)
                .category(category)
                .departure(departure)
                .destination(destination)
                .transport(transportation)
                .description(description)
                .build();

        tourRepository.save(tour); // lưu trước để có ID

        // 3. Lưu thông tin chi tiết (information)
        TourInformation tourInfo = TourInformation.builder()
                .tour(tour)
                .attractions(information.getAttractions())
                .cuisine(information.getCuisine())
                .idealTime(information.getIdealTime())
                .promotion(information.getPromotion())
                .suitableObject(information.getSuitableObject())
                .vehicle(information.getVehicle())
                .build();
        tourInformationRepository.save(tourInfo);

        // 4. Lưu lịch trình (schedules)
        List<TourSchedule> scheduleList = new ArrayList<>();
        for (TourScheduleRequest scheduleRequest : schedules) {
            TourSchedule schedule = TourSchedule.builder()
                    .day(scheduleRequest.getDay())
                    .title(scheduleRequest.getTitle())
                    .information(scheduleRequest.getInformation())
                    .tour(tour)
                    .build();
            scheduleList.add(schedule);
        }
        tourSchedulesRepository.saveAll(scheduleList);

        // 5. Lưu chi tiết tour (giá và ngày khởi hành)
        List<TourDetail> details = new ArrayList<>();
        for (TourDetailRequest detailRequest : tourDetails) {
            TourDetail detail = TourDetail.builder()
                    .tour(tour)
                    .adultPrice(detailRequest.getAdultPrice())
                    .childrenPrice((detailRequest.getChildrenPrice() != null) ? detailRequest.getChildrenPrice() : 0)
                    .childPrice((detailRequest.getChildPrice() != null) ? detailRequest.getChildPrice() : 0)
                    .babyPrice((detailRequest.getBabyPrice() != null) ? detailRequest.getBabyPrice() : 0)
                    .stock(detailRequest.getStock())
                    .dayStart(detailRequest.getDayStart())
                    .dayReturn(detailRequest.getDayReturn())
                    .status(TourDetailStatus.SCHEDULED)
                    .singleRoomSupplementPrice(
                            (detailRequest.getSingleRoomSupplementPrice() != null)
                                    ? detailRequest.getSingleRoomSupplementPrice()
                                    : 0)
                    .bookedSlots(0)
                    .build();
            details.add(detail);
        }
        tourDetailRepository.saveAll(details);

        // 6. Upload ảnh
        List<TourImage> tourImages = new ArrayList<>();
        if (imageFiles != null && !imageFiles.isEmpty()) {
            for (MultipartFile file : imageFiles) {
                if (file != null && !file.isEmpty()) {
                    try {
                        CloudinaryUploadResponse cloudinaryUploadResponse = uploadImageFile.upLoadImage(file);
                        TourImage tourImage = TourImage.builder()
                                .tour(tour)
                                .cloudinaryPublicId(cloudinaryUploadResponse.getPublicId())
                                .cloudinaryUrl(cloudinaryUploadResponse.getUrl())
                                .altText("Hình ảnh về " + tour.getTitle())
                                .build();
                        tourImages.add(tourImage);
                        tourImageRepository.save(tourImage);
                    } catch (IOException e) {
                        throw new AppException(ErrorCode.IMAGE_UPLOAD_FAILED);
                    }
                }
            }
        }

        tour.setInActive(true);
        tour.setTourInformation(tourInfo);
        tour.setTourSchedules(scheduleList);
        tour.setTourImages(tourImages);
        tour.setTourDetails(details);

        // Refresh tour entity để đảm bảo có data mới nhất
        tourRepository.saveAndFlush(tourRepository.save(tour));

        TourResponse tourResponse = new TourResponse();

        // 7. Trả về response
        return tourResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourResponse> getAllToursWithDetails() {
        List<Tour> tourProjections = tourRepository.findAll();
        List<TourResponse> tourResponses = new ArrayList<>();

        for (Tour tour : tourProjections) {
            // Lấy trước danh sách ảnh tour
            List<String> tourImageUrls = tourImageRepository
                    .getByTour_Id(tour.getId())
                    .stream()
                    .map(TourImage::getCloudinaryUrl)
                    .collect(Collectors.toList());

            // Lấy danh sách các chi tiết tour (TourDetail)
            List<TourDetail> tourDetails = tourDetailRepository.findByTour_Id(tour.getId());

            for (TourDetail tourDetail : tourDetails) {
                TourResponse tourResponse = new TourResponse();

                // Gán thông tin từ tour
                tourResponse.setId(tour.getId());
                tourResponse.setTitle(tour.getTitle());
                tourResponse.setDescription(tour.getDescription());
                tourResponse.setInActive(tour.getInActive());
                tourResponse.setIsFeatured(tour.getIsFeatured());
                tourResponse.setTourImages(tourImageUrls);

                // Gán thông tin từ tourDetail
                tourResponse.setTourDetailsId(tourDetail.getId());
                tourResponse.setStatus(tourDetail.getStatus().name());
                tourResponse.setAdultPrice(tourDetail.getAdultPrice());
                tourResponse.setChildrenPrice(tourDetail.getChildrenPrice());
                tourResponse.setChildPrice(tourDetail.getChildPrice());
                tourResponse.setBabyPrice(tourDetail.getBabyPrice());
                tourResponse.setSlots(tourDetail.getStock());
                tourResponse.setBookedSlots(tourDetail.getBookedSlots());
                tourResponse.setRemainingSlots(tourDetail.getRemainingSlots());
                tourResponse.setDayStart(tourDetail.getDayStart());
                tourResponse.setDayReturn(tourDetail.getDayReturn());


                // Gán các thực thể liên quan
                tourResponse.setCategory(tour.getCategory().getName());
                tourResponse.setDeparture(tour.getDeparture().getName());
                tourResponse.setDestination(tour.getDestination().getName());
                tourResponse.setTransportation(tour.getTransport().getName());

                tourResponses.add(tourResponse);
            }
        }
        return tourResponses;
    }

    @Override
    public List<TourResponse> getAllTours() {
        return List.of();
    }

    @Override
    public TourResponse getTourById(Long id) {
        return null;
    }

    @Override
    public TourResponse updateTour(Long id, TourRequest request) {
        return null;
    }

    @Override
    public void deleteTour(Long id) {
        Tour tour =
                tourRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));


        tourRepository.delete(tour);
    }

    @Override
    public List<TourResponse> searchTours(String keyword) {
        return List.of();
    }

    @Override
    public List<TourResponse> getToursByCategory(Long categoryId) {
        return List.of();
    }

    @Override
    public List<TourResponse> getToursByDestination(Long destinationId) {
        return List.of();
    }

    @Override
    public void changeTourStatus(Long id, StatusRequest statusRequest) {
        Tour tour =
                tourRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));
        tour.setInActive(statusRequest.getInActive());
    }

    @Override
    public void changeTourFeatured(Long id, FeaturedRequest featuredRequest) {
        Tour tour =
                tourRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));
        tour.setIsFeatured(featuredRequest.getIsFeatured());
    }

    @Override
    public void changeToursDetailsStatus(Long TourDetailId, ToursDetailsStatusRequest request) {
        TourDetail tourDetail = tourDetailRepository.findById(TourDetailId).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));
        tourDetail.setStatus(TourDetailStatus.valueOf(request.getStatus()));
        tourDetailRepository.save(tourDetail);
    }
}
