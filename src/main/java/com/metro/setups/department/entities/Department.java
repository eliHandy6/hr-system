package com.metro.setups.department.entities;

import com.metro.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "DEPARTMENT")
public class Department extends Auditable {
    @NotNull(message = "Title name is missing")
    @Column(name = "TITLE_NAME")
    private String name;
    @Column(name = "MANAGER_ID")
    private Long manager_id;
}
