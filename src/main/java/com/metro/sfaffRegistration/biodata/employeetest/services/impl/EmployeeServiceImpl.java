package com.metro.sfaffRegistration.biodata.employeetest.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.EmptySpaceExceptionHandler;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.biodata.employeetest.dtos.EmployeeDTO;
import com.metro.sfaffRegistration.biodata.employeetest.repository.EmployeeRepository;
import com.metro.sfaffRegistration.biodata.employeetest.services.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ApiResponse createEmployee(EmployeeDTO employeeDTO) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to Employee")
                .success(false)
                .data(employeeDTO)
                .build();
        String name = employeeDTO.getName();
        if((name.trim()).length() == 0) throw new EmptySpaceExceptionHandler("Name of the department cannot be empty");
        if (existsByName(name)) {
            throw new DuplicateResourceException("department " + name +" already exists in the database try another");
        }
        Employee employee = Employee.builder()
                .name(employeeDTO.getName())
                .build();
        employee = employeeRepository.save(employee);
        employeeDTO.setId((Long) employee.getId());
        apiResponse.setData(employeeDTO);
        apiResponse.setMessage("Created Employee Successfully");
        apiResponse.setSuccess(true);
        return apiResponse;
    }

    @Override
    public boolean existsByName(String name) {
        return employeeRepository.existsEmployeeByNameIgnoreCase(name);
    }
}
