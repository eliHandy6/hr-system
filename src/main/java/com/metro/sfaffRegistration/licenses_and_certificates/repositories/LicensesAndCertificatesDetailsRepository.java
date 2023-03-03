package com.metro.sfaffRegistration.licenses_and_certificates.repositories;

import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.licenses_and_certificates.specification.LicensesAndCertificatesDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicensesAndCertificatesDetailsRepository extends JpaRepository<LicensesAndCertificatesDetails, Long> {
    LicensesAndCertificatesDetails findLicensesAndCertificatesDetailsByEmployee(Employee employee);
    boolean existsLicensesAndCertificatesDetailsByEmployee(Employee employee);
}
