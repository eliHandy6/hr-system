package com.metro.setups.jobsubcategories.entities;

import com.metro.audit.Auditable;
import com.metro.setups.jobcategories.entities.JobCategory;
import com.metro.setups.sections.Entity.Section;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "JOB_SUBCATEGORIES_TABLE")
public class JobSubCategories extends Auditable {
    @Column(name = "SECTION_NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @ManyToOne
    @JoinColumn(name = "JOB_CATEGORY_ID")
    private JobCategory jobCategory;


}
