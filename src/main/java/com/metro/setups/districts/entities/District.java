package com.metro.setups.districts.entities;

import com.metro.audit.Auditable;
import com.metro.setups.department.entities.Department;
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
@Table(name = "DISTRICT_TABLE")
public class District extends Auditable {
    @Column(name = "DISTRICT_NAME")
    private String name;

}
