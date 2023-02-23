package com.metro.setups.department.services;

import com.metro.core.ApiResponse;
import com.metro.exceptions.ApiResponses;
import com.metro.setups.department.dtos.DepartmentDTO;

public interface DepartmentService {
    ApiResponse getAllDepartments();
    ApiResponse getDepartmentById(Long id);
    ApiResponse getDepartmentByName(String name);
    ApiResponse updateDepartment(Long id, DepartmentDTO departmentDTO);
    ApiResponse createDepartment(DepartmentDTO departmentDTO);

}
