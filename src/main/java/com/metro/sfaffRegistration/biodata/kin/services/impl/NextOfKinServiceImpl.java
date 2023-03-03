package com.metro.sfaffRegistration.biodata.kin.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.sfaffRegistration.biodata.children.dtos.ChildrenDTO;
import com.metro.sfaffRegistration.biodata.children.specifications.Children;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.biodata.employeetest.repository.EmployeeRepository;
import com.metro.sfaffRegistration.biodata.kin.dtos.NextOfKinDTO;
import com.metro.sfaffRegistration.biodata.kin.repositories.NextOfKinDetailsRepository;
import com.metro.sfaffRegistration.biodata.kin.services.NextOfKinService;
import com.metro.sfaffRegistration.biodata.kin.specification.NextOfKinDetails;
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
    public ApiResponse createNextOfKinDetails(NextOfKinDTO nextOfKinDTO, Long id) {
        log.info("Saving next of kin details for Employee with id : "+ id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save next of kin details for employee")
                .success(false)
                .data(nextOfKinDTO)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
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
            nextOfKinDTO.setEmployee_ID(nextOfKinDetails.getEmployee().getId());
            response.setMessage("Successfully Saved the next of kin details");
            response.setData(nextOfKinDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateNextOfKinDetails(Long id, NextOfKinDTO nextOfKinDTO, Long kin_id) {
        log.info("Updating next of kin details for Employee with ID " + nextOfKinDTO.getEmployee_ID());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save next of kin details for employee")
                .success(false)
                .data(nextOfKinDTO)
                .build();

        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        List<NextOfKinDetails> list = nextOfKinDetailsRepository.findNextOfKinDetailsByEmployee(employee);
        Boolean state = false;
        for (NextOfKinDetails nextOfKinDetails : list) {
            if (nextOfKinDetails.getId() == kin_id) state = true;
        }
        if (!state) throw new ResourceNotFoundException("Cannot Modify another Staff next of kin details");
        NextOfKinDetails nextOfKinDetails = nextOfKinDetailsRepository.findById(kin_id).orElseThrow(
                () -> new ResourceNotFoundException("Next of Kin Details with Id " + kin_id + " Not found")
        );
        nextOfKinDetails.setEmployee(employee);
        nextOfKinDetails.setName(nextOfKinDTO.getName());
        nextOfKinDetails.setRelationship(nextOfKinDTO.getRelationship());
        nextOfKinDetails.setResidence(nextOfKinDTO.getRelationship());
        nextOfKinDetails.setNationalID(nextOfKinDetails.getNationalID());
        nextOfKinDetails.setPhoneNumber(nextOfKinDTO.getPhoneNumber());
        nextOfKinDetails = nextOfKinDetailsRepository.save(nextOfKinDetails);
        if (StringUtils.isNotEmpty(nextOfKinDetails.getId().toString())) {
            nextOfKinDTO.setId(Long.valueOf(nextOfKinDetails.getId().longValue()));
            nextOfKinDTO.setEmployee_ID(nextOfKinDetails.getEmployee().getId());
            response.setMessage("Successfully Updated the Next of Kin Details");
            response.setData(nextOfKinDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse selectNextOfKinDetailsByID(Long id) {
        log.info("Getting next of kin details by input ID");
        List<NextOfKinDTO> nextOfKinDTOList = new LinkedList<>();
        ApiResponse response = ApiResponse.builder()
                .message("No Next of kin Details existing with id " + id)
                .success(false)
                .data(nextOfKinDTOList)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        List<NextOfKinDetails> list = nextOfKinDetailsRepository.findNextOfKinDetailsByEmployee(employee);
        if (!list.isEmpty()) {
            nextOfKinDTOList.addAll(list.stream().map(e -> {
                NextOfKinDTO nextOfKinDTO = NextOfKinDTO.builder()
                        .Id(e.getId())
                        .nationalID(e.getNationalID())
                        .employee_ID(e.getEmployee().getId())
                        .phoneNumber(e.getPhoneNumber())
                        .Residence(e.getResidence())
                        .relationship(e.getRelationship())
                        .name(e.getName())
                        .build();
                return nextOfKinDTO;
            }).toList());
            response.setSuccess(true);
            response.setMessage("Retrieved all Next of Kin Details for Employee with ID " + id);
            response.setData(nextOfKinDTOList);
        }
        return response;
    }
}
