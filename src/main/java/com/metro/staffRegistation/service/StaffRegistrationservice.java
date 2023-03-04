package com.metro.staffRegistation.service;

import com.metro.core.ApiResponse;
import com.metro.setups.staff.payrollgroup.dtos.PayrollGroupDto;
import com.metro.staffRegistation.dto.StaffRegistationDetailsDto;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface StaffRegistrationservice {

    ApiResponse createStaff(StaffRegistationDetailsDto staffRegistationDetailsDto);
}
