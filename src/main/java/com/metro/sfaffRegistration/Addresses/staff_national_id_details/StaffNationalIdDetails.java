package com.metro.sfaffRegistration.Addresses.staff_national_id_details;

import com.metro.audit.Auditable;
import com.metro.setups.districts.entities.District;
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
@Table(name = "EMPLOYEE_NATIONAL_ID_DETAILS")
public class StaffNationalIdDetails extends Auditable {

    @Column(name = "DIVISION_NAME", nullable = true)
    private String divisionName;
    @Column(name = "LOCATION_NAME", nullable = true)
    private String locationName;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District district;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;
}
