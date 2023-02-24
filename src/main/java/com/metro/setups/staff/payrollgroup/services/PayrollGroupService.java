package com.metro.setups.staff.payrollgroup.services;

import com.metro.core.ApiResponse;
import com.metro.setups.staff.payrollgroup.dtos.PayrollGroupDto;


/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface PayrollGroupService {

    ApiResponse createPayrollGroup(PayrollGroupDto payrollGroupDto);

    ApiResponse updatePayrollGroup(Long id, PayrollGroupDto payrollGroupDto);

    ApiResponse getPayrollGroups();

    ApiResponse deletePayrollGroup(Long id);

    ApiResponse selectPayrollGroupByName(String name);

    ApiResponse selectPayrollGroupById(Long id);

    boolean existsPayrollGroupByName(String name);
}
