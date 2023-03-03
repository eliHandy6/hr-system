package com.metro.sfaffRegistration.Addresses.staff_national_id_details.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.districts.entities.District;
import com.metro.setups.districts.repositories.DistrictRepository;
import com.metro.sfaffRegistration.Addresses.staff_national_id_details.StaffNationalIdDetails;
import com.metro.sfaffRegistration.Addresses.staff_national_id_details.dto.StaffNationalIdDetailsDTO;
import com.metro.sfaffRegistration.Addresses.staff_national_id_details.repositories.NationalIdDetailsrepository;
import com.metro.sfaffRegistration.Addresses.staff_national_id_details.services.StaffNationalIdDetailsService;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.biodata.employeetest.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StaffNationalIdDetailsServiceImpl implements StaffNationalIdDetailsService {
    private final DistrictRepository districtRepository;
    private final NationalIdDetailsrepository nationalIdDetailsrepository;
    private final EmployeeRepository employeeRepository;

    public StaffNationalIdDetailsServiceImpl(DistrictRepository districtRepository, NationalIdDetailsrepository nationalIdDetailsrepository, EmployeeRepository employeeRepository) {
        this.districtRepository = districtRepository;

        this.nationalIdDetailsrepository = nationalIdDetailsrepository;

        this.employeeRepository = employeeRepository;
    }

    @Override
    public ApiResponse createStaffNationalIdDetailsDetails(StaffNationalIdDetailsDTO staffNationalIdDetailsDTO, Long id) {
        log.info("Creating National Id Details for Employee with Id ", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save Id Details for employee")
                .success(false)
                .data(staffNationalIdDetailsDTO)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        if (staffNationalIdDetailsDetailsExists(employee))
            throw new DuplicateResourceException("Id details of employee exists Try updating");
        Long district_id = staffNationalIdDetailsDTO.getDistrict_id();
        District district = districtRepository.findById(district_id).orElseThrow(() -> new ResourceNotFoundException(
                "district with id " + district_id + " not found."));
        StaffNationalIdDetails staffNationalIdDetails = StaffNationalIdDetails.builder()
                .district(district)
                .divisionName(staffNationalIdDetailsDTO.getDivision())
                .locationName(staffNationalIdDetailsDTO.getLocation())
                .employee(employee)
                .build();

        staffNationalIdDetails = nationalIdDetailsrepository.save(staffNationalIdDetails);
        if (StringUtils.isNotEmpty(staffNationalIdDetails.getId().toString())) {
            staffNationalIdDetailsDTO.setId(Long.valueOf(staffNationalIdDetails.getId().longValue()));
            staffNationalIdDetailsDTO.setEmployee_id(staffNationalIdDetails.getEmployee().getId());
            response.setMessage("Successfully Saved the Id Details of the Employee");
            response.setData(staffNationalIdDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateStaffNationalIdDetailsDetails(Long id, StaffNationalIdDetailsDTO staffNationalIdDetailsDTO) {
        log.info("Updating ID details with ID : ", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update ID details for employee")
                .success(false)
                .data(staffNationalIdDetailsDTO)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        Long district_id = staffNationalIdDetailsDTO.getDistrict_id();
        District district = districtRepository.findById(district_id).orElseThrow(() -> new ResourceNotFoundException(
                "district with id " + district_id + " not found."));
        StaffNationalIdDetails staffNationalIdDetails = nationalIdDetailsrepository.findStaffNationalIdDetailsByEmployee(employee);
        staffNationalIdDetails.setDistrict(district);
        staffNationalIdDetails.setDivisionName(staffNationalIdDetailsDTO.getDivision());
        staffNationalIdDetails.setLocationName(staffNationalIdDetailsDTO.getLocation());
        staffNationalIdDetails = nationalIdDetailsrepository.save(staffNationalIdDetails);
        if (StringUtils.isNotEmpty(staffNationalIdDetails.getId().toString())) {
            staffNationalIdDetailsDTO.setId(Long.valueOf(staffNationalIdDetails.getId().longValue()));
            staffNationalIdDetailsDTO.setEmployee_id(staffNationalIdDetails.getEmployee().getId());
            response.setMessage("Successfully Updated the Id Details");
            response.setData(staffNationalIdDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse selectStaffNationalIdDetailsDetailsByID(Long id) {
        log.info("Selecting Id details for Staff ID : ", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch Id details for employee")
                .success(false)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        StaffNationalIdDetails staffNationalIdDetails = nationalIdDetailsrepository.findStaffNationalIdDetailsByEmployee(employee);
        if (StringUtils.isNotEmpty(staffNationalIdDetails.getId().toString())){
            StaffNationalIdDetailsDTO emergencyContactDetailsDTO = StaffNationalIdDetailsDTO.builder()
                    .Id(staffNationalIdDetails.getId())
                    .location(staffNationalIdDetails.getLocationName())
                    .division(staffNationalIdDetails.getDivisionName())
                    .district_id(staffNationalIdDetails.getDistrict().getId())
                    .build();
            response.setSuccess(true);
            response.setMessage("Retrieved all Id Details by Employee ID");
            response.setData(emergencyContactDetailsDTO);
        }
        return response;
    }

    @Override
    public boolean staffNationalIdDetailsDetailsExists(Employee employee) {

        return nationalIdDetailsrepository.existsStaffNationalIdDetailsByEmployee(employee);
    }
}
