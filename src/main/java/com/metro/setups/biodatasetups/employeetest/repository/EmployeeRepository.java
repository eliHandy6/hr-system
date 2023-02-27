package com.metro.setups.biodatasetups.employeetest.repository;

import com.metro.setups.biodatasetups.employeetest.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
