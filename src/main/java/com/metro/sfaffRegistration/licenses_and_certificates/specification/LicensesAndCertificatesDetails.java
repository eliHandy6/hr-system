package com.metro.sfaffRegistration.licenses_and_certificates.specification;

import com.metro.audit.Auditable;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEE_LICENSES AND_CERTIFICATES_ID_DETAILS")
public class LicensesAndCertificatesDetails extends Auditable {
    @Column(name = "ISSSUER_NAME", nullable = false)
    private  String issuerName;
    @Column(name = "LICENSE_NUMBER", nullable = false)
    private String licenseNumber;
    @Column(name = "LICENSE_TYPE", nullable = false)
    private String licenseType;
    @Column(name = "LICENSE_DETAILS", nullable = false)
    private String licenseDetails;
    @Column(name = "EXPIRY_DATE", nullable = false)
    private LocalDate expiryDate;
    @Column(name = "DATE_OF_ISSUE", nullable = false)
    private LocalDate dateOfIssue;
    @Column(name = "LICENSE_CONDITION", nullable = false)
    private String condition;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

}
