package com.metro.setups.department.services;

import com.metro.exceptions.ApiResponses;
import com.metro.setups.department.dtos.DepartmentDTO;

public interface DepartmentService {
    ApiResponses getAllDepartments();
    ApiResponses getDepartmentById(Long id);
    ApiResponses getDepartmentByName(String name);
    ApiResponses updateDepartment(Long id, DepartmentDTO departmentDTO);
    ApiResponses createDepartment(DepartmentDTO departmentDTO);

}
