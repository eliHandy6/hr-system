package com.metro.sfaffRegistration.Addresses.employee_addreses.repositories;

import com.metro.sfaffRegistration.Addresses.employee_addreses.specification.EmployeeAddressDetails;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeAddressDetailsRepository extends JpaRepository<EmployeeAddressDetails, Long> {
    List<EmployeeAddressDetails> findEmployeeAddressDetailsByEmployee(Employee employee);

}
