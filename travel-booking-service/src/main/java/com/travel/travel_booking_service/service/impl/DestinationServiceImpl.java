package com.travel.travel_booking_service.service.impl;

import com.travel.travel_booking_service.dto.DestinationDTO;
import com.travel.travel_booking_service.entity.Destination;
import com.travel.travel_booking_service.exception.BusinessException;
import com.travel.travel_booking_service.exception.DuplicateResourceException;
import com.travel.travel_booking_service.exception.ResourceNotFoundException;
import com.travel.travel_booking_service.mapper.DestinationMapper;
import com.travel.travel_booking_service.repository.DestinationRepository;
import com.travel.travel_booking_service.service.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;
    private final DestinationMapper destinationMapper;

    @Override
    public DestinationDTO createDestination(DestinationDTO destinationDTO) {
        validateDestination(destinationDTO);

        if (isDestinationExists(destinationDTO.getName())) {
            throw new DuplicateResourceException("Destination", "name", destinationDTO.getName());
        }

        Destination destination = destinationMapper.toEntity(destinationDTO);
        Destination savedDestination = destinationRepository.save(destination);
        return destinationMapper.toDTO(savedDestination);
    }

    @Override
    public DestinationDTO updateDestination(Long id, DestinationDTO destinationDTO) {
        validateDestination(destinationDTO);

        Destination existingDestination = destinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination", "id", id));

        if (!existingDestination.getName().equals(destinationDTO.getName()) 
                && isDestinationExists(destinationDTO.getName())) {
            throw new DuplicateResourceException("Destination", "name", destinationDTO.getName());
        }

        destinationMapper.updateEntityFromDTO(destinationDTO, existingDestination);
        Destination updatedDestination = destinationRepository.save(existingDestination);
        return destinationMapper.toDTO(updatedDestination);
    }

    @Override
    public void deleteDestination(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination", "id", id));

        if (destination.getTours() != null && !destination.getTours().isEmpty()) {
            throw new BusinessException(ErrorCode.DESTINATION_IN_USE);
        }

        destinationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public DestinationDTO getDestinationById(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination", "id", id));
        return destinationMapper.toDTO(destination);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DestinationDTO> getAllDestinations(Pageable pageable) {
        return destinationRepository.findAll(pageable)
                .map(destinationMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DestinationDTO> getPopularDestinations() {
        return destinationRepository.findPopularDestinations().stream()
                .map(destinationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DestinationDTO> searchDestinations(String keyword) {
        return destinationRepository.searchDestinations(keyword).stream()
                .map(destinationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isDestinationExists(String name) {
        return destinationRepository.existsByName(name);
    }

    @Override
    public void validateDestination(DestinationDTO destinationDTO) {
        if (destinationDTO.getName() == null || destinationDTO.getName().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Destination name is required");
        }

        if (destinationDTO.getDescription() == null || destinationDTO.getDescription().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Destination description is required");
        }

        if (destinationDTO.getRegion() == null || destinationDTO.getRegion().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Destination region is required");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DestinationDTO> getDestinationsByRegion(String region) {
        return destinationRepository.findByRegion(region).stream()
                .map(destinationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DestinationDTO> getDestinationsWithTourCount() {
        return destinationRepository.findAllWithTourCount().stream()
                .map(destinationMapper::toDTO)
                .collect(Collectors.toList());
    }
} 