package com.metro.setups.biodatasetups.children.services.Impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.biodatasetups.children.dtos.ChildrenDTO;
import com.metro.setups.biodatasetups.children.repository.ChildrenRepository;
import com.metro.setups.biodatasetups.children.services.ChildrenService;
import com.metro.setups.biodatasetups.children.specifications.Children;
import com.metro.setups.biodatasetups.employeetest.Employee;
import com.metro.setups.biodatasetups.employeetest.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    public ApiResponse createChildren(ChildrenDTO childrenDTO) {
        log.info("Saving Employee's child with id : {}", childrenDTO.getEmployee_id());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the child")
                .success(false)
                .data(childrenDTO)
                .build();
        Employee employee = employeeRepository.findById(childrenDTO.getEmployee_id()).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + childrenDTO.getEmployee_id() + " not found"
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
            response.setMessage("Successfully Saved the child");
            response.setData(childrenDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateChildren(Long id, ChildrenDTO childrenDTO) {
        log.info("Updating Employee's child with id : {}", childrenDTO.getEmployee_id());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update the child's details")
                .success(false)
                .data(childrenDTO)
                .build();
        Children children = childrenRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Child with given Id " + id + " Not Found")

        );
        Employee employee = employeeRepository.findById(childrenDTO.getEmployee_id()).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + childrenDTO.getEmployee_id() + " not found"
                ));
        children.setFirstName(childrenDTO.getFirstName());
        children.setSecondName(childrenDTO.getSecondName());
        children.setLastName(childrenDTO.getLastName());
        children.setGender(childrenDTO.getGender());
        children.setDateOfBirth(childrenDTO.getDateOfBirth());
        children.setEmployee(employee);
        children = childrenRepository.save(children);
        if (StringUtils.isNotEmpty(children.getId().toString())) {
            childrenDTO.setId(Long.valueOf(children.getId().longValue()));
            response.setMessage("Successfully Updated the child Details");
            response.setData(childrenDTO);
            response.setSuccess(true);
        }
        return response;
    }

    //use paging => to add
    @Override
    public ApiResponse getChildren() {
        log.info("Retrieving all children");
        List<ChildrenDTO> childrenDTOList = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .message("No Kiddos existing")
                .success(false)
                .build();
        List<Children> list = childrenRepository.findAll(Sort.by("firstName").ascending());
        if (!list.isEmpty()) {
            childrenDTOList.addAll(list.stream().map(e -> {
                ChildrenDTO childrenDTO = ChildrenDTO.builder()
                        .firstName(e.getFirstName())
                        .lastName(e.getLastName())
                        .secondName(e.getSecondName())
                        .gender(e.getGender())
                        .dateOfBirth(e.getDateOfBirth())
                        .employee_id(e.getEmployee().getId())
                        .Id(e.getId())
                        .build();
                return childrenDTO;
            }).toList());
        response.setSuccess(true);
        response.setMessage("Retrieved all children");
        response.setData(childrenDTOList);
        }
        return response;
    }

    @Override
    public ApiResponse selectChildrenByName(String name) {
        log.info("Retrieving all children with given name");
        List<ChildrenDTO> childrenDTOList = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .message("No Kiddos existing")
                .success(false)
                .build();
        List<Children> list = childrenRepository.findChildrenByFirstNameIgnoreCaseOrLastNameIgnoreCaseOrSecondNameIgnoreCase(name, name, name);
        Comparator<Children> firstNameComparator = new Comparator<Children>() {
            @Override
            public int compare(Children o1, Children o2) {
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
        };
        Collections.sort(list, firstNameComparator);
        if (!list.isEmpty()) {
            childrenDTOList.addAll(list.stream().map(e -> {
                ChildrenDTO childrenDTO = ChildrenDTO.builder()
                        .firstName(e.getFirstName())
                        .lastName(e.getLastName())
                        .secondName(e.getSecondName())
                        .gender(e.getGender())
                        .dateOfBirth(e.getDateOfBirth())
                        .employee_id(e.getEmployee().getId())
                        .Id(e.getId())
                        .build();
                return childrenDTO;
            }).toList());
            response.setSuccess(true);
            response.setMessage("Retrieved all  By searched name");
            response.setData(childrenDTOList);
        }
        return response;
    }

    @Override
    public ApiResponse selectChildrenByID(Long id) {
        log.info("Getting Children by Id");
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("No Kiddo existing by that Id")
                .build();
        Children children = childrenRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Child with given Id " + id + " Not Found")

        );
        if (StringUtils.isNotEmpty(children.getId().toString())) {
            ChildrenDTO childrenDTO = ChildrenDTO.builder()
                    .Id(children.getId())
                    .employee_id(children.getEmployee().getId())
                    .dateOfBirth(children.getDateOfBirth())
                    .firstName(children.getFirstName())
                    .secondName(children.getSecondName())
                    .lastName(children.getLastName())
                    .build();
            response.setMessage("Successfully Retrieved the child By ID");
            response.setData(childrenDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public boolean ChildrenExists(String name) {
        return childrenRepository.existsByFirstNameIgnoreCaseOrSecondNameIgnoreCaseOrLastNameIgnoreCase(name, name, name);
    }
}
