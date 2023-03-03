package com.metro.sfaffRegistration.biodata.employeetest.services;

import com.metro.core.ApiResponse;
import com.metro.sfaffRegistration.biodata.employeetest.dtos.EmployeeDTO;

public interface EmployeeService {
    ApiResponse createEmployee(EmployeeDTO employeeDTO);
    boolean existsByName(String name);
}
