package com.metro.setups.department.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.EmptySpaceExceptionHandler;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.department.dtos.DepartmentDTO;
import com.metro.setups.department.entities.Department;
import com.metro.setups.department.repositories.DepartmentRepository;
import com.metro.setups.department.services.DepartmentService;
import com.metro.setups.sections.Entity.Section;
import com.metro.setups.sections.dtos.SectionDTO;
import com.metro.setups.sections.respositories.SectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private  final SectionRepository sectionRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, SectionRepository sectionRepository) {
        this.departmentRepository = departmentRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    public ApiResponse getAllDepartments() {
        log.info("getting all departments ...... {}");
        List<DepartmentDTO> departmentDTOS = new ArrayList<>();
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to fetch all the departments")
                .success(false)
                .data(departmentDTOS)
                .build();
        List<Department> list = departmentRepository.findAll(Sort.by("name").ascending());
        if (!list.isEmpty()) {
            departmentDTOS.addAll(list.stream().map(e -> {
                DepartmentDTO departmentDTO = DepartmentDTO.builder()
                        .Id(e.getId())
                        .name(e.getName())
                        .manager_id(e.getManager_id())
                        .build();
                return departmentDTO;
            }).toList());
            apiResponse.setMessage("Retrieved all the departments Successfully");
            apiResponse.setSuccess(true);
            apiResponse.setData(departmentDTOS);
        }
        return apiResponse;
    }

    @Override
    public ApiResponse getDepartmentById(Long id) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to get department by Id")
                .success(false)
                .data(null)
                .build();

        Department department = departmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Department with id" + id + " Not found")
        );
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                        .Id(department.getId())
                        .name(department.getName())
                        .manager_id(department.getManager_id())
                        .build();
        apiResponse.setMessage("Successfully fetched department with given Id");
        apiResponse.setSuccess(true);
        apiResponse.setData(departmentDTO);
        return apiResponse;
    }

    @Override
    public ApiResponse getDepartmentByName(String name) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to get department by name")
                .success(false)
                .data(null)
                .build();
        Department department = departmentRepository.findDepartmentByName(name).orElseThrow(() -> new ResourceNotFoundException(" Department with name : " + name + "not found"));
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .Id(department.getId())
                .name(department.getName())
                .manager_id(department.getManager_id())
                .build();
        apiResponse.setData(departmentDTO);
        apiResponse.setMessage("Completed Search successfully");
        apiResponse.setSuccess(true);
        return apiResponse;
    }

    @Override
    public ApiResponse updateDepartment(Long id, DepartmentDTO departmentDTO) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to update the department")
                .success(false)
                .data(departmentDTO)
                .build();
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("department with id " + id +" not found" ));
        department.setName(departmentDTO.getName());
        department.setManager_id(departmentDTO.getManager_id());
        department = departmentRepository.save(department);
        departmentDTO.setId((Long) department.getId());
        apiResponse.setData(departmentDTO);
        apiResponse.setMessage("Updated Department Successfully");
        apiResponse.setSuccess(true);
        return apiResponse;
    }

    @Override
    public ApiResponse createDepartment(DepartmentDTO departmentDTO) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to create Department")
                .success(false)
                .data(departmentDTO)
                .build();
        String name = departmentDTO.getName();
        if((name.trim()).length() == 0) throw new EmptySpaceExceptionHandler("Name of the department cannot be empty");
        if (departmentRepository.findDepartmentByName(name).isPresent()) {
            throw new DuplicateResourceException("department " + name +" already exists in the database try another");
        }

        Department department = new Department(departmentDTO.getName(), departmentDTO.getManager_id());
        department = departmentRepository.save(department);
        departmentDTO.setId((Long) department.getId());
        apiResponse.setData(departmentDTO);
        apiResponse.setMessage("Created Department Successfully");
        apiResponse.setSuccess(true);
        return apiResponse;
    }

    @Override
    public ApiResponse getAllDepartmentSections(Long id) {
        log.info("getting all sections for a specific ...... {}");
        List<SectionDTO> sectionDTOS = new ArrayList<>();
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to fetch all then sections associated with the department id")
                .success(false)
                .data(sectionDTOS)
                .build();
        List<Section> list = sectionRepository.getSectionsByDepartment_Id(id);
        list.sort(Comparator.comparing(Section::getName));
        if (!list.isEmpty()) {
            sectionDTOS.addAll(list.stream().map(e -> {
                SectionDTO sectionDTO = SectionDTO.builder()
                        .sectionId(e.getId())
                        .name(e.getName())
                        .department_id(e.getDepartment().getId())
                        .build();
                return sectionDTO;
            }).toList());
            apiResponse.setData(sectionDTOS);
            apiResponse.setMessage("Completed Retrieving sections in the department");
            apiResponse.setSuccess(true);
        }
        return apiResponse;

    }
}
