package com.metro.sfaffRegistration.biodata.children.services.Impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.sfaffRegistration.biodata.children.dtos.ChildrenDTO;
import com.metro.sfaffRegistration.biodata.children.repository.ChildrenRepository;
import com.metro.sfaffRegistration.biodata.children.services.ChildrenService;
import com.metro.sfaffRegistration.biodata.children.specifications.Children;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.biodata.employeetest.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class ChildrenServiceImpl implements ChildrenService {
    private final ChildrenRepository childrenRepository;
    private final EmployeeRepository employeeRepository;

    public ChildrenServiceImpl(ChildrenRepository childrenRepository, EmployeeRepository employeeRepository) {
        this.childrenRepository = childrenRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ApiResponse createChildren(ChildrenDTO childrenDTO, Long id) {
        log.info("Saving Employee's child with id : {}", childrenDTO.getEmployee_id());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the child")
                .success(false)
                .data(childrenDTO)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        Children children = Children.builder()
                .firstName(childrenDTO.getFirstName())
                .secondName(childrenDTO.getSecondName())
                .lastName(childrenDTO.getLastName())
                .dateOfBirth(childrenDTO.getDateOfBirth())
                .gender(childrenDTO.getGender())
                .employee(employee)
                .build();
        children = childrenRepository.save(children);
        if (StringUtils.isNotEmpty(children.getId().toString())) {
            childrenDTO.setId(Long.valueOf(children.getId().longValue()));
            childrenDTO.setEmployee_id(children.getEmployee().getId());
            response.setMessage("Successfully Saved the child");
            response.setData(childrenDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateChildren(Long id, ChildrenDTO childrenDTO, Long child_id) {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        log.info("Updating Employee's child with id : {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update the child's details")
                .success(false)
                .data(childrenDTO)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + childrenDTO.getEmployee_id() + " not found"
                ));
        List<Children> list = childrenRepository.findChildrenByEmployee(employee);
        Boolean state = false;
        for (Children child : list) {
            if (child.getId() == child_id) state = true;
        }
        if (!state) throw new ResourceNotFoundException("Cannot Modify another Staff child's details");
        Children children = childrenRepository.findById(child_id).orElseThrow(
                () -> new ResourceNotFoundException("Child with given Id " + id + " Not Found")
        );
        children.setFirstName(childrenDTO.getFirstName());
        children.setSecondName(childrenDTO.getSecondName());
        children.setLastName(childrenDTO.getLastName());
        children.setGender(childrenDTO.getGender());
        children.setDateOfBirth(childrenDTO.getDateOfBirth());
        children = childrenRepository.save(children);
        if (StringUtils.isNotEmpty(children.getId().toString())) {
            childrenDTO.setId(Long.valueOf(children.getId().longValue()));
            childrenDTO.setEmployee_id(children.getEmployee().getId());
            response.setMessage("Successfully Updated the child Details");
            response.setData(childrenDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse selectChildrenByStaffID(Long id) {
        log.info("Getting Children by Id");
        List<ChildrenDTO> childrenDTOList = new LinkedList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("No Kiddo existing by that Id")
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        List<Children> list = childrenRepository.findChildrenByEmployee(employee);
        if (!list.isEmpty()) {
            childrenDTOList.addAll(list.stream().map(e -> {
                ChildrenDTO childrenDTO = ChildrenDTO.builder()
                        .Id(e.getId())
                        .firstName(e.getFirstName())
                        .secondName(e.getSecondName())
                        .lastName(e.getLastName())
                        .gender(e.getGender())
                        .dateOfBirth(e.getDateOfBirth())
                        .employee_id(e.getEmployee().getId())
                        .build();
                return childrenDTO;
            }).toList());
            response.setSuccess(true);
            response.setMessage("Retrieved all Children Details for Employee with ID " + id);
            response.setData(childrenDTOList);
        }
        return response;
    }
}
