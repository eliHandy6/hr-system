package com.metro.setups.staff.jobcategories.entities;

import com.metro.audit.Auditable;
import com.metro.setups.staff.jobsubcategories.entities.JobSubCategories;
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
@Table(name = "JOB_CATEGORY")
public class JobCategory extends Auditable {
    @Column(name = "JOB_NAME")
    private String name;

    @OneToMany
    @JoinColumn(name = "JOB_SUBCATEGORY_ID")
    private List<JobSubCategories> jobSubCategories = new ArrayList<>();

}
