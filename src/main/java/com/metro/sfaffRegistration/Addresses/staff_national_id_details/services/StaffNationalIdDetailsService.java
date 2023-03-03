package com.metro.sfaffRegistration.Addresses.staff_national_id_details.services;

import com.metro.core.ApiResponse;
import com.metro.sfaffRegistration.Addresses.staff_national_id_details.dto.StaffNationalIdDetailsDTO;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;

public interface StaffNationalIdDetailsService {
    ApiResponse createStaffNationalIdDetailsDetails(StaffNationalIdDetailsDTO staffNationalIdDetailsDTO, Long id);
    ApiResponse updateStaffNationalIdDetailsDetails(Long id, StaffNationalIdDetailsDTO staffNationalIdDetailsDTO);
    ApiResponse selectStaffNationalIdDetailsDetailsByID(Long id);
    boolean staffNationalIdDetailsDetailsExists(Employee employee);
}
