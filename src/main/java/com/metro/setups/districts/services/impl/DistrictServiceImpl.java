package com.metro.setups.districts.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.districts.dtos.DistrictDTO;
import com.metro.setups.districts.entities.District;
import com.metro.setups.districts.repositories.DistrictRepository;
import com.metro.setups.districts.services.DistrictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
 **/
@Service
@Slf4j
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepository;

    public DistrictServiceImpl(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @Override
    public ApiResponse createDistrict(DistrictDTO districtDTO) {
        log.info("Saving district {}", districtDTO.getName());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the district")
                .success(false)
                .data(districtDTO)
                .build();
        if (districtExists(districtDTO.getName())) {
            throw new DuplicateResourceException(
                    "district is already existing"
            );
        }
        District district = District.builder()
                .name(districtDTO.getName())
                .build();
        district = districtRepository.save(district);
        if (StringUtils.isNotEmpty(district.getId().toString())) {
            districtDTO.setDistrictId(Long.valueOf(district.getId().longValue()));
            response.setMessage("district created successfully");
            response.setData(districtDTO);
            response.setSuccess(true);
        }
        return  response;
    }

    @Override
    public ApiResponse updateDistrict(Long id, DistrictDTO districtDTO) {
        log.info("updating district with id {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update the category")
                .success(false)
                .data(districtDTO)
                .build();
        District district = districtRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "District with id "+  id + " not found"));
        district.setName(districtDTO.getName());
        district = districtRepository.save(district);

        if (StringUtils.isNotEmpty(district.getId().toString())) {
            districtDTO.setDistrictId(Long.valueOf(district.getId().longValue()));
            response.setMessage("District updated  successfully");
            response.setData(districtDTO);
            response.setSuccess(true);
        }

        return response;
    }

    @Override
    public ApiResponse getDistricts() {
        log.info("getting all districts ...... {}");
        List<DistrictDTO> districtDTOS = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(districtDTOS)
                .message("No Districts existing")
                .build();

        List<District> districts = districtRepository.findAll(Sort.by("name").ascending());
        if (!districts.isEmpty()) {
            districtDTOS.addAll(districts.stream().map(e -> {
                DistrictDTO districtDTO = DistrictDTO.builder()
                        .name(e.getName())
                        .districtId(e.getId())
                        .build();
                return districtDTO;
            }).toList());
            response.setMessage("All Districts fetched successfully");
            response.setData(districtDTOS);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse selectDistrictByName(String name) {
        log.info("selecting Districts  by name {}", name);
        List<DistrictDTO> districtDTOS = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .message("No record of district is existing")
                .success(false)
                .data(districtDTOS)
                .build();
        List<District> districtList= districtRepository.findDistrictByNameIgnoreCase(name);
        if (!districtList.isEmpty()) {
            districtDTOS.addAll(districtList.stream().map(e -> {
                DistrictDTO districtDTO = DistrictDTO.builder()
                        .name(e.getName())
                        .districtId(e.getId())
                        .build();
                return districtDTO;
            }).toList());
            response.setMessage("retrieved districts successfully");
            response.setData(districtDTOS);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse selectDistrictByID(Long id) {
        log.info("selecting district by id {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch the district")
                .success(false)
                .build();

        District district = districtRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "district with id " +id+ " not found."));

        if (StringUtils.isNotEmpty(district.getId().toString())) {
            DistrictDTO districtDTO = DistrictDTO.builder()
                    .districtId(district.getId())
                    .name(district.getName())
                    .build();
            districtDTO.setDistrictId(Long.valueOf(district.getId().longValue()));
            response.setMessage("District fetched  successfully");
            response.setData(districtDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public boolean districtExists(String name) {
        return districtRepository.existsDistrictByNameIgnoreCase(name);
    }
}
