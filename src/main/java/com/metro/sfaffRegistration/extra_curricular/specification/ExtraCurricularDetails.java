package com.metro.sfaffRegistration.extra_curricular.specification;

import com.metro.audit.Auditable;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
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
@Table(name = "EXTRA_CURRICULAR_DETAILS")
public class ExtraCurricularDetails extends Auditable {
    @Column(name = "SOCIAL_CLUB_NAME", nullable = false)
    private String socialClubName;
    @Column(name = "SPORT_NAME", nullable = false)
    private String sportName;
    @Column(name = "HOBBY_NAME", nullable = false)
    private String hobbyName;
    @Column(name = "ACHIEVEMENT_DETAILS", nullable = false)
    private String achievementDetails;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
