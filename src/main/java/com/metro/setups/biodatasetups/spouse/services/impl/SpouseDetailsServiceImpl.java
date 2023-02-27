package com.metro.setups.biodatasetups.spouse.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.biodatasetups.employeetest.Employee;
import com.metro.setups.biodatasetups.employeetest.repository.EmployeeRepository;
import com.metro.setups.biodatasetups.spouse.dtos.SpouseDetailsDTO;
import com.metro.setups.biodatasetups.spouse.repositories.SpouseDetailsRepository;
import com.metro.setups.biodatasetups.spouse.services.SpouseDetailsService;
import com.metro.setups.biodatasetups.spouse.specification.SpouseDetails;
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
    public ApiResponse createSpouseDetails(SpouseDetailsDTO spouseDetailsDTO) {
        log.info("Saving Spouse details for employee with id " + spouseDetailsDTO.getEmployee_ID());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save spouse details for employee")
                .success(false)
                .data(spouseDetailsDTO)
                .build();
        Employee employee = employeeRepository.findById(spouseDetailsDTO.getEmployee_ID()).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + spouseDetailsDTO.getEmployee_ID() + " not found"
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
            response.setMessage("Successfully Saved the child");
            response.setData(spouseDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateSpouseDetails(Long id, SpouseDetailsDTO spouseDetailsDTO) {
        log.info("Updating Spouse details for employee with id " + spouseDetailsDTO.getEmployee_ID());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save spouse details for employee")
                .success(false)
                .data(spouseDetailsDTO)
                .build();
        SpouseDetails spouseDetails = spouseDetailsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Spouse with Id " + id + " Not found")
        );
        Employee employee = employeeRepository.findById(spouseDetailsDTO.getEmployee_ID()).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + spouseDetailsDTO.getEmployee_ID() + " not found"
                ));
        spouseDetails.setEmail(spouseDetails.getEmail());
        spouseDetails.setResidence(spouseDetails.getResidence());
        spouseDetails.setName(spouseDetails.getName());
        spouseDetails.setEmployee(employee);
        spouseDetails.setPhoneNumber(spouseDetails.getPhoneNumber());
        spouseDetails.setNationalID(spouseDetails.getNationalID());
        if (StringUtils.isNotEmpty(spouseDetails.getId().toString())) {
            spouseDetailsDTO.setId(Long.valueOf(spouseDetails.getId().longValue()));
            response.setMessage("Successfully Updated the Next of Kin Details");
            response.setData(spouseDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    //do pagers
    @Override
    public ApiResponse getSpouseDetails() {
        log.info("Getting all spouse details ....");
        List<SpouseDetailsDTO> spouseDetailsDTOList = new LinkedList<>();
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get all spouse details in the DB")
                .success(false)
                .build();
        List<SpouseDetails> list = spouseDetailsRepository.findAll(Sort.by("name").ascending());
        if (!list.isEmpty()) {
            spouseDetailsDTOList.addAll(list.stream().map(e -> {
                SpouseDetailsDTO spouseDetailsDTO = SpouseDetailsDTO.builder()
                        .name(e.getName())
                        .email(e.getEmail())
                        .Residence(e.getResidence())
                        .phoneNumber(e.getPhoneNumber())
                        .nationalID(e.getNationalID())
                        .employee_ID(e.getEmployee().getId())
                        .Id(e.getId())
                        .build();
                return spouseDetailsDTO;
            }).toList());
            response.setSuccess(true);
            response.setMessage("Retrieved all the available spouse Details");
            response.setData(spouseDetailsDTOList);
        }
        return response;
    }

    @Override
    public ApiResponse selectSpouseDetailsByName(String name) {
        log.info("Getting all spouse details by name....");
        List<SpouseDetailsDTO> spouseDetailsDTOList = new LinkedList<>();
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get spouse details")
                .success(false)
                .build();
        List<SpouseDetails> list = spouseDetailsRepository.findSpouseDetailsByNameIgnoreCase(name);
        if (!list.isEmpty()) {
            spouseDetailsDTOList.addAll(list.stream().map(e -> {
                SpouseDetailsDTO spouseDetailsDTO = SpouseDetailsDTO.builder()
                        .name(e.getName())
                        .email(e.getEmail())
                        .Residence(e.getResidence())
                        .phoneNumber(e.getPhoneNumber())
                        .nationalID(e.getNationalID())
                        .employee_ID(e.getEmployee().getId())
                        .Id(e.getId())
                        .build();
                return spouseDetailsDTO;
            }).toList());
            response.setSuccess(true);
            response.setMessage("Retrieved all spouse Details by name");
            response.setData(spouseDetailsDTOList);
        }
        return response;
    }

    @Override
    public ApiResponse selectSpouseDetailsByID(Long id) {
        log.info("Getting next of kin details by input ID " + id);
        ApiResponse response = ApiResponse.builder()
                .message("No Spouse Details existing with id " + id)
                .success(false)
                .build();
        SpouseDetails spouseDetails = spouseDetailsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Spouse with Id " + id + " Not found")
        );
        if (StringUtils.isNotEmpty(spouseDetails.getId().toString())) {
            SpouseDetailsDTO spouseDetailsDTO = SpouseDetailsDTO.builder()
                    .Id(spouseDetails.getId())
                    .employee_ID(spouseDetails.getEmployee().getId())
                    .nationalID(spouseDetails.getNationalID())
                    .phoneNumber(spouseDetails.getPhoneNumber())
                    .Residence(spouseDetails.getResidence())
                    .email(spouseDetails.getEmail())
                    .name(spouseDetails.getName())
                    .build();
            response.setMessage("Successfully Retrieved the Spouse By ID");
            response.setData(spouseDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public boolean spouseDetailsExists(String name) {
        return spouseDetailsRepository.existsSpouseDetailsByNameIgnoreCase(name);
    }
}
