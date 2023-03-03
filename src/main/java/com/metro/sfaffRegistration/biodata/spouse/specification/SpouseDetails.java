package com.metro.sfaffRegistration.biodata.spouse.specification;

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
@Table(name = "SPOUSE_DETAILS_TABLE")
public class SpouseDetails extends Auditable{
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;
    @Column(name = "RESIDENCE", nullable = false)
    private String Residence;
    @Column(name = "NATIONAL_IDENTITY_NUMBER", nullable = false)
    private Long nationalID;
    @Column(name = "EMAIL")
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;
}
