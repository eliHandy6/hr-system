package com.metro.setups.staff.jobgroup.services.Impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.staff.jobgroup.dtos.JobGroupDto;
import com.metro.setups.staff.jobgroup.repositories.JobGroupRepository;
import com.metro.setups.staff.jobgroup.services.JobGroupService;
import com.metro.setups.staff.jobgroup.specifications.JobGroup;
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
public class JobGroupServiceImpl implements JobGroupService {

    private final JobGroupRepository jobGroupRepository;

    @Override
    public ApiResponse createJobGroup(JobGroupDto jobGroupDto) {
        log.info("Saving job group {}", jobGroupDto.getJobGroupName());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the job group")
                .success(false)
                .data(jobGroupDto)
                .build();
        if (existsJobGroupByName(jobGroupDto.getJobGroupName())) {
            throw new DuplicateResourceException(
                    "job group is existing"
            );
        }
        JobGroup jobGroup = JobGroup.builder()
                .jobGroupName(jobGroupDto.getJobGroupName())
                .build();

        jobGroup = jobGroupRepository.save(jobGroup);
        if (StringUtils.isNotEmpty(jobGroup.getId().toString())) {
            jobGroupDto.setJobGroupId(Long.valueOf(jobGroup.getId().longValue()));
            response.setMessage("job group inserted  successfully");
            response.setData(jobGroupDto);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateJobGroup(Long id, JobGroupDto jobGroupDto) {

        log.info("updating job group {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update job group ")
                .success(false)
                .data(jobGroupDto)
                .build();

        JobGroup jobGroup = jobGroupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "job group  not found with id  " + id));

        if (existsJobGroupByName(jobGroupDto.getJobGroupName())) {
            throw new DuplicateResourceException(
                    "job group  is existing"
            );
        }

        jobGroup.setJobGroupName(jobGroupDto.getJobGroupName());
        jobGroup = jobGroupRepository.save(jobGroup);

        if (StringUtils.isNotEmpty(jobGroup.getId().toString())) {
            jobGroupDto.setJobGroupId(Long.valueOf(jobGroup.getId().longValue()));
            response.setMessage("job group updated  successfully");
            response.setData(jobGroupDto);
            response.setSuccess(true);
        }

        return response;
    }

    @Override
    public ApiResponse getJobGroups() {
        log.info("fetching  job group {}");
        List<JobGroupDto> jobGroupDtos = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(jobGroupDtos)
                .message("No record is existing")
                .build();

        List<JobGroup> statusNameList = jobGroupRepository.findAll(Sort.by("jobGroupName").ascending());
        if (!statusNameList.isEmpty()) {
            jobGroupDtos.addAll(statusNameList.stream().map(e -> {
                JobGroupDto jobGroupDto = JobGroupDto.builder()
                        .jobGroupName(e.getJobGroupName())
                        .jobGroupId(e.getId().longValue())
                        .build();
                return jobGroupDto;
            }).toList());
            response.setMessage("job group fetched  successfully");
            response.setData(jobGroupDtos);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse deleteJobGroups(Long id) {
        return null;
    }

    @Override
    public ApiResponse selectJobGroupByName(String name) {

        log.info("fetching  job group {}");
        List<JobGroupDto> jobGroupDtos = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(jobGroupDtos)
                .message("No record is existing")
                .build();

        List<JobGroup> jobGroupList = jobGroupRepository.findByJobGroupNameContainingIgnoreCase(name.trim());
        if (!jobGroupList.isEmpty()) {
            jobGroupDtos.addAll(jobGroupList.stream().map(e -> {
                JobGroupDto jobGroupDto = JobGroupDto.builder()
                        .jobGroupName(e.getJobGroupName())
                        .jobGroupId(e.getId())
                        .build();
                return jobGroupDto;
            }).toList());
            response.setMessage("job group fetched  successfully");
            response.setData(jobGroupDtos);
            response.setSuccess(true);
        }
        return response;

    }

    @Override
    public ApiResponse selectJobGroupById(Long id) {
        log.info("selecting job group {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch job group ")
                .success(false)
                .build();

        JobGroup jobGroup = jobGroupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "job group  not found with id  " + id));

        if (StringUtils.isNotEmpty(jobGroup.getId().toString())) {
            JobGroupDto jobGroupDto = JobGroupDto.builder()
                    .jobGroupName(jobGroup.getJobGroupName())
                    .jobGroupId(jobGroup.getId())
                    .build();
            response.setMessage("job groups fetched  successfully");
            response.setData(jobGroupDto);
            response.setSuccess(true);
        }

        return response;
    }

    @Override
    public boolean existsJobGroupByName(String name) {
        return jobGroupRepository.existsJobGroupByJobGroupNameContainingIgnoreCase(name);
    }


}
