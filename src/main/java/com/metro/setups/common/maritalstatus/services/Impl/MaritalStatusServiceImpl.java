package com.metro.setups.common.maritalstatus.services.Impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.common.maritalstatus.dtos.MaritalStatusDto;
import com.metro.setups.common.maritalstatus.repositories.MaritalStatusRepository;
import com.metro.setups.common.maritalstatus.services.MaritalStatusService;

import com.metro.setups.common.maritalstatus.specifications.MaritalStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class MaritalStatusServiceImpl implements MaritalStatusService {

    private final MaritalStatusRepository maritalStatusRepository;

    @Override
    public ApiResponse createMaritalStatus(MaritalStatusDto maritalStatusDto) {
        log.info("Saving marital status {}", maritalStatusDto.getMaritalStatusName());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the marital status")
                .success(false)
                .data(maritalStatusDto)
                .build();
        if (existsMaritalStatusName(maritalStatusDto.getMaritalStatusName())) {
            throw new DuplicateResourceException(
                    "marital status is existing"
            );
        }
        MaritalStatus maritalStatus = MaritalStatus.builder()
                .maritalStatusName(maritalStatusDto.getMaritalStatusName())
                .build();

        maritalStatus = maritalStatusRepository.save(maritalStatus);
        if (StringUtils.isNotEmpty(maritalStatus.getId().toString())) {
            maritalStatusDto.setMaritalStatusId(Long.valueOf(maritalStatus.getId().longValue()));
            response.setMessage("marital status inserted  successfully");
            response.setData(maritalStatusDto);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateMaritalStatus(Long id, MaritalStatusDto maritalStatusDto) {

        log.info("updating marital status {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update marital status")
                .success(false)
                .data(maritalStatusDto)
                .build();

        com.metro.setups.common.maritalstatus.specifications.MaritalStatus maritalStatus = maritalStatusRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "marital status not found with id  " + id));

        if (existsMaritalStatusName(maritalStatusDto.getMaritalStatusName())) {
            throw new DuplicateResourceException(
                    "marital status is existing"
            );
        }

        maritalStatus.setMaritalStatusName(maritalStatusDto.getMaritalStatusName());
        maritalStatus = maritalStatusRepository.save(maritalStatus);

        if (StringUtils.isNotEmpty(maritalStatus.getId().toString())) {
            maritalStatusDto.setMaritalStatusId(Long.valueOf(maritalStatus.getId().longValue()));
            response.setMessage("marital status updated  successfully");
            response.setData(maritalStatusDto);
            response.setSuccess(true);
        }

        return response;
    }

    @Override
    public ApiResponse getMaritalStatuses() {
        log.info("fetching  Marital status categories {}");
        List<MaritalStatusDto> maritalStatusDtos = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(maritalStatusDtos)
                .message("No record is existing")
                .build();

        List<com.metro.setups.common.maritalstatus.specifications.MaritalStatus> statusNameList = maritalStatusRepository.findAll(Sort.by("maritalStatusName").ascending());
        if (!statusNameList.isEmpty()) {
            maritalStatusDtos.addAll(statusNameList.stream().map(e -> {
                MaritalStatusDto maritalStatusDto = MaritalStatusDto.builder()
                        .maritalStatusName(e.getMaritalStatusName())
                        .maritalStatusId(e.getId().longValue())
                        .build();
                return maritalStatusDto;
            }).toList());
            response.setMessage("Marital status fetched  successfully");
            response.setData(maritalStatusDtos);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse deleteMaritalStatus(Long id) {
        return null;
    }

    @Override
    public ApiResponse selectMaritalStatusByName(String name) {

        log.info("fetching  Marital status categories {}");
        List<MaritalStatusDto> maritalStatusDtos = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(maritalStatusDtos)
                .message("No record is existing")
                .build();

        List<MaritalStatus> statusNameList = maritalStatusRepository.findByMaritalStatusNameContainingIgnoreCase(name.trim());
        if (!statusNameList.isEmpty()) {
            maritalStatusDtos.addAll(statusNameList.stream().map(e -> {
                MaritalStatusDto maritalStatusDto = MaritalStatusDto.builder()
                        .maritalStatusName(e.getMaritalStatusName())
                        .maritalStatusId(e.getId().longValue())
                        .build();
                return maritalStatusDto;
            }).toList());
            response.setMessage("Marital status fetched  successfully");
            response.setData(maritalStatusDtos);
            response.setSuccess(true);
        }
        return response;

    }

    @Override
    public ApiResponse selectMaritalStatusByID(Long id) {
        log.info("selecting marital status {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch marital status ")
                .success(false)
                .build();

        MaritalStatus maritalStatus = maritalStatusRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "marital status  not found with id  " + id));

        if (StringUtils.isNotEmpty(maritalStatus.getId().toString())) {
            MaritalStatusDto maritalStatusDto = MaritalStatusDto.builder()
                    .maritalStatusId(maritalStatus.getId())
                    .maritalStatusName(maritalStatus.getMaritalStatusName())
                    .build();
            response.setMessage("marital status updated  successfully");
            response.setData(maritalStatusDto);
            response.setSuccess(true);
        }

        return response;
    }

    @Override
    public boolean existsMaritalStatusName(String name) {
        return maritalStatusRepository.existsMaritalStatusByMaritalStatusNameIgnoreCase(name);
    }


}
