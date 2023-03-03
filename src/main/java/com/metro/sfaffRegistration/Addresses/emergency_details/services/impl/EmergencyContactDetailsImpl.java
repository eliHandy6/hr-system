package com.metro.sfaffRegistration.Addresses.emergency_details.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.sfaffRegistration.Addresses.emergency_details.dtos.EmergencyContactDetailsDTO;
import com.metro.sfaffRegistration.Addresses.emergency_details.repositories.EmergencyContactDetailsRepository;
import com.metro.sfaffRegistration.Addresses.emergency_details.services.EmergencyContactDetailsService;
import com.metro.sfaffRegistration.Addresses.emergency_details.specifications.EmergencyContactDetails;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.biodata.employeetest.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class EmergencyContactDetailsImpl implements EmergencyContactDetailsService {
    private final EmergencyContactDetailsRepository emergencyContactDetailsRepository;
    private final EmployeeRepository employeeRepository;

    public EmergencyContactDetailsImpl(EmergencyContactDetailsRepository emergencyContactDetailsRepository, EmployeeRepository employeeRepository) {
        this.emergencyContactDetailsRepository = emergencyContactDetailsRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ApiResponse createEmergencyContactDetails(EmergencyContactDetailsDTO emergencyContactDetailsDTO, Long id) {
        log.info("Saving Employee Emergency details with ID : ", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save Emergency Contact details for employee")
                .success(false)
                .data(emergencyContactDetailsDTO)
                .build();

        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        EmergencyContactDetails emergencyContactDetails = EmergencyContactDetails.builder()
                .email(emergencyContactDetailsDTO.getEmail())
                .phoneNumber(emergencyContactDetailsDTO.getPhoneNumber())
                .relationship(emergencyContactDetailsDTO.getRelationship())
                .residence(emergencyContactDetailsDTO.getResidence())
                .name(emergencyContactDetailsDTO.getName())
                .employee(employee)
                .build();
        emergencyContactDetails = emergencyContactDetailsRepository.save(emergencyContactDetails);
        if (StringUtils.isNotEmpty(emergencyContactDetails.getId().toString())) {
            emergencyContactDetailsDTO.setId(Long.valueOf(emergencyContactDetails.getId().longValue()));
            emergencyContactDetailsDTO.setEmployee_id(emergencyContactDetails.getEmployee().getId());
            response.setMessage("Successfully Saved the Address of the Employee");
            response.setData(emergencyContactDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateEmergencyContactDetails(Long id, EmergencyContactDetailsDTO emergencyContactDetailsDTO, Long contact_id) {
        log.info("Updating Employee Emergency details with ID : ", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update Emergency Contact details for employee")
                .success(false)
                .data(emergencyContactDetailsDTO)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        List<EmergencyContactDetails> list = emergencyContactDetailsRepository.findEmergencyContactDetailsByEmployee(employee);
        Boolean state = false;
        for (EmergencyContactDetails details : list) {
            if (details.getId() == contact_id) state = true;
        }
        if(!state) throw new ResourceNotFoundException("You're trying to modify a resource not within your scope");
        EmergencyContactDetails emergencyContactDetails1 =emergencyContactDetailsRepository.findById(contact_id).orElseThrow(
                () -> new ResourceNotFoundException("Emergency contact details with id not found")
        );
        emergencyContactDetails1.setEmail(emergencyContactDetailsDTO.getEmail());
        emergencyContactDetails1.setName(emergencyContactDetailsDTO.getName());
        emergencyContactDetails1.setResidence(emergencyContactDetailsDTO.getResidence());
        emergencyContactDetails1.setPhoneNumber(emergencyContactDetailsDTO.getPhoneNumber());
        emergencyContactDetails1.setRelationship(emergencyContactDetailsDTO.getRelationship());
        emergencyContactDetails1 = emergencyContactDetailsRepository.save(emergencyContactDetails1);
        if (StringUtils.isNotEmpty(emergencyContactDetails1.getId().toString())) {
            emergencyContactDetailsDTO.setId(Long.valueOf(emergencyContactDetails1.getId().longValue()));
            emergencyContactDetailsDTO.setEmployee_id(emergencyContactDetails1.getEmployee().getId());
            response.setMessage("Successfully Updated the Emergency Contact Details");
            response.setData(emergencyContactDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse selectStaffEmergencyContactDetailsByID(Long id) {
        log.info("Selecting Emergency Contact details with ID : ", id);
        List<EmergencyContactDetailsDTO> emergencyContactDetailsDTOList = new LinkedList<>();
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch Emergency Contact details for employee")
                .success(false)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        List<EmergencyContactDetails> list = emergencyContactDetailsRepository.findEmergencyContactDetailsByEmployee(employee);
        if (!list.isEmpty()) {
            emergencyContactDetailsDTOList.addAll(list.stream().map(e -> {
                EmergencyContactDetailsDTO emergencyContactDetailsDTO = EmergencyContactDetailsDTO.builder()
                        .Id(e.getId())
                        .employee_id(e.getEmployee().getId())
                        .name(e.getName())
                        .phoneNumber(e.getPhoneNumber())
                        .phoneNumber(e.getPhoneNumber())
                        .email(e.getEmail())
                        .relationship(e.getRelationship())
                        .residence(e.getResidence())
                        .build();
                return emergencyContactDetailsDTO;
            }).toList());
            response.setSuccess(true);
            response.setMessage("Retrieved all Address Details by Employee ID" + id);
            response.setData(emergencyContactDetailsDTOList);
        }
        return response;
    }
}
