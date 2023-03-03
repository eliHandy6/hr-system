package com.metro.sfaffRegistration.biodata.employeetest;

import com.metro.audit.Auditable;
import com.metro.sfaffRegistration.Addresses.emergency_details.specifications.EmergencyContactDetails;
import com.metro.sfaffRegistration.Addresses.employee_addreses.specification.EmployeeAddressDetails;
import com.metro.sfaffRegistration.Addresses.staff_national_id_details.StaffNationalIdDetails;
import com.metro.sfaffRegistration.biodata.children.specifications.Children;
import com.metro.sfaffRegistration.biodata.kin.specification.NextOfKinDetails;
import com.metro.sfaffRegistration.biodata.spouse.specification.SpouseDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEES_TABLE")
public class Employee extends Auditable {
    @Column(name = "Employee_Name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Children> children = new ArrayList<>();
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private NextOfKinDetails nextOfKinDetails;
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private SpouseDetails spouseDetails;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private EmployeeAddressDetails employeeAddressDetails;
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private EmergencyContactDetails emergencyContactDetails;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private StaffNationalIdDetails staffNationalIdDetails;

}
