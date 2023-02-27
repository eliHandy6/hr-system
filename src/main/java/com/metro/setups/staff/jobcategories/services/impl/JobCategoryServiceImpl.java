package com.metro.setups.staff.jobcategories.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.EmptySpaceExceptionHandler;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.staff.jobcategories.dtos.JobCategoryDTO;
import com.metro.setups.staff.jobcategories.entities.JobCategory;
import com.metro.setups.staff.jobcategories.repositories.JobCategoryRepositories;
import com.metro.setups.staff.jobcategories.services.JobCategoryService;
import com.metro.setups.staff.jobsubcategories.entities.JobSubCategories;
import com.metro.setups.staff.jobsubcategories.repositories.JobSubCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
 **/
@Service
@Slf4j
public class JobCategoryServiceImpl implements JobCategoryService {
    private final JobCategoryRepositories jobCategoryRepositories;
    private final JobSubCategoryRepository jobSubCategoryRepository;

    public JobCategoryServiceImpl(JobCategoryRepositories jobCategoryRepositories, JobSubCategoryRepository jobSubCategoryRepository) {
        this.jobCategoryRepositories = jobCategoryRepositories;
        this.jobSubCategoryRepository = jobSubCategoryRepository;
    }

    @Override
    public ApiResponse createJobCategory(JobCategoryDTO jobCategoryDTO) {
        log.info("Creating job category {}" + jobCategoryDTO.getName());
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to create job category")
                .success(false)
                .data(jobCategoryDTO)
                .build();
        String name = jobCategoryDTO.getName();
        if ((name.trim()).length() == 0) throw new EmptySpaceExceptionHandler("Name of the department cannot be empty");
        if (jobCategoryRepositories.findJobCategoriesByNameIgnoreCase(jobCategoryDTO.getName()).size() != 0) {
            throw new DuplicateResourceException("job category " + name + " already exists in the database try another");
        }
        JobCategory jobCategory = JobCategory.builder()
                .name(jobCategoryDTO.getName())
                .build();
        jobCategory = jobCategoryRepositories.save(jobCategory);
        JobCategoryDTO jobCategoryDTO1 = JobCategoryDTO.builder()
                .jobCategoryId(jobCategory.getId())
                .name(jobCategory.getName())
                .build();
        apiResponse.setData(jobCategoryDTO1);
        apiResponse.setMessage("Created Job Category Successfully");
        apiResponse.setSuccess(true);
        return apiResponse;
    }

    @Override
    public ApiResponse updateJobCategory(Long id, JobCategoryDTO jobCategoryDTO) {
        log.info("Updating job category with id {}" + id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to update job category")
                .success(false)
                .data(jobCategoryDTO)
                .build();
        JobCategory jobCategory = jobCategoryRepositories.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job Category with id " + id + " not found"));
        jobCategory.setName(jobCategoryDTO.getName());
        jobCategory = jobCategoryRepositories.save(jobCategory);
        jobCategoryDTO.setJobCategoryId((Long) jobCategory.getId());
        apiResponse.setData(jobCategoryDTO);
        apiResponse.setMessage("Updated Job Category Successfully");
        apiResponse.setSuccess(true);
        return apiResponse;
    }

    @Override
    public ApiResponse getJobCategory() {
        log.info("getting all the job categories ...... {}");
        List<JobCategoryDTO> jobCategoryDTOS = new ArrayList<>();
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to fetch all the job categories")
                .success(false)
                .data(jobCategoryDTOS)
                .build();
        List<JobCategory> list = jobCategoryRepositories.findAll(Sort.by("name").ascending());
        if (!list.isEmpty()) {
            jobCategoryDTOS.addAll(list.stream().map(e -> {
                JobCategoryDTO jobCategoryDTO = JobCategoryDTO.builder()
                        .jobCategoryId(e.getId())
                        .name(e.getName())
                        .build();
                return jobCategoryDTO;
            }).toList());
            apiResponse.setMessage("Retrieved all the job Categories Successfully");
            apiResponse.setSuccess(true);
            apiResponse.setData(jobCategoryDTOS);
        }
        return apiResponse;
    }

    @Override
    public ApiResponse selectJobCategoryByName(String name) {
        List<JobCategoryDTO> jobCategoryDTOS = new ArrayList<>();
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to get Job Category by name")
                .success(false)
                .data(null)
                .build();
        List<JobCategory> jobCategories = jobCategoryRepositories.findJobCategoriesByNameIgnoreCase(name);
        if (!jobCategories.isEmpty()) {
            jobCategoryDTOS.addAll(jobCategories.stream().map(e -> {
                JobCategoryDTO jobCategoryDTO = JobCategoryDTO.builder()
                        .name(e.getName())
                        .jobCategoryId(e.getId())
                        .build();
                return jobCategoryDTO;
            }).toList());

            apiResponse.setData(jobCategoryDTOS);
            apiResponse.setMessage("Completed Search successfully");
            apiResponse.setSuccess(true);

        }
        return apiResponse;
    }

    @Override
    public ApiResponse selectJobCategoryByID(Long id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch the Job category")
                .success(false)
                .build();

        JobCategory jobCategory = jobCategoryRepositories.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "section with id not found  " + id));
        if (StringUtils.isNotEmpty(jobCategory.getId().toString())) {
            JobCategoryDTO jobCategoryDTO = JobCategoryDTO.builder()
                    .name(jobCategory.getName())
                    .jobCategoryId(jobCategory.getId())
                    .build();
            response.setMessage("Job category fetched  successfully");
            response.setData(jobCategoryDTO);
            response.setSuccess(true);
        }
        return response;
    }
    //one remaining api;
    @Override
    public ApiResponse getJobSubCategoryByJobId(Long id) {
        log.info("getting all jobSubCategories for Job Categories ...... {}");
        List<JobCategoryDTO> jobCategoryDTOS = new ArrayList<>();
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to fetch all ")
                .success(false)
                .data(jobCategoryDTOS)
                .build();
        List<JobSubCategories> list = jobSubCategoryRepository.getJobSubCategoriesByJobCategory_Id(id);
        list.sort(Comparator.comparing(JobSubCategories::getName));
        if (!list.isEmpty()) {
            jobCategoryDTOS.addAll(list.stream().map(e -> {
                JobCategoryDTO jobCategoryDTO = JobCategoryDTO.builder()
                        .jobSubcategoryId(e.getId())
                        .name(e.getName())
                        .jobCategoryId(e.getJobCategory().getId())
                        .build();
                return jobCategoryDTO;
            }).toList());
            apiResponse.setData(jobCategoryDTOS);
            apiResponse.setMessage("Completed Retrieving");
            apiResponse.setSuccess(true);
        }
        return apiResponse;
    }

    @Override
    public boolean jobCategoryExists(String name) {
        return jobCategoryRepositories.existsJobCategoriesByNameIgnoreCase(name);
    }
}
