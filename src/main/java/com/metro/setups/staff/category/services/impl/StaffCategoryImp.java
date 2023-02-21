package com.metro.setups.staff.category.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.staff.category.dtos.StaffCategoryDto;
import com.metro.setups.staff.category.repositories.StaffCategoryRepository;
import com.metro.setups.staff.category.services.StaffCategoryService;
import com.metro.setups.staff.category.specifications.StaffCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffCategoryImp implements StaffCategoryService {

    private final StaffCategoryRepository staffCategoryRepository;


    @Override
    public ApiResponse createStaffCategory(StaffCategoryDto staffCategoryDto) {
        log.info("Saving staff category {}", staffCategoryDto.getCategoryName());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the category")
                .success(false)
                .data(staffCategoryDto)
                .build();
        if (existsStaffCategoryName(staffCategoryDto.getCategoryName())) {
            throw new DuplicateResourceException(
                    "staff category is existing"
            );
        }
        StaffCategory staffCategory = StaffCategory.builder()
                .categoryName(staffCategoryDto.getCategoryName())
                .build();

        staffCategory = staffCategoryRepository.save(staffCategory);
        if (StringUtils.isNotEmpty(staffCategory.getId().toString())) {
            staffCategoryDto.setCategoryId(Long.valueOf(staffCategory.getId().longValue()));
            response.setMessage("staff category inserted  successfully");
            response.setData(staffCategoryDto);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateStaffCategory(Long id, StaffCategoryDto staffCategoryDto) {
        log.info("updating staff category {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update the category")
                .success(false)
                .data(staffCategoryDto)
                .build();

        StaffCategory staffCategory = staffCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "staff category not found with id  " + id));

        staffCategory.setCategoryName(staffCategoryDto.getCategoryName());
        staffCategory = staffCategoryRepository.save(staffCategory);

        if (StringUtils.isNotEmpty(staffCategory.getId().toString())) {
            staffCategoryDto.setCategoryId(Long.valueOf(staffCategory.getId().longValue()));
            response.setMessage("staff category updated  successfully");
            response.setData(staffCategoryDto);
            response.setSuccess(true);
        }

        return response;
    }

    @Override
    public ApiResponse getAllStaffCategory() {
        return null;
    }

    @Override
    public ApiResponse deleteStaffCategoryById(Long id) {
        return null;
    }

    @Override
    public ApiResponse selectStaffCategoryByName(String name) {
        return null;
    }

    @Override
    public ApiResponse selectStaffCategoryByID(Long id) {
        return null;
    }

    @Override
    public boolean existsStaffCategoryName(String name) {
        return staffCategoryRepository.existsStaffCategoryByCategoryNameIgnoreCase(name);
    }
}
