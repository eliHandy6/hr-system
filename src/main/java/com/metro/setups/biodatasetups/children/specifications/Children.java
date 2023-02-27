package com.metro.setups.biodatasetups.children.specifications;

import com.metro.audit.Auditable;
import com.metro.setups.biodatasetups.employeetest.Employee;
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
@Table(name = "CHILDREN_TABLE")
public class Children extends Auditable {
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "SECOND_NAME", nullable = false)
    private String secondName;
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    private LocalDate dateOfBirth;
    @Column(name = "GENDER", nullable = false)
    private Gender gender;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
