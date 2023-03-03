package com.metro.sfaffRegistration.Addresses.staff_national_id_details.repositories;

import com.metro.sfaffRegistration.Addresses.emergency_details.specifications.EmergencyContactDetails;
import com.metro.sfaffRegistration.Addresses.staff_national_id_details.StaffNationalIdDetails;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationalIdDetailsrepository extends JpaRepository<StaffNationalIdDetails, Long> {
    StaffNationalIdDetails findStaffNationalIdDetailsByEmployee(Employee employee);
    boolean existsStaffNationalIdDetailsByEmployee(Employee employee);
}
