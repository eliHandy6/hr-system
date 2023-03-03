package com.metro.sfaffRegistration.licenses_and_certificates.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.biodata.employeetest.repository.EmployeeRepository;
import com.metro.sfaffRegistration.licenses_and_certificates.dtos.LicensesAndCertificatesDetailsDTO;
import com.metro.sfaffRegistration.licenses_and_certificates.repositories.LicensesAndCertificatesDetailsRepository;
import com.metro.sfaffRegistration.licenses_and_certificates.services.LicensesAndCertificatesDetailsService;
import com.metro.sfaffRegistration.licenses_and_certificates.specification.LicensesAndCertificatesDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LicensesAndCertificatesDetailsServiceImpl implements LicensesAndCertificatesDetailsService {
    private final EmployeeRepository employeeRepository;
    private final LicensesAndCertificatesDetailsRepository licensesAndCertificatesDetailsRepository;

    public LicensesAndCertificatesDetailsServiceImpl(EmployeeRepository employeeRepository, LicensesAndCertificatesDetailsRepository licensesAndCertificatesDetailsRepository) {
        this.employeeRepository = employeeRepository;
        this.licensesAndCertificatesDetailsRepository = licensesAndCertificatesDetailsRepository;
    }

    @Override
    public ApiResponse createLicensesAndCertificatesDetails(LicensesAndCertificatesDetailsDTO licensesAndCertificatesDetailsDTO, Long id) {
        log.info("Saving Licences and Certificate for employee with Id number", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to Save Licenses and Certificate details for employee")
                .success(false)
                .data(licensesAndCertificatesDetailsDTO)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        if (licensesAndCertificatesDetailsExists(employee))
            throw new ResourceNotFoundException("Employee with id " + id + "has licenses and Certificates Try updating");
        LicensesAndCertificatesDetails licensesAndCertificatesDetails = LicensesAndCertificatesDetails.builder()
                .licenseNumber(licensesAndCertificatesDetailsDTO.getLicenseNumber())
                .licenseDetails(licensesAndCertificatesDetailsDTO.getLicenseDetails())
                .condition(licensesAndCertificatesDetailsDTO.getCondition())
                .licenseType(licensesAndCertificatesDetailsDTO.getLicenseType())
                .dateOfIssue(licensesAndCertificatesDetailsDTO.getDateOfIssue())
                .expiryDate(licensesAndCertificatesDetailsDTO.getExpiryDate())
                .issuerName(licensesAndCertificatesDetailsDTO.getIssuerName())
                .employee(employee)
                .condition(licensesAndCertificatesDetailsDTO.getCondition())
                .build();
        licensesAndCertificatesDetails = licensesAndCertificatesDetailsRepository.save(licensesAndCertificatesDetails);
        if (StringUtils.isNotEmpty(licensesAndCertificatesDetails.getId().toString())) {
            licensesAndCertificatesDetailsDTO.setId(Long.valueOf(licensesAndCertificatesDetails.getId().longValue()));
            licensesAndCertificatesDetailsDTO.setEmployee_id(licensesAndCertificatesDetails.getEmployee().getId());
            response.setMessage("Successfully Saved Licenses and certification Details of the Employee");
            response.setData(licensesAndCertificatesDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateLicensesAndCertificatesDetails(Long id, LicensesAndCertificatesDetailsDTO licensesAndCertificatesDetailsDTO) {
        log.info("Saving Licences and Certificate for employee with Id number", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to Save Licenses and Certificate details for employee")
                .success(false)
                .data(licensesAndCertificatesDetailsDTO)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        LicensesAndCertificatesDetails licensesAndCertificatesDetails = licensesAndCertificatesDetailsRepository.findLicensesAndCertificatesDetailsByEmployee(employee);
        licensesAndCertificatesDetails.setLicenseDetails(licensesAndCertificatesDetailsDTO.getLicenseDetails());
        licensesAndCertificatesDetails.setLicenseType(licensesAndCertificatesDetailsDTO.getLicenseType());
        licensesAndCertificatesDetails.setLicenseNumber(licensesAndCertificatesDetailsDTO.getLicenseNumber());
        licensesAndCertificatesDetails.setExpiryDate(licensesAndCertificatesDetailsDTO.getExpiryDate());
        licensesAndCertificatesDetails.setDateOfIssue(licensesAndCertificatesDetailsDTO.getDateOfIssue());
        licensesAndCertificatesDetails.setCondition(licensesAndCertificatesDetailsDTO.getCondition());
        licensesAndCertificatesDetails.setIssuerName(licensesAndCertificatesDetails.getIssuerName());
        licensesAndCertificatesDetails = licensesAndCertificatesDetailsRepository.save(licensesAndCertificatesDetails);
        if (StringUtils.isNotEmpty(licensesAndCertificatesDetails.getId().toString())) {
            licensesAndCertificatesDetailsDTO.setId(Long.valueOf(licensesAndCertificatesDetails.getId().longValue()));
            licensesAndCertificatesDetailsDTO.setEmployee_id(licensesAndCertificatesDetails.getEmployee().getId());
            response.setMessage("Successfully Updated the Licenses and Details");
            response.setData(licensesAndCertificatesDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse selectLicensesAndCertificatesDetailsByID(Long id) {
        log.info("Getting Licenses and certification details for Staff with ID : ", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get all certificate details for employee")
                .success(false)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        LicensesAndCertificatesDetails licensesAndCertificatesDetails = licensesAndCertificatesDetailsRepository.findLicensesAndCertificatesDetailsByEmployee(employee);
        if (StringUtils.isNotEmpty(licensesAndCertificatesDetails.getId().toString())) {
            LicensesAndCertificatesDetailsDTO licensesAndCertificatesDetailsDTO = LicensesAndCertificatesDetailsDTO.builder()
                    .Id(licensesAndCertificatesDetails.getId())
                    .employee_id(licensesAndCertificatesDetails.getEmployee().getId())
                    .licenseDetails(licensesAndCertificatesDetails.getLicenseDetails())
                    .licenseType(licensesAndCertificatesDetails.getLicenseType())
                    .condition(licensesAndCertificatesDetails.getCondition())
                    .dateOfIssue(licensesAndCertificatesDetails.getDateOfIssue())
                    .expiryDate(licensesAndCertificatesDetails.getExpiryDate())
                    .licenseNumber(licensesAndCertificatesDetails.getLicenseNumber())
                    .issuerName(licensesAndCertificatesDetails.getIssuerName())
                    .build();
            response.setSuccess(true);
            response.setMessage("Retrieved all Certificates and Licenses Details for Employee with ID");
            response.setData(licensesAndCertificatesDetailsDTO);
        }
        return response;
    }

    @Override
    public boolean licensesAndCertificatesDetailsExists(Employee employee) {
        return licensesAndCertificatesDetailsRepository.existsLicensesAndCertificatesDetailsByEmployee(employee);
    }
}
