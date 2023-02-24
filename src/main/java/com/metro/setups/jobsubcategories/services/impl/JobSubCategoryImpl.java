package com.metro.setups.jobsubcategories.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.jobcategories.entities.JobCategory;
import com.metro.setups.jobcategories.repositories.JobCategoryRepositories;
import com.metro.setups.jobsubcategories.dtos.JobSubCategoryDTOS;
import com.metro.setups.jobsubcategories.entities.JobSubCategories;
import com.metro.setups.jobsubcategories.repositories.JobSubCategoryRepository;
import com.metro.setups.jobsubcategories.services.JobSubCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class JobSubCategoryImpl implements JobSubCategoryService {
    private final JobSubCategoryRepository jobSubCategoryRepository;
    private final JobCategoryRepositories jobCategoryRepositories;

    public JobSubCategoryImpl(JobSubCategoryRepository jobSubCategoryRepository, JobCategoryRepositories jobCategoryRepositories) {
        this.jobSubCategoryRepository = jobSubCategoryRepository;
        this.jobCategoryRepositories = jobCategoryRepositories;
    }

    @Override
    public ApiResponse createJobSubCategory(JobSubCategoryDTOS jobSubCategoryDTOS) {
        log.info("Creating job sub-category {}" + jobSubCategoryDTOS.getName());
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to create job sub-category")
                .success(false)
                .data(jobSubCategoryDTOS)
                .build();
        if (jobSubCategoryExists(jobSubCategoryDTOS.getName())) {
            throw new DuplicateResourceException(
                    "job sub-category already exists"
            );
        }
        JobCategory jobCategory = jobCategoryRepositories.findById(jobSubCategoryDTOS.getJobCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Job Category with id " + jobSubCategoryDTOS.getJobCategoryId() + " not found"
                ));
        JobSubCategories jobSubCategories = JobSubCategories.builder()
                .name(jobSubCategoryDTOS.getName())
                .description(jobSubCategoryDTOS.getName())
                .jobCategory(jobCategory)
                .build();
        jobSubCategories = jobSubCategoryRepository.save(jobSubCategories);
        jobSubCategoryDTOS.setJobCategoryId(jobSubCategories.getJobCategory().getId());
        jobSubCategoryDTOS.setJobSubCategoryId(jobSubCategories.getId());
        apiResponse.setData(jobSubCategoryDTOS);
        apiResponse.setMessage("Created Job Sub-Category Successfully");
        apiResponse.setSuccess(true);
        return apiResponse;
    }

    @Override
    public ApiResponse updateJobSubCategory(Long id, JobSubCategoryDTOS jobSubCategoryDTOS) {
        log.info("Updating job sub category with id {}" + id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to update job category")
                .success(false)
                .data(jobSubCategoryDTOS)
                .build();
        JobSubCategories jobSubCategories = jobSubCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job Sub-Category with id " + id + " not found"));
        jobSubCategories.setName(jobSubCategoryDTOS.getName());
        jobSubCategories.setDescription(jobSubCategoryDTOS.getDesctription());
        jobSubCategories = jobSubCategoryRepository.save(jobSubCategories);
        jobSubCategoryDTOS.setJobSubCategoryId((Long) jobSubCategories.getId());
        apiResponse.setData(jobSubCategoryDTOS);
        apiResponse.setMessage("Updated Job Sub-Category Successfully");
        apiResponse.setSuccess(true);
        return apiResponse;
    }

    @Override
    public ApiResponse getJobSubCategory() {
        log.info("getting all the job sub categories ...... {}");
        List<JobSubCategoryDTOS> jobCategoryDTOS = new ArrayList<>();
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to fetch all the job sub categories")
                .success(false)
                .data(jobCategoryDTOS)
                .build();
        List<JobSubCategories> list = jobSubCategoryRepository.findAll(Sort.by("name").ascending());
        if (!list.isEmpty()) {
            jobCategoryDTOS.addAll(list.stream().map(e -> {
                JobSubCategoryDTOS jobSubCategoryDTOS= JobSubCategoryDTOS.builder()
                        .jobSubCategoryId(e.getId())
                        .name(e.getName())
                        .desctription(e.getDescription())
                        .build();
                return jobSubCategoryDTOS;
            }).toList());
            apiResponse.setMessage("Retrieved all the job Sub-Categories Successfully");
            apiResponse.setSuccess(true);
            apiResponse.setData(jobCategoryDTOS);
        }
        return apiResponse;
    }

    @Override
    public ApiResponse selectJobSubCategoryByName(String name) {
        List<JobSubCategoryDTOS> jobCategoryDTOS = new ArrayList<>();
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to get Job Sub-Category by name")
                .success(false)
                .data(null)
                .build();
        List<JobSubCategories> jobCategories = jobSubCategoryRepository.findJobSubCategoriesByNameIgnoreCase(name);
        if (!jobCategories.isEmpty()) {
            jobCategoryDTOS.addAll(jobCategories.stream().map(e -> {
                JobSubCategoryDTOS jobSubCategoryDTOS = JobSubCategoryDTOS.builder()
                        .name(e.getName())
                        .desctription(e.getDescription())
                        .jobSubCategoryId(e.getId())
                        .build();
                return jobSubCategoryDTOS;
            }).toList());

            apiResponse.setData(jobCategoryDTOS);
            apiResponse.setMessage("Completed Search successfully");
            apiResponse.setSuccess(true);

        }
        return apiResponse;
    }

    @Override
    public ApiResponse selectJobSubCategoryByID(Long id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch the Job Sub-category")
                .success(false)
                .build();

        JobSubCategories jobSubCategories = jobSubCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "section with id not found  " + id));
        if (StringUtils.isNotEmpty(jobSubCategories.getId().toString())) {
            JobSubCategoryDTOS jobSubCategoryDTOS = JobSubCategoryDTOS.builder()
                    .name(jobSubCategories.getName())
                    .jobSubCategoryId(jobSubCategories.getId())
                    .desctription(jobSubCategories.getDescription())
                    .build();
            response.setMessage("Job category fetched  successfully");
            response.setData(jobSubCategoryDTOS);
            response.setSuccess(true);
        }
        return response;
    }


    @Override
    public boolean jobSubCategoryExists(String name) {
        return jobSubCategoryRepository.existsSectionByNameIgnoreCase(name);
    }
}
