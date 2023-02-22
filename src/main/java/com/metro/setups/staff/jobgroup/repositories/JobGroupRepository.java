package com.metro.setups.staff.jobgroup.repositories;

import com.metro.setups.staff.jobgroup.specifications.JobGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface JobGroupRepository extends JpaRepository<JobGroup, Long> {

    boolean existsJobGroupByJobGroupNameContainingIgnoreCase(String name);

    List<JobGroup> findByJobGroupNameContainingIgnoreCase(String name);
}
