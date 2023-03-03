package com.metro.sfaffRegistration.licenses_and_certificates.services;

import com.metro.core.ApiResponse;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.licenses_and_certificates.dtos.LicensesAndCertificatesDetailsDTO;

public interface LicensesAndCertificatesDetailsService {
    ApiResponse createLicensesAndCertificatesDetails(LicensesAndCertificatesDetailsDTO licensesAndCertificatesDetailsDTO, Long id);
    ApiResponse updateLicensesAndCertificatesDetails(Long id, LicensesAndCertificatesDetailsDTO licensesAndCertificatesDetailsDTO);
    ApiResponse selectLicensesAndCertificatesDetailsByID(Long id);
    boolean licensesAndCertificatesDetailsExists(Employee employee);
}
