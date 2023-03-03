package com.metro.sfaffRegistration.biodata.employeetest.repository;

import com.metro.sfaffRegistration.Addresses.employee_addreses.specification.EmployeeAddressDetails;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsEmployeeByNameIgnoreCase(String name);

}
