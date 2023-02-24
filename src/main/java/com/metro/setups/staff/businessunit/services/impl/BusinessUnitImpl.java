package com.metro.setups.staff.businessunit.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.staff.businessunit.dtos.BusinessUnitDto;
import com.metro.setups.staff.businessunit.repositories.BusinessUnitRepository;
import com.metro.setups.staff.businessunit.specifications.BusinessUnit;
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
@RequiredArgsConstructor
@Slf4j
public class BusinessUnitImpl implements BusinessUnitService {

    private final BusinessUnitRepository businessUnitRepository;

    @Override
    public ApiResponse createBusinessUnit(BusinessUnitDto businessUnitDto) {
        log.info("Saving business unit {}", businessUnitDto.getBusinessUnitName()
        );
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the business unit")
                .success(false)
                .data(businessUnitDto)
                .build();
        if (existsBusinessUnitByName(businessUnitDto.getBusinessUnitName())) {
            throw new DuplicateResourceException(
                    "business unit is existing"
            );
        }
        BusinessUnit businessUnit = BusinessUnit.builder()
                .businessUnitName(businessUnitDto.getBusinessUnitName())
                .build();

        businessUnit = businessUnitRepository.save(businessUnit);
        if (StringUtils.isNotEmpty(businessUnit.getId().toString())) {
            businessUnitDto.setBusinessUnitId(Long.valueOf(businessUnit.getId().longValue()));
            response.setMessage("business unit inserted  successfully");
            response.setData(businessUnitDto);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateBusinessUnit(Long id, BusinessUnitDto businessUnitDto) {
        log.info("updating business unit {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update business unit ")
                .success(false)
                .data(businessUnitDto)
                .build();

        BusinessUnit businessUnit = businessUnitRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "business unit not found with id  " + id));

        if (existsBusinessUnitByName(businessUnitDto.getBusinessUnitName())) {
            throw new DuplicateResourceException(
                    "business unit is existing"
            );
        }

        businessUnit.setBusinessUnitName(businessUnitDto.getBusinessUnitName());
        businessUnit = businessUnitRepository.save(businessUnit);

        if (StringUtils.isNotEmpty(businessUnit.getId().toString())) {
            businessUnitDto.setBusinessUnitId(Long.valueOf(businessUnit.getId().longValue()));
            response.setMessage("business unit updated  successfully");
            response.setData(businessUnitDto);
            response.setSuccess(true);
        }

        return response;
    }

    @Override
    public ApiResponse getBusinessUnits() {
        log.info("fetching  business units {}");
        List<BusinessUnitDto> businessUnitDtos = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(businessUnitDtos)
                .message("No record is existing")
                .build();

        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll(Sort.by("businessUnitName").ascending());
        if (!businessUnitList.isEmpty()) {
            businessUnitDtos.addAll(businessUnitList.stream().map(e -> {
                BusinessUnitDto businessUnitDto = BusinessUnitDto.builder()
                        .businessUnitName(e.getBusinessUnitName())
                        .businessUnitId(e.getId())
                        .build();
                return businessUnitDto;
            }).toList());
            response.setMessage("business unit fetched  successfully");
            response.setData(businessUnitDtos);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse deleteBusinessUnits(Long id) {
        return null;
    }

    @Override
    public ApiResponse selectBusinessUnitByName(String name) {
        log.info("fetching  business unit  {}");
        List<BusinessUnitDto> businessUnitDtos = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(businessUnitDtos)
                .message("No record is existing")
                .build();

        List<BusinessUnit> businessUnitList = businessUnitRepository.findByBusinessUnitNameContainingIgnoreCase(name.trim());
        if (!businessUnitList.isEmpty()) {
            businessUnitDtos.addAll(businessUnitList.stream().map(e -> {
                BusinessUnitDto businessUnitDto = BusinessUnitDto.builder()
                        .businessUnitName(e.getBusinessUnitName())
                        .businessUnitId(e.getId())
                        .build();
                return businessUnitDto;
            }).toList());
            response.setMessage("business units fetched  successfully");
            response.setData(businessUnitDtos);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse selectBusinessUnitById(Long id) {
        log.info("selecting business units {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch business units ")
                .success(false)
                .build();
        BusinessUnit businessUnit = businessUnitRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "business units  not found with id  " + id));
        if (StringUtils.isNotEmpty(businessUnit.getId().toString())) {
            BusinessUnitDto businessUnitDto = BusinessUnitDto.builder()
                    .businessUnitName(businessUnit.getBusinessUnitName())
                    .businessUnitId(businessUnit.getId())
                    .build();
            response.setMessage("business unit fetched  successfully");
            response.setData(businessUnitDto);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public boolean existsBusinessUnitByName(String name) {
        return businessUnitRepository.existsBusinessUnitByBusinessUnitNameContainingIgnoreCase(name);
    }
}
