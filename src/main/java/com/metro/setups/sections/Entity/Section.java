package com.metro.setups.sections.Entity;

import com.metro.audit.Auditable;
import com.metro.setups.department.entities.Department;
import com.metro.setups.designation.entities.Designation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


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
@Table(name = "SECTIONS_TABLE", schema = "hrm")
public class Section extends Auditable {
    @Column(name = "SECTION_NAME")
    private String name;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Designation> designations = new ArrayList<>();


}


