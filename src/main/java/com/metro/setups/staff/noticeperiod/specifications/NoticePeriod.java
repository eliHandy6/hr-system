package com.metro.setups.staff.noticeperiod.specifications;

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
@Table(name = "notice_period", schema="hrm")
public class NoticePeriod extends Auditable {

    @Column(name = "notice_period_name", nullable = false)
    private String noticePeriodName;
}
