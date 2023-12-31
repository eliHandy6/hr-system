package com.metro.setups.common.ethnicity.specifications;

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
@Table(name = "ethnic_group",schema = "hrm")
public class EthnicGroup extends Auditable {

    @Column(name = "ethnic_group_name", nullable = false)
    private String ethnicGroupName;
}
