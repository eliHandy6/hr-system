package com.metro.sfaffRegistration.Addresses.employee_addreses.specification;

import com.metro.audit.Auditable;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.biodata.spouse.specification.SpouseDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "EMPLOYEE_ADDRESS_DETAILS", schema="hrm")
public class EmployeeAddressDetails extends Auditable {
    @Column(name = "PRIMARY_PHONE_NUMBER", nullable = false)
    private String primaryMobileNumber;
    @Column(name = "SECONDARY_PHONE_NUMBER", nullable = true)
    private String secondaryMobileNumber;
    @Column(name = "POSTAL_ADDRESS", nullable = false)
    private Long postalAddress;
    @Column(name = "POSTAL_CODE", nullable = false)
    private Long postalCode;
    @Column(name = "PERSONAL_EMAIL", nullable = false)
    private String personalEmail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;
}
