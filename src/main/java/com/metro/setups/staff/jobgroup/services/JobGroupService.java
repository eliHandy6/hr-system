package com.metro.setups.staff.jobgroup.services;

import com.metro.core.ApiResponse;
import com.metro.setups.staff.jobgroup.dtos.JobGroupDto;


/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface JobGroupService {

    ApiResponse createJobGroup(JobGroupDto jobGroupDto);

    ApiResponse updateJobGroup(Long id, JobGroupDto jobGroupDto);

    ApiResponse getJobGroups();

    ApiResponse deleteJobGroups(Long id);

    ApiResponse selectJobGroupByName(String name);

    ApiResponse selectJobGroupById(Long id);

    boolean existsJobGroupByName(String name);
}
