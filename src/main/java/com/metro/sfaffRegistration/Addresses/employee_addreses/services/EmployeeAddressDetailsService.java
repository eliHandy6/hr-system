package com.metro.sfaffRegistration.Addresses.employee_addreses.services;

import com.metro.core.ApiResponse;
import com.metro.sfaffRegistration.Addresses.employee_addreses.dtos.EmployeeAddressDetailsDTO;

public interface EmployeeAddressDetailsService {
    ApiResponse createEmployeeAddressDetails(EmployeeAddressDetailsDTO employeeAddressDetailsDTO, Long id);

    ApiResponse updateEmployeeAddressDetails(Long id, EmployeeAddressDetailsDTO employeeAddressDetailsDTO, Long address_id);

    ApiResponse selectEmployeeAddressDetailsByID(Long id);
}
