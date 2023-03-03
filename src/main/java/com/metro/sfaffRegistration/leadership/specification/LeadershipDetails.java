package com.metro.sfaffRegistration.leadership.specification;

import com.metro.audit.Auditable;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
@Table(name = "LEADERSHIP_DETAILS")
public class LeadershipDetails extends Auditable {
    @Column(name = "CLUB_NAME", nullable = false)
    private String clubName;
    @Column(name = "POSITION_NAME", nullable = false)
    private String positionName;
    @Column(name = "BEGIN_DATE", nullable = false)
    private LocalDate beginDate;
    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;
    @Column(name = "ACHIEVEMENT_DETAILS", nullable = false)
    private String achievementDetails;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;
}
