package com.metro.setups.shifdetails.entities;

import com.metro.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SHIFT_DETAILS", schema = "hrm")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShiftDetails extends Auditable {
    @NotNull(message = "Title name is missing")
    @Column(name = "SHIFT_DETAIL_NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
}
