package com.metro.staffRegistation.service.impl;

import com.metro.core.ApiResponse;
import com.metro.core.ErrorDto;
import com.metro.setups.common.titles.Entity.Titles;
import com.metro.setups.common.titles.repositories.TitleRepository;
import com.metro.setups.department.repositories.DepartmentRepository;
import com.metro.setups.designation.repository.DesignationRepository;

import com.metro.setups.sections.respositories.SectionRepository;
import com.metro.setups.staff.businessunit.repositories.BusinessUnitRepository;
import com.metro.setups.staff.category.repositories.StaffCategoryRepository;
import com.metro.setups.staff.category.specifications.StaffCategory;
import com.metro.setups.staff.jobcategories.entities.JobCategory;
import com.metro.setups.staff.jobcategories.repositories.JobCategoryRepositories;
import com.metro.setups.staff.jobgroup.repositories.JobGroupRepository;
import com.metro.setups.staff.jobsubcategories.repositories.JobSubCategoryRepository;
import com.metro.setups.staff.leavecategory.repositories.LeaveCategoryRepository;
import com.metro.setups.staff.noticeperiod.repositories.NoticePeriodRepository;
import com.metro.setups.staff.payrollgroup.repositories.PayrollGroupRepository;
import com.metro.staffRegistation.dto.StaffRegistationDetailsDto;
import com.metro.staffRegistation.service.StaffRegistrationservice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;


import java.util.Optional;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class StaffRegistrationImpl implements StaffRegistrationservice {

    private final StaffCategoryRepository staffCategoryRepository;
    private final TitleRepository titleRepository;
    private final JobCategoryRepositories jobCategoryRepositories;
    private final JobSubCategoryRepository jobSubCategoryRepository;
    private final BusinessUnitRepository businessUnitRepository;
    private final NoticePeriodRepository noticePeriodRepository;
    private final PayrollGroupRepository payrollGroupRepository;
    private final DepartmentRepository departmentRepository;
    private final SectionRepository sectionRepository;
    private final DesignationRepository designationRepository;
    private final JobGroupRepository jobGroupRepository;
    private final LeaveCategoryRepository leaveCategoryRepository;


    @Override
    public ApiResponse createStaff(StaffRegistationDetailsDto staffRegistationDetailsDto) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the staff ")
                .success(false)
                .data(staffRegistationDetailsDto)
                .build();

        // JSONArray foundErrors = new JSONArray();


        Optional<StaffCategory> staffCategory = staffCategoryRepository.findById(staffRegistationDetailsDto.getStaffCategoryId());
        Optional<Titles> title = titleRepository.findById(staffRegistationDetailsDto.getTitleId());
        Optional<JobCategory> jobCategory = jobCategoryRepositories.findById(staffRegistationDetailsDto.getJobCategoryId());

        // Optional<JobCategory> j =jobSubCategoryRepository.findById()


//
//        if (!staffCategory.isPresent()) {
//            foundErrors.add(new ErrorDto("Staff category id does not exist" + staffRegistationDetailsDto.getStaffCategoryId()));
//        }
//        if (!title.isPresent()) {
//            foundErrors.add(new ErrorDto("Staff title id does not exist" + staffRegistationDetailsDto.getTitleId()));
//        }
//
//        if (!jobCategory.isPresent()) {
//            foundErrors.add(new ErrorDto("Job category id does not exist" + staffRegistationDetailsDto.getJobCategoryId()));
//        }
//
//
//        if (foundErrors.size() > 0) {
//            response.setErrors(foundErrors);
//            response.setStatus(HttpStatus.BAD_REQUEST);
//            return response;
//        }


        return response;
    }
}
