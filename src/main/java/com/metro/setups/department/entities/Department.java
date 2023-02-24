package com.metro.setups.department.entities;

import com.metro.audit.Auditable;
import com.metro.setups.sections.Entity.Section;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DEPARTMENT")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department extends Auditable {
    @NotNull(message = "Title name is missing")
    @Column(name = "TITLE_NAME")
    private String name;
    @Column(name = "MANAGER_ID")
    private Long manager_id;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> sections = new ArrayList<>();


    public Department(String name, Long manager_id) {
        this.name = name;
        this.manager_id = manager_id;
    }
}
