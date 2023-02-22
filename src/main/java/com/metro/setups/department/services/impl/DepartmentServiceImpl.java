package com.metro.setups.department.services.impl;

import com.metro.exceptions.APIExceptions;
import com.metro.exceptions.ApiResponses;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.EmptySpaceExceptionHandler;
import com.metro.setups.department.dtos.DepartmentDTO;
import com.metro.setups.department.entities.Department;
import com.metro.setups.department.repositories.DepartmentRepository;
import com.metro.setups.department.services.DepartmentService;
import com.metro.setups.titles.Entity.Titles;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public ApiResponses getAllDepartments() {
        ApiResponses apiResponse = ApiResponses.builder()
                .message("Failed to fetch all the departments")
                .success(false)
                .data(null)
                .build();
        List<Department> list = departmentRepository.findAll();
        apiResponse.setMessage("Retrieved all the departments Successfully");
        apiResponse.setSuccess(true);
        apiResponse.setData(list);
        return apiResponse;
    }

    @Override
    public ApiResponses getDepartmentById(Long id) {
        return null;
    }

    @Override
    public ApiResponses getDepartmentByName(String name) {
        return null;
    }

    @Override
    public ApiResponses updateDepartment(Long id, DepartmentDTO departmentDTO) {
        return null;
    }

    @Override
    public ApiResponses createDepartment(DepartmentDTO departmentDTO) {
        ApiResponses apiResponse = ApiResponses.builder()
                .message("Failed to create title")
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
}
