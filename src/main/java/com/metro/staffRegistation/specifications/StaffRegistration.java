package com.metro.staffRegistation.specifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.metro.audit.Auditable;
import com.metro.setups.common.titles.Entity.Titles;
import com.metro.setups.department.entities.Department;
import com.metro.setups.designation.entities.Designation;

import com.metro.setups.sections.Entity.Section;
import com.metro.setups.staff.businessunit.specifications.BusinessUnit;
import com.metro.setups.staff.category.specifications.StaffCategory;
import com.metro.setups.staff.jobcategories.entities.JobCategory;
import com.metro.setups.staff.jobgroup.specifications.JobGroup;
import com.metro.setups.staff.jobsubcategories.entities.JobSubCategories;
import com.metro.setups.staff.leavecategory.specifications.LeaveCategory;
import com.metro.setups.staff.noticeperiod.specifications.NoticePeriod;
import com.metro.setups.staff.payrollgroup.specifications.PayrollGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

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
@Table(name = "staff_registration")
public class StaffRegistration extends Auditable {


    @JoinColumn(name = "staff_category_id")
    @ManyToOne
    private StaffCategory staffCategoryId;

    @Column(name = "staff_first_name")
    private String firstName;

    @Column(name = "staff_second_name")
    private String secondName;


    @Column(name = "staff_other_names")
    private String otherNames;

    @JoinColumn(name = "staff_title")
    @ManyToOne
    private Titles titleId;

    @JoinColumn(name = "staff_job_category_id")
    @ManyToOne
    private JobCategory jobCategoryId;


    @JoinColumn(name = "staff_job_sub_category_id")
    @ManyToOne
    private JobSubCategories jobSubCategoryId;


    @JoinColumn(name = "staff_business_unit_id")
    @ManyToOne
    private BusinessUnit businessUnitId;


    @JoinColumn(name = "staff_notice_period_id")
    @ManyToOne
    private NoticePeriod noticePeriodId;


    @JoinColumn(name = "staff_payroll_group_id")
    @ManyToOne
    private PayrollGroup payrollGroupId;


    @JoinColumn(name = "staff_department_id")
    @ManyToOne
    private Department departmentId;


    @JoinColumn(name = "staff_section_id")
    @ManyToOne
    private Section sectionId;

    @JoinColumn(name = "staff_designation_id")
    @ManyToOne
    private Designation designationId;


    @JoinColumn(name = "staff_job_group_id")
    @ManyToOne
    private JobGroup staffJobGroupId;


    @JoinColumn(name = "staff_leave_category_id")
    @ManyToOne
    private LeaveCategory staffLeaveCategoryId;

    @Column(name = "staff_interview_date")
    private LocalDate interviewDate;


    @Column(name = "staff_reporting_date")
    private LocalDate reportDate;


    @Column(name = "staff_field_of_specialization")
    private String specialization;


    @Column(name = "staff_postion_applied")
    private String positionAppliedFor;
}
