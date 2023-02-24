package com.metro.setups.shifdetails.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.shifdetails.dtos.ShiftDetailsDTO;
import com.metro.setups.shifdetails.entities.ShiftDetails;
import com.metro.setups.shifdetails.repositories.ShiftDetailsRepository;
import com.metro.setups.shifdetails.services.ShiftDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ShiftDetailsServiceImpl implements ShiftDetailsService {
    private final ShiftDetailsRepository shiftDetailsRepository;

    public ShiftDetailsServiceImpl(ShiftDetailsRepository shiftDetailsRepository) {
        this.shiftDetailsRepository = shiftDetailsRepository;
    }

    @Override
    public ApiResponse createShiftDetails(ShiftDetailsDTO shiftDetailsDTO) {
        log.info("Creating shift Details with name {}", shiftDetailsDTO.getName());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the shiftDetails")
                .success(false)
                .data(shiftDetailsDTO)
                .build();
        ShiftDetails shiftDetails = ShiftDetails.builder()
                .description(shiftDetailsDTO.getDescription())
                .name(shiftDetailsDTO.getName())
                .build();
        shiftDetails = shiftDetailsRepository.save(shiftDetails);
        if (StringUtils.isNotEmpty(shiftDetails.getId().toString())) {
            shiftDetailsDTO.setId(Long.valueOf(shiftDetails.getId().longValue()));
            response.setMessage("shift details created successfully");
            response.setData(shiftDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateShiftDetails(Long id, ShiftDetailsDTO shiftDetailsDTO) {
        log.info("updating Shift details with name {}", shiftDetailsDTO.getName());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update the shiftDetails")
                .success(false)
                .data(shiftDetailsDTO)
                .build();
        ShiftDetails shiftDetails = shiftDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Shift Details with id "+  id + " not found"));
        shiftDetails.setName(shiftDetailsDTO.getName());
        shiftDetails.setDescription(shiftDetailsDTO.getDescription());
        shiftDetails = shiftDetailsRepository.save(shiftDetails);
        if (StringUtils.isNotEmpty(shiftDetails.getId().toString())) {
            shiftDetailsDTO.setId(Long.valueOf(shiftDetails.getId().longValue()));
            response.setMessage("Shift Details updated  successfully");
            response.setData(shiftDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse getShiftDetails() {
        log.info("Getting all shift Details");
        List<ShiftDetailsDTO> shiftDetailsDTOList = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(shiftDetailsDTOList)
                .message("No Shift Details existing")
                .build();
        List<ShiftDetails> list = shiftDetailsRepository.findAll(Sort.by("name").ascending());
        if (!list.isEmpty()) {
            shiftDetailsDTOList.addAll(list.stream().map(e -> {
                ShiftDetailsDTO shiftDetailsDTO = ShiftDetailsDTO.builder()
                        .name(e.getName())
                        .description(e.getDescription())
                        .Id(e.getId())
                        .build();
                return shiftDetailsDTO;
            }).toList());

        }
        response.setMessage("All Shift Details fetched successfully");
        response.setData(shiftDetailsDTOList);
        response.setSuccess(true);
        return response;
    }

    @Override
    public ApiResponse selectShiftDetailsByName(String name) {
        log.info("Getting shift Details by Name");
        List<ShiftDetailsDTO> shiftDetailsDTOList = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(shiftDetailsDTOList)
                .message("No Shift Details existing")
                .build();
        List<ShiftDetails> list = shiftDetailsRepository.findShiftDetailsByNameIgnoreCase(name);
        if (!list.isEmpty()) {
            shiftDetailsDTOList.addAll(list.stream().map(e -> {
                ShiftDetailsDTO shiftDetailsDTO = ShiftDetailsDTO.builder()
                        .name(e.getName())
                        .description(e.getDescription())
                        .Id(e.getId())
                        .build();
                return shiftDetailsDTO;
            }).toList());

        }
        response.setMessage("All Shift Details fetched successfully");
        response.setData(shiftDetailsDTOList);
        response.setSuccess(true);
        return response;
    }

    @Override
    public ApiResponse selectShiftDetailsByID(Long id) {
        log.info("Getting shift by Id");
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("No Shift Details existing")
                .build();
        ShiftDetails shiftDetails = shiftDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "ShiftDetails with id " +id+ " not found."));
        if (StringUtils.isNotEmpty(shiftDetails.getId().toString())) {
            ShiftDetailsDTO shiftDetailsDTO = ShiftDetailsDTO.builder()
                    .Id((Long) shiftDetails.getId())
                    .name(shiftDetails.getName())
                    .description(shiftDetails.getDescription())
                    .build();
            response.setMessage("Shift Details fetched  successfully");
            response.setData(shiftDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public boolean shiftDetailsExists(String name) {
        return shiftDetailsRepository.existsByNameIgnoreCase(name);
    }
}
