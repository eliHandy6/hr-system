package com.metro.sfaffRegistration.biodata.spouse.repositories;

import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.biodata.spouse.specification.SpouseDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpouseDetailsRepository extends JpaRepository<SpouseDetails, Long> {
    List<SpouseDetails> findSpouseDetailsByEmployee(Employee employee);
}
