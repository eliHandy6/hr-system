package com.metro.sfaffRegistration.Addresses.emergency_details.services;

import com.metro.core.ApiResponse;
import com.metro.sfaffRegistration.Addresses.emergency_details.dtos.EmergencyContactDetailsDTO;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;

public interface EmergencyContactDetailsService {

    ApiResponse createEmergencyContactDetails(EmergencyContactDetailsDTO emergencyContactDetailsDTO, Long id);
    ApiResponse updateEmergencyContactDetails(Long id, EmergencyContactDetailsDTO emergencyContactDetailsDTO, Long contact_id);
    ApiResponse selectStaffEmergencyContactDetailsByID(Long id);
}
