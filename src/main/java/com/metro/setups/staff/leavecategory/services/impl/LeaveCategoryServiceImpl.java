package com.metro.setups.staff.leavecategory.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.staff.leavecategory.dtos.LeaveCategoryDto;
import com.metro.setups.staff.leavecategory.repositories.LeaveCategoryRepository;
import com.metro.setups.staff.leavecategory.services.LeaveCategoryService;
import com.metro.setups.staff.leavecategory.specifications.LeaveCategory;
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
public class LeaveCategoryServiceImpl implements LeaveCategoryService {

    private final LeaveCategoryRepository leaveCategoryRepository;

    @Override
    public ApiResponse createLeaveCategory(LeaveCategoryDto leaveCategoryDto) {
        log.info("Saving leave category{}", leaveCategoryDto.getLeaveCategoryName());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the leave category")
                .success(false)
                .data(leaveCategoryDto)
                .build();
        if (existsLeaveCategoryByName(leaveCategoryDto.getLeaveCategoryName())) {
            throw new DuplicateResourceException(
                    "leave category is existing"
            );
        }
        LeaveCategory leaveCategory = LeaveCategory.builder()
                .leaveCategoryName(leaveCategoryDto.getLeaveCategoryName())
                .build();

        leaveCategory = leaveCategoryRepository.save(leaveCategory);
        if (StringUtils.isNotEmpty(leaveCategory.getId().toString())) {
            leaveCategoryDto.setLeaveCategoryId(Long.valueOf(leaveCategory.getId().longValue()));
            response.setMessage("leave category inserted  successfully");
            response.setData(leaveCategoryDto);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateLeaveCategory(Long id, LeaveCategoryDto leaveCategoryDto) {
        log.info("updating leave category {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update leave category  ")
                .success(false)
                .data(leaveCategoryDto)
                .build();

        LeaveCategory leaveCategory = leaveCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "leave category  not found with id  " + id));

        if (existsLeaveCategoryByName(leaveCategoryDto.getLeaveCategoryName())) {
            throw new DuplicateResourceException(
                    "leave category is existing"
            );
        }

        leaveCategory.setLeaveCategoryName(leaveCategoryDto.getLeaveCategoryName());
        leaveCategory = leaveCategoryRepository.save(leaveCategory);

        if (StringUtils.isNotEmpty(leaveCategory.getId().toString())) {
            leaveCategoryDto.setLeaveCategoryId(Long.valueOf(leaveCategory.getId().longValue()));
            response.setMessage("leave category  updated  successfully");
            response.setData(leaveCategoryDto);
            response.setSuccess(true);
        }

        return response;
    }

    @Override
    public ApiResponse getLeaveCategories() {
        log.info("fetching  ethnic groups categories {}");
        List<LeaveCategoryDto> categoryDtoArrayList = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(categoryDtoArrayList)
                .message("No record is existing")
                .build();

        List<LeaveCategory> leaveCategoryList = leaveCategoryRepository.findAll(Sort.by("leaveCategoryName").ascending());
        if (!leaveCategoryList.isEmpty()) {
            categoryDtoArrayList.addAll(leaveCategoryList.stream().map(e -> {
                LeaveCategoryDto leaveCategoryDto = LeaveCategoryDto.builder()
                        .leaveCategoryName(e.getLeaveCategoryName())
                        .leaveCategoryId(e.getId())
                        .build();
                return leaveCategoryDto;
            }).toList());
            response.setMessage("leave category fetched  successfully");
            response.setData(categoryDtoArrayList);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse deleteLeaveCategory(Long id) {
        return null;
    }

    @Override
    public ApiResponse selectLeaveCategoryByName(String name) {
        log.info("fetching  leave category  {}");
        List<LeaveCategoryDto> leaveCategoryDtos = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(leaveCategoryDtos)
                .message("No record is existing")
                .build();

        List<LeaveCategory> leaveCategories = leaveCategoryRepository.findByLeaveCategoryNameContainingIgnoreCase(name.trim());
        if (!leaveCategories.isEmpty()) {
            leaveCategoryDtos.addAll(leaveCategories.stream().map(e -> {
                LeaveCategoryDto leaveCategoryDto = LeaveCategoryDto.builder()
                        .leaveCategoryName(e.getLeaveCategoryName())
                        .leaveCategoryId(e.getId())
                        .build();
                return leaveCategoryDto;
            }).toList());
            response.setMessage("leave category fetched  successfully");
            response.setData(leaveCategoryDtos);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse selectLeaveCategoryByID(Long id) {
        log.info("selecting leave category  {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch leave category  ")
                .success(false)
                .build();
        LeaveCategory leaveCategory = leaveCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "leave category   not found with id  " + id));
        if (StringUtils.isNotEmpty(leaveCategory.getId().toString())) {
            LeaveCategoryDto leaveCategoryDto = LeaveCategoryDto.builder()
                    .leaveCategoryName(leaveCategory.getLeaveCategoryName())
                    .leaveCategoryId(leaveCategory.getId())
                    .build();
            response.setMessage("leave category selected  successfully");
            response.setData(leaveCategoryDto);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public boolean existsLeaveCategoryByName(String name) {
        return leaveCategoryRepository.existsLeaveCategoryByLeaveCategoryNameContainingIgnoreCase(name);
    }
}
