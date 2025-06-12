package com.travel.travel_booking_service.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import org.hibernate.proxy.HibernateProxy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
                    .title(scheduleRequest.getTitle().toUpperCase())
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
                    .status(TourDetailStatus.SCHEDULED.name())
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
    public List<CustomerTourSearchResponse> getAllActiveToursWithFilter(
            Integer departureId, Integer budgetId, Integer categoryId, Integer transTypeId, LocalDate fromDate) {
        Specification<Tour> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Lọc các tour đang hoạt động
            predicates.add(criteriaBuilder.equal(root.get("inActive"), true));

            // Lọc theo departureId nếu có
            if (departureId != null) {
                predicates.add(criteriaBuilder.equal(root.get("departure").get("id"), departureId));
            }

            // Lọc theo categoryId nếu có
            if (categoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));
            }

            // Lọc theo transTypeId nếu có
            if (transTypeId != null) {
                predicates.add(criteriaBuilder.equal(root.get("transport").get("id"), transTypeId));
            }

            // Lọc theo budgetId (liên quan đến adultPrice trong TourDetail)
            if (budgetId != null) {
                Join<Tour, TourDetail> tourDetailJoin = root.join("tourDetails", JoinType.INNER);
                // budgetId ánh xạ đến một khoảng giá
                predicates.add(criteriaBuilder.between(
                        tourDetailJoin.get("adultPrice"), getBudgetRangeMin(budgetId), getBudgetRangeMax(budgetId)));
            }

            // Lọc theo fromDate nếu có, và loại bỏ tour có dayStart đã qua
            LocalDate today = LocalDate.now();
            Join<Tour, TourDetail> tourDetailJoin = root.join("tourDetails", JoinType.INNER);
            if (fromDate != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(tourDetailJoin.get("dayStart"), fromDate.atStartOfDay()));
            } else {
                // Loại các tour có dayStart trước ngày hiện tại
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(tourDetailJoin.get("dayStart"), today.atStartOfDay()));
            }

            // Chỉ lấy các tour có trạng thái hợp lệ (scheduled, confirmed, in_progress)
            predicates.add(tourDetailJoin.get("status").in(TourDetailStatus.SCHEDULED));

            // Đảm bảo truy vấn trả về các kết quả khác nhau
            query.distinct(true);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        List<Tour> tours = tourRepository.findAll(specification);

        return tours.stream()
                .map(tour -> {
                    CustomerTourSearchResponse response = new CustomerTourSearchResponse();

                    response.setId(tour.getId());
                    response.setTitle(tour.getTitle());
                    response.setDestination(tour.getDestination().getName());
                    response.setTransportation(tour.getTransport().getName());
                    response.setDeparture(tour.getDeparture().getName());
                    response.setCategory(tour.getCategory().getName());

                    // Lấy tất cả dayStart hợp lệ từ tourDetails
                    List<String> dayStarts = tour.getTourDetails().stream()
                            .filter(detail -> (fromDate == null
                                            && !detail.getDayStart()
                                                    .toLocalDate()
                                                    .isBefore(LocalDate.now()))
                                    || (fromDate != null
                                            && !detail.getDayStart()
                                                    .toLocalDate()
                                                    .isBefore(fromDate)))
                            .filter(detail -> detail.getStatus() == TourDetailStatus.SCHEDULED.name())
                            .map(detail -> detail.getDayStart().toString())
                            .collect(Collectors.toList());

                    // Nếu không có tourDetails hợp lệ, loại bỏ tour
                    if (dayStarts.isEmpty()) {
                        return null;
                    }

                    // Lấy tourDetail đầu tiên để lấy giá và duration
                    TourDetail firstValidDetail = tour.getTourDetails().stream()
                            .filter(detail -> (fromDate == null
                                            && !detail.getDayStart()
                                                    .toLocalDate()
                                                    .isBefore(LocalDate.now()))
                                    || (fromDate != null
                                            && !detail.getDayStart()
                                                    .toLocalDate()
                                                    .isBefore(fromDate)))
                            .filter(detail -> detail.getStatus() == TourDetailStatus.SCHEDULED.name())
                            .findFirst()
                            .orElse(null);

                    if (firstValidDetail != null) {
                        response.setPrice(firstValidDetail.getAdultPrice());
                        response.setDuration(Long.toString(ChronoUnit.DAYS.between(
                                firstValidDetail.getDayStart(), firstValidDetail.getDayReturn())));
                    }

                    response.setDayStarts(dayStarts);

                    // Ánh xạ thêm ảnh chính nếu có
                    if (!tour.getTourImages().isEmpty()) {
                        response.setTourImages(tour.getTourImages().get(0).getCloudinaryUrl());
                    }
                    return response;
                })
                .filter(Objects::nonNull) // Loại bỏ các response null
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerTourSearchResponse> getAllActiveToursWithFilterWithSlug(
            String slug,
            Integer departureId,
            Integer budgetId,
            Integer categoryId,
            Integer transTypeId,
            LocalDate fromDate) {
        Specification<Tour> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Lọc các tour đang hoạt động
            predicates.add(criteriaBuilder.equal(root.get("inActive"), true));

            // Lọc theo slug nếu có
            if (StringUtils.hasText(slug)) {
                System.out.println("Slug received: " + slug);

                try {
                    String decodedSlug = URLDecoder.decode(slug, StandardCharsets.UTF_8.name())
                            .toLowerCase()
                            .trim();
                    //                            .replaceAll("\\.", " ");
                    System.out.println("decodedSlug: " + decodedSlug);

                    // Normalize the slug for comparison
                    String normalizedSlug = decodedSlug.toLowerCase().trim();
                    System.out.println("normalizedSlug: " + normalizedSlug);

                    Join<Tour, Destination> destinationJoin = root.join("destination", JoinType.INNER);

                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(destinationJoin.get("name")), "%" + decodedSlug.toLowerCase() + "%"));
                } catch (UnsupportedEncodingException e) {
                    System.err.println("Error decoding slug: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }

            // Lọc theo departureId nếu có
            if (departureId != null) {
                predicates.add(criteriaBuilder.equal(root.get("departure").get("id"), departureId));
            }

            // Lọc theo categoryId nếu có
            if (categoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));
            }

            // Lọc theo transTypeId nếu có
            if (transTypeId != null) {
                predicates.add(criteriaBuilder.equal(root.get("transport").get("id"), transTypeId));
            }

            // Lọc theo budgetId (liên quan đến adultPrice trong TourDetail)
            if (budgetId != null) {
                Join<Tour, TourDetail> tourDetailJoin = root.join("tourDetails", JoinType.INNER);
                // budgetId ánh xạ đến một khoảng giá
                predicates.add(criteriaBuilder.between(
                        tourDetailJoin.get("adultPrice"), getBudgetRangeMin(budgetId), getBudgetRangeMax(budgetId)));
            }

            // Lọc theo fromDate nếu có, và loại bỏ tour có dayStart đã qua
            LocalDate today = LocalDate.now();
            Join<Tour, TourDetail> tourDetailJoin = root.join("tourDetails", JoinType.INNER);
            if (fromDate != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(tourDetailJoin.get("dayStart"), fromDate.atStartOfDay()));
            } else {
                // Loại các tour có dayStart trước ngày hiện tại
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(tourDetailJoin.get("dayStart"), today.atStartOfDay()));
            }

            // Chỉ lấy các tour có trạng thái hợp lệ (scheduled, confirmed, in_progress)
            predicates.add(tourDetailJoin.get("status").in(TourDetailStatus.SCHEDULED));

            // Đảm bảo truy vấn trả về các kết quả khác nhau
            query.distinct(true);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        List<Tour> tours = tourRepository.findAll(specification);

        return tours.stream()
                .map(tour -> {
                    CustomerTourSearchResponse response = new CustomerTourSearchResponse();

                    response.setId(tour.getId());
                    response.setTitle(tour.getTitle());
                    response.setDestination(tour.getDestination().getName());
                    response.setTransportation(tour.getTransport().getName());
                    response.setDeparture(tour.getDeparture().getName());
                    response.setCategory(tour.getCategory().getName());

                    // Lấy tất cả dayStart hợp lệ từ tourDetails
                    List<String> dayStarts = tour.getTourDetails().stream()
                            .filter(detail -> (fromDate == null
                                            && !detail.getDayStart()
                                                    .toLocalDate()
                                                    .isBefore(LocalDate.now()))
                                    || (fromDate != null
                                            && !detail.getDayStart()
                                                    .toLocalDate()
                                                    .isBefore(fromDate)))
                            .filter(detail -> detail.getStatus() == TourDetailStatus.SCHEDULED.name())
                            .map(detail -> detail.getDayStart().toString())
                            .collect(Collectors.toList());

                    // Nếu không có tourDetails hợp lệ, loại bỏ tour
                    if (dayStarts.isEmpty()) {
                        return null;
                    }

                    // Lấy tourDetail đầu tiên để lấy giá và duration
                    TourDetail firstValidDetail = tour.getTourDetails().stream()
                            .filter(detail -> (fromDate == null
                                            && !detail.getDayStart()
                                                    .toLocalDate()
                                                    .isBefore(LocalDate.now()))
                                    || (fromDate != null
                                            && !detail.getDayStart()
                                                    .toLocalDate()
                                                    .isBefore(fromDate)))
                            .filter(detail -> Objects.equals(detail.getStatus(), TourDetailStatus.SCHEDULED.name()))
                            .findFirst()
                            .orElse(null);

                    if (firstValidDetail != null) {
                        response.setPrice(firstValidDetail.getAdultPrice());
                        response.setDuration(Long.toString(ChronoUnit.DAYS.between(
                                firstValidDetail.getDayStart(), firstValidDetail.getDayReturn())));
                    }

                    response.setDayStarts(dayStarts);

                    // Ánh xạ thêm ảnh chính nếu có
                    if (!tour.getTourImages().isEmpty()) {
                        response.setTourImages(tour.getTourImages().get(0).getCloudinaryUrl());
                    }
                    return response;
                })
                .filter(Objects::nonNull) // Loại bỏ các response null
                .collect(Collectors.toList());
    }

    @Override
    public CustomerTourViewResponse getTourDetails(Long id) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));

        CustomerTourViewResponse response = new CustomerTourViewResponse();
        response.setId(tour.getId());
        response.setTitle(tour.getTitle());
        response.setDescription(tour.getDescription());
        response.setDestination(tour.getDestination().getName());
        response.setDeparture(tour.getDeparture().getName());

        List<TourImage> tourImages = tour.getTourImages();
        List<TourImageResponse> tourImageResponses = new ArrayList<>();
        for (TourImage tourImage : tourImages) {
            TourImageResponse tourImageResponse = new TourImageResponse();
            tourImageResponse.setId(tourImage.getId());
            tourImageResponse.setTourId(tour.getId());
            tourImageResponse.setImageUrl(tourImage.getCloudinaryUrl());
            tourImageResponses.add(tourImageResponse);
        }
        response.setTourImages(tourImageResponses);

        List<TourDetail> tourDetails = tour.getTourDetails();
        List<CustomerTourDetail> customerTourDetails = new ArrayList<>();
        for (TourDetail tourDetail : tourDetails) {
            CustomerTourDetail customerTourDetail = CustomerTourDetail.builder()
                    .id(tourDetail.getId())
                    .adultPrice(tourDetail.getAdultPrice())
                    .childrenPrice(tourDetail.getChildrenPrice())
                    .childPrice(tourDetail.getChildPrice())
                    .babyPrice(tourDetail.getBabyPrice())
                    .singleRoomSupplementPrice(tourDetail.getSingleRoomSupplementPrice())
                    .stock(tourDetail.getStock())
                    .bookedSlots(tourDetail.getBookedSlots())
                    .remainingSlots(tourDetail.getRemainingSlots())
                    .discount(tourDetail.getDiscountPercent())
                    .dayStart(tourDetail.getDayStart())
                    .dayReturn(tourDetail.getDayReturn())
                    .duration(ChronoUnit.DAYS.between(tourDetail.getDayStart(), tourDetail.getDayReturn()))
                    .status(tourDetail.getStatus())
                    .build();

            customerTourDetails.add(customerTourDetail);
        }
        response.setTourDetails(customerTourDetails);

        TourInformation tourInformation = tour.getTourInformation();
        TourInformationResponse tourInformationResponse = TourInformationResponse.builder()
                .id(tourInformation.getId())
                .TourId(id)
                .attractions(tourInformation.getAttractions())
                .cuisine(tourInformation.getCuisine())
                .idealTime(tourInformation.getIdealTime())
                .vehicle(tourInformation.getVehicle())
                .promotion(tourInformation.getPromotion())
                .suitableObject(tourInformation.getSuitableObject())
                .build();
        response.setTourInformation(tourInformationResponse);

        List<TourSchedule> tourSchedules = tour.getTourSchedules();
        List<TourScheduleResponse> tourScheduleResponses = new ArrayList<>();
        for (TourSchedule tourSchedule : tourSchedules) {
            TourScheduleResponse tourScheduleResponse = new TourScheduleResponse();
            tourScheduleResponse.setId(tourSchedule.getId());
            tourScheduleResponse.setTourId(id);
            tourScheduleResponse.setDay(tourSchedule.getDay());
            tourScheduleResponse.setTitle(tourSchedule.getTitle());
            tourScheduleResponse.setInformation(tourSchedule.getInformation());
            tourScheduleResponses.add(tourScheduleResponse);
        }
        response.setTourSchedules(tourScheduleResponses);

        return response;
    }

    // Hàm lấy tất cả các destination con nếu có trong destination
    public List<Long> getAllDestinationIds(Long parentId) {
        List<Long> ids = new ArrayList<>();
        collectAllDestinationIds(parentId, ids);
        return ids;
    }

    private void collectAllDestinationIds(Long parentId, List<Long> ids) {
        ids.add(parentId);
        List<Destination> children = destinationRepository.findByParentId(parentId);
        for (Destination child : children) {
            collectAllDestinationIds(child.getId(), ids); // đệ quy để lấy các cấp con sâu hơn
        }
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

        List<Long> destinationIds = null;
        if (destinationId != null) {
            destinationIds = getAllDestinationIds(destinationId);
        }

        List<Tour> tours = tourRepository.findAllFiltered(
                destinationIds, departureId, transportationId, categoryId, inActive, isFeatured, title);

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
                tourDetailResponse.setStatus(tourDetail.getStatus());
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
    public TourDetailViewResponse getTourDetailsViewByTourId(Long id) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));

        TourDetailViewResponse tourDetailViewResponse = new TourDetailViewResponse();

        TourInformation tourInformation = tour.getTourInformation();
        List<TourSchedule> tourSchedules = tour.getTourSchedules();
        List<TourDetail> tourDetails = tour.getTourDetails();
        List<TourImage> tourImages = tour.getTourImages();

        // Lấy thông tin cơ bản của tour
        tourDetailViewResponse.setTitle(tour.getTitle());
        tourDetailViewResponse.setCategory(tour.getCategory().getName());
        tourDetailViewResponse.setDeparture(tour.getDeparture().getName());
        tourDetailViewResponse.setDestination(tour.getDestination().getName());
        tourDetailViewResponse.setTransportation(tour.getTransport().getName());
        tourDetailViewResponse.setDescription(tour.getDescription());
        tourDetailViewResponse.setCreatedBy(tour.getCreatedBy());
        tourDetailViewResponse.setInActive(tour.getInActive());

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

        tourDetailViewResponse.setTourInformation(tourInformationResponse);

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

        tourDetailViewResponse.setTourSchedules(tourScheduleResponses);

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
            tourDetailEdit.setBookedSlots(tourDetail.getBookedSlots());
            tourDetailEdit.setRemainingSlots(tourDetail.getRemainingSlots());
            tourDetailEdit.setSingleRoomSupplementPrice(tourDetail.getSingleRoomSupplementPrice());

            tourDetailEdits.add(tourDetailEdit);
        }

        tourDetailViewResponse.setTourDetails(tourDetailEdits);

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

        tourDetailViewResponse.setImages(tourImageResponses);

        return tourDetailViewResponse;
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
            tourDetailEdit.setBookedSlots(tourDetail.getBookedSlots());
            tourDetailEdit.setRemainingSlots(tourDetail.getRemainingSlots());
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
        if (information == null
                || schedules == null
                || tourDetails == null
                || schedules.isEmpty()
                || tourDetails.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_JSON_DATA);
        }

        // 3. Lấy các entity liên quan
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        if (category instanceof HibernateProxy) {
            category = (Category)
                    ((HibernateProxy) category).getHibernateLazyInitializer().getImplementation();
        }

        Departure departure = departureRepository
                .findById(departureId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTURE_NOT_FOUND));
        if (departure instanceof HibernateProxy) {
            departure = (Departure)
                    ((HibernateProxy) departure).getHibernateLazyInitializer().getImplementation();
        }

        Destination destination = destinationRepository
                .findById(destinationId)
                .orElseThrow(() -> new AppException(ErrorCode.DESTINATION_NOT_FOUND));
        if (destination instanceof HibernateProxy) {
            destination = (Destination)
                    ((HibernateProxy) destination).getHibernateLazyInitializer().getImplementation();
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
            if (detailRequest.getDayStart() == null
                    || detailRequest.getDayReturn() == null
                    || detailRequest.getStock() == null
                    || detailRequest.getAdultPrice() == null) {
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
                    .status(TourDetailStatus.SCHEDULED.name())
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
                        if (cloudinaryUploadResponse.getPublicId() == null
                                || cloudinaryUploadResponse.getUrl() == null) {
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
        tourDetail.setStatus(request.getStatus());
        tourDetailRepository.save(tourDetail);
    }

    // Hàm giả định để lấy khoảng giá từ budgetId
    private Long getBudgetRangeMin(Integer budgetId) {
        // Thay bằng logic thực tế của bạn
        return switch (budgetId) {
            case 1 -> 0L;
            case 2 -> 5_000_000L;
            case 3 -> 10_000_000L;
            case 4 -> 20_000_000L;
            default -> 0L;
        };
    }

    private Long getBudgetRangeMax(Integer budgetId) {
        // Thay bằng logic thực tế của bạn
        return switch (budgetId) {
            case 1 -> 5_000_000L;
            case 2 -> 10_000_000L;
            case 3 -> 20_000_000L;
            default -> Long.MAX_VALUE;
        };
    }
}
