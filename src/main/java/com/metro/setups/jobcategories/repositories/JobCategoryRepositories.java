package com.metro.setups.jobcategories.repositories;

import com.metro.setups.jobcategories.entities.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
 **/
@Repository
public interface JobCategoryRepositories extends JpaRepository<JobCategory, Long> {
    List<JobCategory> findJobCategoriesByNameIgnoreCase(String name);

    boolean existsJobCategoriesByNameIgnoreCase(String name);
}
