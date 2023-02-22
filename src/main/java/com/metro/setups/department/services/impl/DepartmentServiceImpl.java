package com.metro.setups.department.services.impl;

import com.metro.exceptions.ApiResponses;
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
        return null;
    }
}
