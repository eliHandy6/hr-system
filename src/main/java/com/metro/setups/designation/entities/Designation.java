package com.metro.setups.designation.entities;

import com.metro.audit.Auditable;
import com.metro.setups.department.entities.Department;
import com.metro.setups.sections.Entity.Section;
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
@Table(name = "DESIGNATIONS_TABLE")
public class Designation extends Auditable {
    @Column(name = "SECTION_NAME")
    private String name;
    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;
}
