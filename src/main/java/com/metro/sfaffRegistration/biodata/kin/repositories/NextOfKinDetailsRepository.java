package com.metro.sfaffRegistration.biodata.kin.repositories;

import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.biodata.kin.specification.NextOfKinDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NextOfKinDetailsRepository extends JpaRepository<NextOfKinDetails, Long> {
    List<NextOfKinDetails> findNextOfKinDetailsByEmployee(Employee employee);
}
