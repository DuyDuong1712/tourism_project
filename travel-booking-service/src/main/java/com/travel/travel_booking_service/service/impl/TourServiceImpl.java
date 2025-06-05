package com.travel.travel_booking_service.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.hibernate.proxy.HibernateProxy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.travel_booking_service.dto.request.*;
import com.travel.travel_booking_service.dto.response.*;
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
                    .discountPercent(detailRequest.getDiscount())
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
    public List<TourResponse> getAllTours() {
        List<Tour> tours = tourRepository.findAll();
        List<TourResponse> tourResponses = new ArrayList<>();
        for (Tour tour : tours) {
            // Lấy danh sách ảnh tour
            List<String> tourImageUrls = new ArrayList<>();
            for (TourImage tourImage : tourImageRepository.getByTour_Id(tour.getId())) {
                String cloudinaryUrl = tourImage.getCloudinaryUrl();
                tourImageUrls.add(cloudinaryUrl);
            }

            TourResponse tourResponse = tourMapper.toTourResponse(tour);
            tourResponse.setTourImages(tourImageUrls);
            tourResponse.setCategory(tour.getCategory().getName());
            tourResponse.setDeparture(tour.getDeparture().getName());
            tourResponse.setDestination(tour.getDestination().getName());
            tourResponse.setTransportation(tour.getTransport().getName());

            tourResponses.add(tourResponse);
        }
        return tourResponses;
    }

    @Override
    public List<TourResponse> getAllToursFiltered(
            Long destinationId,
            Long departureId,
            Long transportationId,
            Long categoryId,
            Boolean inActive,
            Boolean isFeatured,
            String title) {
        List<Tour> tours = tourRepository.findAllFiltered(
                destinationId, departureId, transportationId, categoryId, inActive, isFeatured, title);

        List<TourResponse> tourResponses = new ArrayList<>();
        for (Tour tour : tours) {
            // Lấy danh sách ảnh tour
            List<String> tourImageUrls = new ArrayList<>();
            for (TourImage tourImage : tourImageRepository.getByTour_Id(tour.getId())) {
                String cloudinaryUrl = tourImage.getCloudinaryUrl();
                tourImageUrls.add(cloudinaryUrl);
            }

            TourResponse tourResponse = tourMapper.toTourResponse(tour);
            tourResponse.setTourImages(tourImageUrls);
            tourResponse.setCategory(tour.getCategory().getName());
            tourResponse.setDeparture(tour.getDeparture().getName());
            tourResponse.setDestination(tour.getDestination().getName());
            tourResponse.setTransportation(tour.getTransport().getName());

            tourResponses.add(tourResponse);
        }
        return tourResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourDetailResponse> getAllToursWithDetails() {
        List<Tour> tourProjections = tourRepository.findAll();
        List<TourDetailResponse> tourDetailResponses = new ArrayList<>();

        for (Tour tour : tourProjections) {
            // Lấy trước danh sách ảnh tour
            List<String> tourImageUrls = tourImageRepository.getByTour_Id(tour.getId()).stream()
                    .map(TourImage::getCloudinaryUrl)
                    .collect(Collectors.toList());

            // Lấy danh sách các chi tiết tour (TourDetail)
            List<TourDetail> tourDetails = tourDetailRepository.findByTour_Id(tour.getId());

            for (TourDetail tourDetail : tourDetails) {
                TourDetailResponse tourDetailResponse = new TourDetailResponse();

                // Gán thông tin từ tour
                tourDetailResponse.setId(tour.getId());
                tourDetailResponse.setTitle(tour.getTitle());
                tourDetailResponse.setDescription(tour.getDescription());
                tourDetailResponse.setInActive(tour.getInActive());
                tourDetailResponse.setIsFeatured(tour.getIsFeatured());
                tourDetailResponse.setTourImages(tourImageUrls);

                // Gán thông tin từ tourDetail
                tourDetailResponse.setTourDetailId(tourDetail.getId());
                tourDetailResponse.setStatus(tourDetail.getStatus().name());
                tourDetailResponse.setAdultPrice(tourDetail.getAdultPrice());
                tourDetailResponse.setChildrenPrice(tourDetail.getChildrenPrice());
                tourDetailResponse.setChildPrice(tourDetail.getChildPrice());
                tourDetailResponse.setBabyPrice(tourDetail.getBabyPrice());
                tourDetailResponse.setSlots(tourDetail.getStock());
                tourDetailResponse.setBookedSlots(tourDetail.getBookedSlots());
                tourDetailResponse.setRemainingSlots(tourDetail.getRemainingSlots());
                tourDetailResponse.setDayStart(tourDetail.getDayStart());
                tourDetailResponse.setDayReturn(tourDetail.getDayReturn());

                // Gán các thực thể liên quan
                tourDetailResponse.setCategory(tour.getCategory().getName());
                tourDetailResponse.setDeparture(tour.getDeparture().getName());
                tourDetailResponse.setDestination(tour.getDestination().getName());
                tourDetailResponse.setTransportation(tour.getTransport().getName());

                tourDetailResponses.add(tourDetailResponse);
            }
        }
        return tourDetailResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourDetailResponse> getTourDetailsByTourId(Long id) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));

        List<TourDetailResponse> tourDetailResponses = new ArrayList<>();

        // Lấy trước danh sách ảnh tour
        List<String> tourImageUrls = tourImageRepository.getByTour_Id(id).stream()
                .map(TourImage::getCloudinaryUrl)
                .collect(Collectors.toList());

        // Lấy danh sách các chi tiết tour (TourDetail)
        List<TourDetail> tourDetails = tourDetailRepository.findByTour_Id(id);

        for (TourDetail tourDetail : tourDetails) {
            TourDetailResponse tourDetailResponse = new TourDetailResponse();

            // Gán thông tin từ tour
            tourDetailResponse.setId(tour.getId());
            tourDetailResponse.setTitle(tour.getTitle());
            tourDetailResponse.setDescription(tour.getDescription());
            tourDetailResponse.setInActive(tour.getInActive());
            tourDetailResponse.setIsFeatured(tour.getIsFeatured());
            tourDetailResponse.setTourImages(tourImageUrls);

            // Gán thông tin từ tourDetail
            tourDetailResponse.setTourDetailId(tourDetail.getId());
            tourDetailResponse.setStatus(tourDetail.getStatus().name());
            tourDetailResponse.setAdultPrice(tourDetail.getAdultPrice());
            tourDetailResponse.setChildrenPrice(tourDetail.getChildrenPrice());
            tourDetailResponse.setChildPrice(tourDetail.getChildPrice());
            tourDetailResponse.setBabyPrice(tourDetail.getBabyPrice());
            tourDetailResponse.setSlots(tourDetail.getStock());
            tourDetailResponse.setBookedSlots(tourDetail.getBookedSlots());
            tourDetailResponse.setRemainingSlots(tourDetail.getRemainingSlots());
            tourDetailResponse.setDayStart(tourDetail.getDayStart());
            tourDetailResponse.setDayReturn(tourDetail.getDayReturn());

            // Gán các thực thể liên quan
            tourDetailResponse.setCategory(tour.getCategory().getName());
            tourDetailResponse.setDeparture(tour.getDeparture().getName());
            tourDetailResponse.setDestination(tour.getDestination().getName());
            tourDetailResponse.setTransportation(tour.getTransport().getName());

            tourDetailResponses.add(tourDetailResponse);
        }

        return tourDetailResponses;
    }

    @Override
    public TourEditResponse getTourById(Long id) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));

        TourInformation tourInformation = tour.getTourInformation();
        List<TourSchedule> tourSchedules = tour.getTourSchedules();
        List<TourDetail> tourDetails = tour.getTourDetails();
        List<TourImage> tourImages = tour.getTourImages();

        TourEditResponse tourEditResponse = new TourEditResponse();

        // Lấy thông tin cơ bản của tour
        tourEditResponse.setTitle(tour.getTitle());
        tourEditResponse.setCatagoryId(tour.getCategory().getId());
        tourEditResponse.setDepartureId(tour.getDeparture().getId());
        tourEditResponse.setDestinationId(tour.getDestination().getId());
        tourEditResponse.setTransportId(tour.getTransport().getId());
        tourEditResponse.setDescription(tour.getDescription());

        // Lấy thong tin trong tour-info
        TourInformationResponse tourInformationResponse = new TourInformationResponse();
        tourInformationResponse.setId(tourInformation.getId());
        tourInformationResponse.setTourId(tourInformation.getId());
        tourInformationResponse.setAttractions(tourInformation.getAttractions());
        tourInformationResponse.setCuisine(tourInformation.getCuisine());
        tourInformationResponse.setPromotion(tourInformation.getPromotion());
        tourInformationResponse.setVehicle(tourInformation.getVehicle());
        tourInformationResponse.setSuitableObject(tourInformation.getSuitableObject());
        tourInformationResponse.setIdealTime(tourInformation.getIdealTime());

        tourEditResponse.setTourInformation(tourInformationResponse);

        // Lay thong tin trong schedule
        List<TourScheduleResponse> tourScheduleResponses = new ArrayList<>();
        for (TourSchedule tourSchedule : tour.getTourSchedules()) {
            TourScheduleResponse tourScheduleResponse = new TourScheduleResponse();
            tourScheduleResponse.setId(tourSchedule.getId());
            tourScheduleResponse.setTourId(tourSchedule.getTour().getId());
            tourScheduleResponse.setDay(tourSchedule.getDay());
            tourScheduleResponse.setTitle(tourSchedule.getTitle());
            tourScheduleResponse.setInformation(tourSchedule.getInformation());

            tourScheduleResponses.add(tourScheduleResponse);
        }

        tourEditResponse.setTourSchedules(tourScheduleResponses);

        // Lay thong tin trong tour-details
        List<TourDetailEdit> tourDetailEdits = new ArrayList<>();
        for (TourDetail tourDetail : tourDetails) {
            TourDetailEdit tourDetailEdit = new TourDetailEdit();
            tourDetailEdit.setId(tourDetail.getId());
            tourDetailEdit.setTourId(tourDetail.getTour().getId());
            tourDetailEdit.setAdultPrice(tourDetail.getAdultPrice());
            tourDetailEdit.setChildrenPrice(tourDetail.getChildrenPrice());
            tourDetailEdit.setChildPrice(tourDetail.getChildPrice());
            tourDetailEdit.setBabyPrice(tourDetail.getBabyPrice());
            tourDetailEdit.setDayStart(tourDetail.getDayStart());
            tourDetailEdit.setDayReturn(tourDetail.getDayReturn());
            tourDetailEdit.setDiscount(tourDetail.getDiscountPercent());
            tourDetailEdit.setStock(tourDetail.getStock());
            tourDetailEdit.setSingleRoomSupplementPrice(tourDetail.getSingleRoomSupplementPrice());

            tourDetailEdits.add(tourDetailEdit);
        }

        tourEditResponse.setTourDetails(tourDetailEdits);

        // Duyet hinh anh
        List<TourImageResponse> tourImageResponses = new ArrayList<>();
        for (TourImage tourImage : tour.getTourImages()) {
            TourImageResponse tourImageResponse = TourImageResponse.builder()
                    .id(tourImage.getId())
                    .TourId(tourImage.getTour().getId())
                    .ImageUrl(tourImage.getCloudinaryUrl())
                    .build();
            tourImageResponses.add(tourImageResponse);
        }

        tourEditResponse.setImages(tourImageResponses);

        return tourEditResponse;
    }

    @Override
    @Transactional
    public TourResponse updateTour(
            Long id,
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

        // 1. Tìm tour theo ID
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));

        // 2. Parse JSON
        TourInfomationRequest information = objectMapper.readValue(informationJson, TourInfomationRequest.class);
        List<TourScheduleRequest> schedules =
                objectMapper.readValue(scheduleJson, new TypeReference<List<TourScheduleRequest>>() {});
        List<TourDetailRequest> tourDetails =
                objectMapper.readValue(tourDetailJson, new TypeReference<List<TourDetailRequest>>() {});

        // Kiểm tra JSON
        if (information == null || schedules == null || tourDetails == null || schedules.isEmpty() || tourDetails.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_JSON_DATA);
        }

        // 3. Lấy các entity liên quan
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        if (category instanceof HibernateProxy) {
            category = (Category) ((HibernateProxy) category).getHibernateLazyInitializer().getImplementation();
        }

        Departure departure = departureRepository
                .findById(departureId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTURE_NOT_FOUND));
        if (departure instanceof HibernateProxy) {
            departure = (Departure) ((HibernateProxy) departure).getHibernateLazyInitializer().getImplementation();
        }

        Destination destination = destinationRepository
                .findById(destinationId)
                .orElseThrow(() -> new AppException(ErrorCode.DESTINATION_NOT_FOUND));
        if (destination instanceof HibernateProxy) {
            destination = (Destination) ((HibernateProxy) destination).getHibernateLazyInitializer().getImplementation();
        }

        Transport transportation = transportRepository
                .findById(transportationId)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSPORT_NOT_FOUND));
        if (transportation instanceof HibernateProxy) {
            transportation = (Transport) ((HibernateProxy) transportation)
                    .getHibernateLazyInitializer()
                    .getImplementation();
        }

        // 4. Cập nhật thông tin cơ bản
        tour.setTitle(title);
        tour.setIsFeatured(isFeatured);
        tour.setCategory(category);
        tour.setDeparture(departure);
        tour.setDestination(destination);
        tour.setTransport(transportation);
        tour.setDescription(description);

        // 5. Cập nhật TourInformation
        TourInformation tourInfo = tour.getTourInformation();
        if (tourInfo == null) {
            tourInfo = TourInformation.builder().tour(tour).build();
        }
        tourInfo.setAttractions(information.getAttractions());
        tourInfo.setCuisine(information.getCuisine());
        tourInfo.setIdealTime(information.getIdealTime());
        tourInfo.setPromotion(information.getPromotion());
        tourInfo.setSuitableObject(information.getSuitableObject());
        tourInfo.setVehicle(information.getVehicle());
        tour.setTourInformation(tourInfo);

        // 6. Cập nhật TourSchedule
        tour.getTourSchedules().clear(); // Xóa các schedules cũ
        for (TourScheduleRequest scheduleRequest : schedules) {
            if (Objects.isNull(scheduleRequest.getDay()) || scheduleRequest.getTitle() == null) {
                throw new AppException(ErrorCode.INVALID_SCHEDULE_DATA);
            }
            TourSchedule schedule = TourSchedule.builder()
                    .day(scheduleRequest.getDay())
                    .title(scheduleRequest.getTitle())
                    .information(scheduleRequest.getInformation())
                    .tour(tour)
                    .build();
            tour.getTourSchedules().add(schedule);
        }

        // 7. Cập nhật TourDetail
        tour.getTourDetails().clear(); // Xóa các details cũ
        for (TourDetailRequest detailRequest : tourDetails) {
            if (detailRequest.getDayStart() == null || detailRequest.getDayReturn() == null ||
                    detailRequest.getStock() == null || detailRequest.getAdultPrice() == null) {
                throw new AppException(ErrorCode.INVALID_TOUR_DETAIL_DATA);
            }
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
                    .build();
            if (detail.getBookedSlots() == null) {
                detail.setBookedSlots(0);
            }
            tour.getTourDetails().add(detail);
        }

        // 8. Cập nhật ảnh
        if (imageFiles != null && !imageFiles.isEmpty()) {
            tour.getTourImages().clear(); // Chỉ xóa ảnh cũ nếu có ảnh mới
            for (MultipartFile file : imageFiles) {
                if (file != null && !file.isEmpty()) {
                    try {
                        CloudinaryUploadResponse cloudinaryUploadResponse = uploadImageFile.upLoadImage(file);
                        if (cloudinaryUploadResponse.getPublicId() == null || cloudinaryUploadResponse.getUrl() == null) {
                            throw new AppException(ErrorCode.IMAGE_UPLOAD_FAILED);
                        }
                        TourImage tourImage = TourImage.builder()
                                .tour(tour)
                                .cloudinaryPublicId(cloudinaryUploadResponse.getPublicId())
                                .cloudinaryUrl(cloudinaryUploadResponse.getUrl())
                                .altText("Hình ảnh về " + tour.getTitle())
                                .build();
                        tour.getTourImages().add(tourImage);
                    } catch (IOException e) {
                        log.error("Tải ảnh thất bại: ", e);
                        throw new AppException(ErrorCode.IMAGE_UPLOAD_FAILED);
                    }
                }
            }
        } // Nếu imageFiles null hoặc rỗng, giữ nguyên tourImages hiện tại

        // 9. Lưu tour
        try {
            tourRepository.saveAndFlush(tour);
        } catch (DataIntegrityViolationException e) {
            log.error("Lỗi lưu tour do vi phạm ràng buộc cơ sở dữ liệu: ", e);
            throw new AppException(ErrorCode.DATABASE_CONSTRAINT_VIOLATION);
        } catch (Exception e) {
            log.error("Lỗi không xác định khi lưu tour: ", e);
            throw new AppException(ErrorCode.TOUR_SAVE_FAILED);
        }

        // 10. Trả về response
        return TourResponse.builder()
//                .id(tour.getId())
//                .title(tour.getTitle())
//                .isFeatured(tour.getIsFeatured())
//                .categoryId(category.getId())
//                .destinationId(destination.getId())
//                .departureId(departure.getId())
//                .transportationId(transportation.getId())
//                .description(tour.getDescription())
                .build();
    }

//    @Override
//    @Transactional
//    public TourResponse updateTour(
//            Long id,
//            String title,
//            Boolean isFeatured,
//            Long categoryId,
//            Long destinationId,
//            Long departureId,
//            Long transportationId,
//            String description,
//            String informationJson,
//            String scheduleJson,
//            String tourDetailJson,
//            List<MultipartFile> imageFiles)
//            throws JsonProcessingException {
//
//        // 1. Tìm tour theo ID
//        Tour tour = tourRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));
//
//        // 2. Parse JSON thành các đối tượng Java
//        TourInfomationRequest information = objectMapper.readValue(informationJson, TourInfomationRequest.class);
//        List<TourScheduleRequest> schedules =
//                objectMapper.readValue(scheduleJson, new TypeReference<List<TourScheduleRequest>>() {});
//        List<TourDetailRequest> tourDetails =
//                objectMapper.readValue(tourDetailJson, new TypeReference<List<TourDetailRequest>>() {});
//
//        // 3. Validate và lấy các entity liên quan
//        Category category = categoryRepository
//                .findById(categoryId)
//                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
//        if (category instanceof HibernateProxy) {
//            category = (Category)
//                    ((HibernateProxy) category).getHibernateLazyInitializer().getImplementation();
//        }
//
//        Departure departure = departureRepository
//                .findById(departureId)
//                .orElseThrow(() -> new AppException(ErrorCode.DEPARTURE_NOT_FOUND));
//        if (departure instanceof HibernateProxy) {
//            departure = (Departure)
//                    ((HibernateProxy) departure).getHibernateLazyInitializer().getImplementation();
//        }
//
//        Destination destination = destinationRepository
//                .findById(destinationId)
//                .orElseThrow(() -> new AppException(ErrorCode.DESTINATION_NOT_FOUND));
//        if (destination instanceof HibernateProxy) {
//            destination = (Destination)
//                    ((HibernateProxy) destination).getHibernateLazyInitializer().getImplementation();
//        }
//
//        Transport transportation = transportRepository
//                .findById(transportationId)
//                .orElseThrow(() -> new AppException(ErrorCode.TRANSPORT_NOT_FOUND));
//        if (transportation instanceof HibernateProxy) {
//            transportation = (Transport) ((HibernateProxy) transportation)
//                    .getHibernateLazyInitializer()
//                    .getImplementation();
//        }
//
//        // 4. Cập nhật thông tin cơ bản của tour
//        tour.setTitle(title);
//        tour.setIsFeatured(isFeatured);
//        tour.setCategory(category);
//        tour.setDeparture(departure);
//        tour.setDestination(destination);
//        tour.setTransport(transportation);
//        tour.setDescription(description);
//
//
//        // 5. Cập nhật thông tin chi tiết (information)
//        TourInformation tourInfo = tour.getTourInformation();
//        if (tourInfo == null) {
//            tourInfo = TourInformation.builder().tour(tour).build();
//        }
//        tourInfo.setAttractions(information.getAttractions());
//        tourInfo.setCuisine(information.getCuisine());
//        tourInfo.setIdealTime(information.getIdealTime());
//        tourInfo.setPromotion(information.getPromotion());
//        tourInfo.setSuitableObject(information.getSuitableObject());
//        tourInfo.setVehicle(information.getVehicle());
//        tourInformationRepository.save(tourInfo);
//
//        // 6. Cập nhật lịch trình (schedules)
//        // Xóa các lịch trình cũ
//        tourSchedulesRepository.deleteByTourId(id);
//        List<TourSchedule> scheduleList = new ArrayList<>();
//        for (TourScheduleRequest scheduleRequest : schedules) {
//            TourSchedule schedule = TourSchedule.builder()
//                    .day(scheduleRequest.getDay())
//                    .title(scheduleRequest.getTitle())
//                    .information(scheduleRequest.getInformation())
//                    .tour(tour)
//                    .build();
//            scheduleList.add(schedule);
//        }
//        tourSchedulesRepository.saveAll(scheduleList);
//
//        // 7. Cập nhật chi tiết tour (giá và ngày khởi hành)
//        // Lấy danh sách TourDetail hiện có
//        List<TourDetail> existingDetails = tourDetailRepository.findByTour_Id(id);
//        List<TourDetail> details = new ArrayList<>();
//
//        // Tạo map để so sánh TourDetailRequest với TourDetail dựa trên một trường duy nhất (giả sử dayStart là duy
//        // nhất)
//        Map<LocalDateTime, TourDetailRequest> requestMap =
//                tourDetails.stream().collect(Collectors.toMap(TourDetailRequest::getDayStart, req -> req, (a, b) -> a));
//
//        // Xóa các TourDetail không còn trong request
//        for (TourDetail existingDetail : existingDetails) {
//            if (!requestMap.containsKey(existingDetail.getDayStart())) {
//                tourDetailRepository.delete(existingDetail);
//            }
//        }
//
//        // Cập nhật hoặc thêm mới TourDetail
//        for (TourDetailRequest detailRequest : tourDetails) {
//            TourDetail detail = existingDetails.stream()
//                    .filter(d -> d.getDayStart().equals(detailRequest.getDayStart()))
//                    .findFirst()
//                    .orElse(TourDetail.builder().tour(tour).build());
//
//            detail.setAdultPrice(detailRequest.getAdultPrice());
//            detail.setChildrenPrice((detailRequest.getChildrenPrice() != null) ? detailRequest.getChildrenPrice() : 0);
//            detail.setChildPrice((detailRequest.getChildPrice() != null) ? detailRequest.getChildPrice() : 0);
//            detail.setBabyPrice((detailRequest.getBabyPrice() != null) ? detailRequest.getBabyPrice() : 0);
//            detail.setStock(detailRequest.getStock());
//            detail.setDayStart(detailRequest.getDayStart());
//            detail.setDayReturn(detailRequest.getDayReturn());
//            detail.setStatus(TourDetailStatus.SCHEDULED);
//            detail.setSingleRoomSupplementPrice(
//                    (detailRequest.getSingleRoomSupplementPrice() != null)
//                            ? detailRequest.getSingleRoomSupplementPrice()
//                            : 0);
//            if (detail.getBookedSlots() == null) {
//                detail.setBookedSlots(0);
//            }
//            detail.setTour(tour);
//            details.add(detail);
//        }
//        tourDetailRepository.saveAll(details);
//
//        // 8. Cập nhật ảnh
//        List<TourImage> tourImages = new ArrayList<>();
//        if (imageFiles != null && !imageFiles.isEmpty()) {
//            // Xóa các ảnh cũ
//            tourImageRepository.deleteByTourId(id);
//            for (MultipartFile file : imageFiles) {
//                if (file != null && !file.isEmpty()) {
//                    try {
//                        CloudinaryUploadResponse cloudinaryUploadResponse = uploadImageFile.upLoadImage(file);
//                        TourImage tourImage = TourImage.builder()
//                                .tour(tour)
//                                .cloudinaryPublicId(cloudinaryUploadResponse.getPublicId())
//                                .cloudinaryUrl(cloudinaryUploadResponse.getUrl())
//                                .altText("Hình ảnh về " + tour.getTitle())
//                                .build();
//                        tourImages.add(tourImage);
//                        tourImageRepository.save(tourImage);
//                    } catch (IOException e) {
//                        throw new AppException(ErrorCode.IMAGE_UPLOAD_FAILED);
//                    }
//                }
//            }
//            tour.setTourImages(tourImages);
//        }
//
//        // 9. Cập nhật các liên kết
//        // 3. Gọi saveAndFlush sau khi đã gán đầy đủ
//        tour.setTourInformation(tourInfo);
//        tour.setTourSchedules(scheduleList);
//        tour.setTourDetails(details);
////        tour.setTourImages(tourImages);
//
//
//        System.out.println(tour);
//
//        // 10. Lưu tour đã cập nhật
//        tourRepository.save(tour);
//        tourRepository.flush();
//
//        // 11. Tạo và trả về response
//        TourResponse tourResponse = new TourResponse();
//        return tourResponse;
//    }

    @Override
    public void deleteTour(Long id) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));

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
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));
        tour.setInActive(statusRequest.getInActive());
    }

    @Override
    public void changeTourFeatured(Long id, FeaturedRequest featuredRequest) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));
        tour.setIsFeatured(featuredRequest.getIsFeatured());
    }

    @Override
    public void changeToursDetailsStatus(Long TourDetailId, ToursDetailsStatusRequest request) {
        TourDetail tourDetail = tourDetailRepository
                .findById(TourDetailId)
                .orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));
        tourDetail.setStatus(TourDetailStatus.valueOf(request.getStatus()));
        tourDetailRepository.save(tourDetail);
    }
}
