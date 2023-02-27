package com.metro.setups.biodatasetups.kin.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.biodatasetups.employeetest.Employee;
import com.metro.setups.biodatasetups.employeetest.repository.EmployeeRepository;
import com.metro.setups.biodatasetups.kin.dtos.NextOfKinDTO;
import com.metro.setups.biodatasetups.kin.repositories.NextOfKinDetailsRepository;
import com.metro.setups.biodatasetups.kin.services.NextOfKinService;
import com.metro.setups.biodatasetups.kin.specification.NextOfKinDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class NextOfKinServiceImpl implements NextOfKinService {
    private final NextOfKinDetailsRepository nextOfKinDetailsRepository;
    private final EmployeeRepository employeeRepository;

    public NextOfKinServiceImpl(NextOfKinDetailsRepository nextOfKinDetailsRepository, EmployeeRepository employeeRepository) {
        this.nextOfKinDetailsRepository = nextOfKinDetailsRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ApiResponse createNextOfKinDetails(NextOfKinDTO nextOfKinDTO) {
        log.info("Saving next of kin details for Employee with id : "+ nextOfKinDTO.getEmployee_ID());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save next of kin details for employee")
                .success(false)
                .data(nextOfKinDTO)
                .build();
        Employee employee = employeeRepository.findById(nextOfKinDTO.getEmployee_ID()).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + nextOfKinDTO.getEmployee_ID() + " not found"
                ));
        NextOfKinDetails nextOfKinDetails = NextOfKinDetails.builder()
                .Residence(nextOfKinDTO.getResidence())
                .nationalID(nextOfKinDTO.getNationalID())
                .phoneNumber(nextOfKinDTO.getPhoneNumber())
                .relationship(nextOfKinDTO.getRelationship())
                .name(nextOfKinDTO.getName())
                .employee(employee)
                .build();
        nextOfKinDetails = nextOfKinDetailsRepository.save(nextOfKinDetails);
        if (StringUtils.isNotEmpty(nextOfKinDetails.getId().toString())) {
            nextOfKinDTO.setId(Long.valueOf(nextOfKinDetails.getId().longValue()));
            response.setMessage("Successfully Saved the child");
            response.setData(nextOfKinDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateNextOfKinDetails(Long id, NextOfKinDTO nextOfKinDTO) {
        log.info("Updating next of kin details for Employee with ID " + nextOfKinDTO.getEmployee_ID());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save next of kin details for employee")
                .success(false)
                .data(nextOfKinDTO)
                .build();
        NextOfKinDetails nextOfKinDetails = nextOfKinDetailsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Next of Kin Details with Id " + id + " Not found")
        );
        Employee employee = employeeRepository.findById(nextOfKinDTO.getEmployee_ID()).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + nextOfKinDTO.getEmployee_ID() + " not found"
                ));
        nextOfKinDetails.setEmployee(employee);
        nextOfKinDetails.setName(nextOfKinDTO.getName());
        nextOfKinDetails.setRelationship(nextOfKinDTO.getRelationship());
        nextOfKinDetails.setResidence(nextOfKinDTO.getRelationship());
        nextOfKinDetails.setNationalID(nextOfKinDetails.getNationalID());
        nextOfKinDetails.setPhoneNumber(nextOfKinDTO.getPhoneNumber());
        nextOfKinDetails = nextOfKinDetailsRepository.save(nextOfKinDetails);
        if (StringUtils.isNotEmpty(nextOfKinDetails.getId().toString())) {
            nextOfKinDTO.setId(Long.valueOf(nextOfKinDetails.getId().longValue()));
            response.setMessage("Successfully Updated the Next of Kin Details");
            response.setData(nextOfKinDTO);
            response.setSuccess(true);
        }
        return response;
    }
    //To : DO => Use paging
    @Override
    public ApiResponse getNextOfKinDetails() {
        log.info("Getting all next of kin Details");
        List<NextOfKinDTO> nextOfKinDTOList = new LinkedList<>();
        ApiResponse response = ApiResponse.builder()
                .message("No Next of kin Details existing")
                .success(false)
                .build();
        List<NextOfKinDetails> list = nextOfKinDetailsRepository.findAll(Sort.by("name").ascending());
        if (!list.isEmpty()) {
            nextOfKinDTOList.addAll(list.stream().map(e -> {
                NextOfKinDTO nextOfKinDTO = NextOfKinDTO.builder()
                        .name(e.getName())
                        .relationship(e.getRelationship())
                        .Residence(e.getResidence())
                        .phoneNumber(e.getPhoneNumber())
                        .nationalID(e.getNationalID())
                        .employee_ID(e.getEmployee().getId())
                        .Id(e.getId())
                        .build();
                return nextOfKinDTO;
            }).toList());
            response.setSuccess(true);
            response.setMessage("Retrieved all next of kin Details");
            response.setData(nextOfKinDTOList);
        }
        return response;
    }

    @Override
    public ApiResponse selectNextOfKinDetailsByName(String name) {
        log.info("Getting all next of kin Details by name");
        List<NextOfKinDTO> nextOfKinDTOList = new LinkedList<>();
        ApiResponse response = ApiResponse.builder()
                .message("No Next of kin Details existing")
                .success(false)
                .build();
        List<NextOfKinDetails> list = nextOfKinDetailsRepository.findNextOfKinDetailsByNameIgnoreCase(name);
        //take this block to a separate function
        if (!list.isEmpty()) {
            nextOfKinDTOList.addAll(list.stream().map(e -> {
                NextOfKinDTO nextOfKinDTO = NextOfKinDTO.builder()
                        .name(e.getName())
                        .relationship(e.getRelationship())
                        .Residence(e.getResidence())
                        .phoneNumber(e.getPhoneNumber())
                        .nationalID(e.getNationalID())
                        .employee_ID(e.getEmployee().getId())
                        .Id(e.getId())
                        .build();
                return nextOfKinDTO;
            }).toList());
            response.setSuccess(true);
            response.setMessage("Retrieved all next of kin Details by name");
            response.setData(nextOfKinDTOList);
        }
        return response;
    }

    @Override
    public ApiResponse selectNextOfKinDetailsByID(Long id) {
        log.info("Getting next of kin details by input ID");
        ApiResponse response = ApiResponse.builder()
                .message("No Next of kin Details existing with id " + id)
                .success(false)
                .build();
        NextOfKinDetails nextOfKinDetails = nextOfKinDetailsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Next of kin Details with id " + id + " Not found")
        );
        if (StringUtils.isNotEmpty(nextOfKinDetails.getId().toString())) {
            NextOfKinDTO nextOfKinDTO = NextOfKinDTO.builder()
                    .Id(nextOfKinDetails.getId())
                    .employee_ID(nextOfKinDetails.getEmployee().getId())
                    .nationalID(nextOfKinDetails.getNationalID())
                    .phoneNumber(nextOfKinDetails.getPhoneNumber())
                    .Residence(nextOfKinDetails.getResidence())
                    .relationship(nextOfKinDetails.getRelationship())
                    .name(nextOfKinDetails.getName())
                    .build();
            response.setMessage("Successfully Retrieved the next of kin By ID");
            response.setData(nextOfKinDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public boolean nextOfKinDetailsExists(String name) {
        return nextOfKinDetailsRepository.existsNextOfKinDetailsByNameIgnoreCase(name);
    }
}
