package com.metro.setups.staff.leavecategory.specifications;

import com.metro.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "leave_category")
public class LeaveCategory extends Auditable {

    @Column(name = "leave_category_name", nullable = false)
    private String leaveCategoryName;
}
