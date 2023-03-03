package com.metro.sfaffRegistration.Addresses.emergency_details.repositories;

import com.metro.sfaffRegistration.Addresses.emergency_details.specifications.EmergencyContactDetails;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmergencyContactDetailsRepository extends JpaRepository<EmergencyContactDetails, Long> {
    boolean existsEmergencyContactDetailsByEmployee(Employee employee);
    List<EmergencyContactDetails> findEmergencyContactDetailsByEmployee(Employee employee);
}
