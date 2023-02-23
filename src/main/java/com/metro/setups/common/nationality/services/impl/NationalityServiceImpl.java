package com.metro.setups.common.nationality.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.common.nationality.dtos.NationalityDto;
import com.metro.setups.common.nationality.repositories.NationalityRepository;
import com.metro.setups.common.nationality.services.NationalityService;
import com.metro.setups.common.nationality.specifications.Nationality;
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
public class NationalityServiceImpl implements NationalityService {

    private final NationalityRepository nationalityRepository;

    @Override
    public ApiResponse createNationality(NationalityDto nationalityDto) {
        log.info("Saving nationality{}", nationalityDto.getNationalityName());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the nationality{")
                .success(false)
                .data(nationalityDto)
                .build();
        if (existsByNationalityName(nationalityDto.getNationalityName())) {
            throw new DuplicateResourceException(
                    "nationality is existing"
            );
        }
        Nationality nationality = Nationality.builder()
                .nationalityName(nationalityDto.getNationalityName())
                .build();

        nationality = nationalityRepository.save(nationality);
        if (StringUtils.isNotEmpty(nationality.getId().toString())) {
            nationalityDto.setNationalityId(Long.valueOf(nationality.getId().longValue()));
            response.setMessage("nationality inserted  successfully");
            response.setData(nationalityDto);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse editNationality(Long id, NationalityDto nationalityDto) {
        log.info("updating nationality{ {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update nationality ")
                .success(false)
                .data(nationalityDto)
                .build();

        Nationality nationality = nationalityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "ethnic nationality not found with id  " + id));

        if (existsByNationalityName(nationalityDto.getNationalityName())) {
            throw new DuplicateResourceException(
                    "nationality  is existing"
            );
        }

        nationality.setNationalityName(nationalityDto.getNationalityName());
        nationality = nationalityRepository.save(nationality);

        if (StringUtils.isNotEmpty(nationality.getId().toString())) {
            nationalityDto.setNationalityId(Long.valueOf(nationality.getId().longValue()));
            response.setMessage("nationality group updated  successfully");
            response.setData(nationalityDto);
            response.setSuccess(true);
        }

        return response;
    }

    @Override
    public ApiResponse getAllNationalities() {
        log.info("fetching nationality {}");
        List<NationalityDto> nationalityDtos = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(nationalityDtos)
                .message("No record is existing")
                .build();

        List<Nationality> nationalityList = nationalityRepository.findAll(Sort.by("nationalityName").ascending());
        if (!nationalityList.isEmpty()) {
            nationalityDtos.addAll(nationalityList.stream().map(e -> {
                NationalityDto nationalityDto = NationalityDto.builder()
                        .nationalityName(e.getNationalityName())
                        .nationalityId(e.getId())
                        .build();
                return nationalityDto;
            }).toList());
            response.setMessage("nationalities  fetched  successfully");
            response.setData(nationalityDtos);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse deleteNationality(Long id) {
        return null;
    }

    @Override
    public ApiResponse selectNationalityByName(String name) {
        log.info("fetching  nationality by name  {}");
        List<NationalityDto> nationalityDtos = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(nationalityDtos)
                .message("No record is existing")
                .build();

        List<Nationality> nationalityList = nationalityRepository.findByNationalityNameContainingIgnoreCase(name.trim());
        if (!nationalityList.isEmpty()) {
            nationalityDtos.addAll(nationalityList.stream().map(e -> {
                NationalityDto nationalityDto = NationalityDto.builder()
                        .nationalityName(e.getNationalityName())
                        .nationalityId(e.getId())
                        .build();
                return nationalityDto;
            }).toList());
            response.setMessage("nationalities  fetched  successfully");
            response.setData(nationalityDtos);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse selectByNationalityId(Long id) {
        log.info("selecting nationalities {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch nationalities status ")
                .success(false)
                .build();
        Nationality nationality = nationalityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "nationalities not found with id  " + id));
        if (StringUtils.isNotEmpty(nationality.getId().toString())) {
            NationalityDto nationalityDto = NationalityDto.builder()
                    .nationalityName(nationality.getNationalityName())
                    .nationalityId(nationality.getId())
                    .build();
            response.setMessage(" nationalities fetched successfully");
            response.setData(nationalityDto);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public boolean existsByNationalityName(String name) {
        return nationalityRepository.existsNationalityByNationalityNameContainingIgnoreCase(name);
    }
}
