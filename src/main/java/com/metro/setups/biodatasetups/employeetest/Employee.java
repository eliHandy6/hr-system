package com.metro.setups.biodatasetups.employeetest;

import com.metro.audit.Auditable;
import com.metro.setups.biodatasetups.children.specifications.Children;
import com.metro.setups.biodatasetups.kin.specification.NextOfKinDetails;
import com.metro.setups.biodatasetups.spouse.specification.SpouseDetails;
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
    private  String name;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Children> children = new ArrayList<>();
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private NextOfKinDetails nextOfKinDetails;
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private SpouseDetails spouseDetails;

}
