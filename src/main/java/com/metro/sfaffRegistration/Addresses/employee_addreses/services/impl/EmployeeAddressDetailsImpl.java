package com.metro.sfaffRegistration.Addresses.employee_addreses.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.sfaffRegistration.Addresses.emergency_details.dtos.EmergencyContactDetailsDTO;
import com.metro.sfaffRegistration.Addresses.emergency_details.specifications.EmergencyContactDetails;
import com.metro.sfaffRegistration.Addresses.employee_addreses.dtos.EmployeeAddressDetailsDTO;
import com.metro.sfaffRegistration.Addresses.employee_addreses.repositories.EmployeeAddressDetailsRepository;
import com.metro.sfaffRegistration.Addresses.employee_addreses.services.EmployeeAddressDetailsService;
import com.metro.sfaffRegistration.Addresses.employee_addreses.specification.EmployeeAddressDetails;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.biodata.employeetest.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class EmployeeAddressDetailsImpl implements EmployeeAddressDetailsService {
    private  final EmployeeAddressDetailsRepository employeeAddressDetailsRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeAddressDetailsImpl(EmployeeAddressDetailsRepository employeeAddressDetailsRepository, EmployeeRepository employeeRepository) {
        this.employeeAddressDetailsRepository = employeeAddressDetailsRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ApiResponse createEmployeeAddressDetails(EmployeeAddressDetailsDTO employeeAddressDetailsDTO, Long id) {
        log.info("saving address of the employee with email " + employeeAddressDetailsDTO.getPersonalEmail() + " and id " + id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save address details for employee")
                .success(false)
                .data(employeeAddressDetailsDTO)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        EmployeeAddressDetails employeeAddressDetails = EmployeeAddressDetails.builder()
                .postalAddress(employeeAddressDetailsDTO.getPostalAddress())
                .personalEmail(employeeAddressDetailsDTO.getPersonalEmail())
                .employee(employee)
                .secondaryMobileNumber(employeeAddressDetailsDTO.getSecondaryMobileNumber())
                .primaryMobileNumber(employeeAddressDetailsDTO.getPrimaryMobileNumber())
                .postalCode(employeeAddressDetailsDTO.getPostalCode())
                .build();
        employeeAddressDetails = employeeAddressDetailsRepository.save(employeeAddressDetails);
        if (StringUtils.isNotEmpty(employeeAddressDetails.getId().toString())) {
            employeeAddressDetailsDTO.setId(Long.valueOf(employeeAddressDetails.getId().longValue()));
            employeeAddressDetailsDTO.setEmployee_id(employeeAddressDetails.getEmployee().getId());
            response.setMessage("Successfully Saved the Address of the Employee");
            response.setData(employeeAddressDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateEmployeeAddressDetails(Long id, EmployeeAddressDetailsDTO employeeAddressDetailsDTO, Long address_id) {
        log.info("updating address of the employee with email " + employeeAddressDetailsDTO.getPersonalEmail() + " and id " + employeeAddressDetailsDTO.getEmployee_id());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update address details for employee")
                .success(false)
                .data(employeeAddressDetailsDTO)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        List<EmployeeAddressDetails> list = employeeAddressDetailsRepository.findEmployeeAddressDetailsByEmployee(employee);
        Boolean state = false;
        for (EmployeeAddressDetails details : list) {
            if (details.getId() == address_id) state = true;
        }
        if(!state) throw new ResourceNotFoundException("You're trying to modify a resource not within your scope");
        EmployeeAddressDetails employeeAddressDetails = employeeAddressDetailsRepository.findById(address_id).orElseThrow(
                () -> new ResourceNotFoundException("Employee Address Details with id : " + address_id + " Not found")
        );
        employeeAddressDetails.setPostalAddress(employeeAddressDetailsDTO.getPostalAddress());
        employeeAddressDetails.setPersonalEmail(employeeAddressDetailsDTO.getPersonalEmail());
        employeeAddressDetails.setPostalCode(employeeAddressDetailsDTO.getPostalCode());
        employeeAddressDetails.setSecondaryMobileNumber(employeeAddressDetailsDTO.getSecondaryMobileNumber());
        employeeAddressDetails.setPrimaryMobileNumber(employeeAddressDetails.getPrimaryMobileNumber());
        employeeAddressDetails = employeeAddressDetailsRepository.save(employeeAddressDetails);
        if (StringUtils.isNotEmpty(employeeAddressDetails.getId().toString())) {
            employeeAddressDetailsDTO.setId(Long.valueOf(employeeAddressDetails.getId().longValue()));
            employeeAddressDetailsDTO.setEmployee_id(employeeAddressDetails.getEmployee().getId());
            response.setMessage("Successfully Updated the Address Details");
            response.setData(employeeAddressDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse selectEmployeeAddressDetailsByID(Long id) {
        log.info("Getting Address Details by input Staff_ID");
        List<EmployeeAddressDetailsDTO> employeeAddressDetailsDTOList = new LinkedList<>();
        ApiResponse response = ApiResponse.builder()
                .message("No Address Details existing with id " + id)
                .success(false)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " Not found")
        );
        List<EmployeeAddressDetails> list = employeeAddressDetailsRepository.findEmployeeAddressDetailsByEmployee(employee);
        if (!list.isEmpty()) {
            employeeAddressDetailsDTOList.addAll(list.stream().map(e -> {
                EmployeeAddressDetailsDTO employeeAddressDetailsDTO = EmployeeAddressDetailsDTO.builder()
                        .Id(e.getId())
                        .employee_id(e.getEmployee().getId())
                        .primaryMobileNumber(e.getPrimaryMobileNumber())
                        .secondaryMobileNumber(e.getSecondaryMobileNumber())
                        .postalAddress(e.getPostalAddress())
                        .postalCode(e.getPostalCode())
                        .personalEmail(e.getPersonalEmail())
                        .build();
                return employeeAddressDetailsDTO;
            }).toList());
            response.setSuccess(true);
            response.setMessage("Retrieved all Employee Address Details by Employee ID " + id);
            response.setData(employeeAddressDetailsDTOList);
        }
        return response;
    }
}
