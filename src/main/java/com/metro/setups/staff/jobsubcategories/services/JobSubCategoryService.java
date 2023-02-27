package com.metro.setups.staff.jobsubcategories.services;

import com.metro.core.ApiResponse;
import com.metro.setups.staff.jobsubcategories.dtos.JobSubCategoryDTOS;

/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
 **/
public interface JobSubCategoryService {
    ApiResponse createJobSubCategory(JobSubCategoryDTOS jobSubCategoryDTOS);

    ApiResponse updateJobSubCategory(Long id, JobSubCategoryDTOS jobSubCategoryDTOS);

    ApiResponse getJobSubCategory();

    ApiResponse selectJobSubCategoryByName(String name);

    ApiResponse selectJobSubCategoryByID(Long id);

    boolean jobSubCategoryExists(String name);
}
