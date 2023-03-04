package com.metro.sfaffRegistration.Addresses.emergency_details.specifications;

import com.metro.audit.Auditable;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
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
@Table(name = "EMPLOYEE_EMERGENCY_CONTACT_DETAILS", schema="hrm")
public class EmergencyContactDetails extends Auditable {
    @Column(name = "EMERGENCY_CONTACT_NAME", nullable = false)
    private String name;
    @Column(name = "EMERGENCY_CONTACT_PHONE_NUMBER", nullable = false)
    private String phoneNumber;
    @Column(name = "EMERGENCY_CONTACT_RELATIONSHIP", nullable = false)
    private String relationship;
    @Column(name = "EMERGENCY_CONTACT_RESIDENCE", nullable = false)
    private String residence;
    @Column(name = "EMERGENCY_CONTACT_EMAIL", nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

}
