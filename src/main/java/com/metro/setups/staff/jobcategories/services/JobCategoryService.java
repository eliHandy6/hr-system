package com.metro.setups.staff.jobcategories.services;

import com.metro.core.ApiResponse;
import com.metro.setups.staff.jobcategories.dtos.JobCategoryDTO;
import org.springframework.stereotype.Service;
/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
 **/
@Service
public interface JobCategoryService {
    ApiResponse createJobCategory(JobCategoryDTO jobCategoryDTO);

    ApiResponse updateJobCategory(Long id,  JobCategoryDTO jobCategoryDTO);

    ApiResponse getJobCategory();

    ApiResponse selectJobCategoryByName(String name);

    ApiResponse selectJobCategoryByID(Long id);
    ApiResponse getJobSubCategoryByJobId(Long id);
    boolean jobCategoryExists(String name);
}
