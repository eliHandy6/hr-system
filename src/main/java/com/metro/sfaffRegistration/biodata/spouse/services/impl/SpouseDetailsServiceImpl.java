package com.metro.sfaffRegistration.biodata.spouse.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.biodata.employeetest.repository.EmployeeRepository;
import com.metro.sfaffRegistration.biodata.kin.dtos.NextOfKinDTO;
import com.metro.sfaffRegistration.biodata.kin.specification.NextOfKinDetails;
import com.metro.sfaffRegistration.biodata.spouse.dtos.SpouseDetailsDTO;
import com.metro.sfaffRegistration.biodata.spouse.repositories.SpouseDetailsRepository;
import com.metro.sfaffRegistration.biodata.spouse.services.SpouseDetailsService;
import com.metro.sfaffRegistration.biodata.spouse.specification.SpouseDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class SpouseDetailsServiceImpl implements SpouseDetailsService {
    private final EmployeeRepository employeeRepository;
    private final SpouseDetailsRepository spouseDetailsRepository;

    public SpouseDetailsServiceImpl(EmployeeRepository employeeRepository, SpouseDetailsRepository spouseDetailsRepository) {
        this.employeeRepository = employeeRepository;
        this.spouseDetailsRepository = spouseDetailsRepository;
    }

    @Override
    public ApiResponse createSpouseDetails(SpouseDetailsDTO spouseDetailsDTO, Long id) {
        log.info("Saving Spouse details for employee with id ", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save spouse details for employee")
                .success(false)
                .data(spouseDetailsDTO)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        SpouseDetails spouseDetails = SpouseDetails.builder()
                .Residence(spouseDetailsDTO.getResidence())
                .phoneNumber(spouseDetailsDTO.getPhoneNumber())
                .nationalID(spouseDetailsDTO.getNationalID())
                .email(spouseDetailsDTO.getEmail())
                .employee(employee)
                .name(spouseDetailsDTO.getName())
                .build();
        spouseDetails = spouseDetailsRepository.save(spouseDetails);
        if (StringUtils.isNotEmpty(spouseDetails.getId().toString())) {
            spouseDetailsDTO.setId(Long.valueOf(spouseDetails.getId().longValue()));
            spouseDetailsDTO.setEmployee_ID(spouseDetails.getEmployee().getId());
            response.setMessage("Successfully Saved the Spouse details");
            response.setData(spouseDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateSpouseDetails(Long id, SpouseDetailsDTO spouseDetailsDTO, Long spouse_id) {
        log.info("Updating Spouse details for employee with id " + id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save spouse details for employee")
                .success(false)
                .data(spouseDetailsDTO)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        List<SpouseDetails> list = spouseDetailsRepository.findSpouseDetailsByEmployee(employee);
        Boolean state = false;
        for (SpouseDetails details : list) {
            if (details.getId() == spouse_id) state = true;
        }
        if (!state) throw new ResourceNotFoundException("Cannot Modify another Staff spouse details");
        SpouseDetails spouseDetails = spouseDetailsRepository.findById(spouse_id).orElseThrow(
                () -> new ResourceNotFoundException("Spouse with Id " + id + " Not found")
        );
        spouseDetails.setEmail(spouseDetails.getEmail());
        spouseDetails.setResidence(spouseDetails.getResidence());
        spouseDetails.setName(spouseDetails.getName());
        spouseDetails.setEmployee(employee);
        spouseDetails.setPhoneNumber(spouseDetails.getPhoneNumber());
        spouseDetails.setNationalID(spouseDetails.getNationalID());
        spouseDetails = spouseDetailsRepository.save(spouseDetails);
        if (StringUtils.isNotEmpty(spouseDetails.getId().toString())) {
            spouseDetailsDTO.setId(Long.valueOf(spouseDetails.getId().longValue()));
            spouseDetailsDTO.setEmployee_ID(spouseDetails.getEmployee().getId());
            response.setMessage("Successfully Updated the Next of Kin Details");
            response.setData(spouseDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }
    @Override
    public ApiResponse selectSpouseDetailsByID(Long id) {
        log.info("Getting next of kin details by input ID " + id);
        List<SpouseDetailsDTO> spouseDetailsDTOList = new LinkedList<>();
        ApiResponse response = ApiResponse.builder()
                .message("No Spouse Details existing with id " + id)
                .success(false)
                .data(spouseDetailsDTOList)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        List<SpouseDetails> list = spouseDetailsRepository.findSpouseDetailsByEmployee(employee);
        if (!list.isEmpty()) {
            spouseDetailsDTOList.addAll(list.stream().map(e -> {
                SpouseDetailsDTO spouseDetailsDTO= SpouseDetailsDTO.builder()
                        .Id(e.getId())
                        .employee_ID(e.getEmployee().getId())
                        .nationalID(e.getNationalID())
                        .phoneNumber(e.getPhoneNumber())
                        .Residence(e.getResidence())
                        .email(e.getEmail())
                        .name(e.getName())
                        .build();
                return spouseDetailsDTO;
            }).toList());
            response.setSuccess(true);
            response.setMessage("Retrieved all Next of Kin Details for Employee with ID " + id);
            response.setData(spouseDetailsDTOList);
        }
        return response;
    }
}
