package com.metro.setups.department.entities;

import com.metro.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DEPARTMENT")
@Data
@Builder
@NoArgsConstructor
public class Department extends Auditable {
    @NotNull(message = "Title name is missing")
    @Column(name = "TITLE_NAME")
    private String name;
    @Column(name = "MANAGER_ID")
    private Long manager_id;

    public Department(String name, Long manager_id) {
        this.name = name;
        this.manager_id = manager_id;
    }
}
