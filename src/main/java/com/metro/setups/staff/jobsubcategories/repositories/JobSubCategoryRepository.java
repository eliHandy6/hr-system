package com.metro.setups.staff.jobsubcategories.repositories;

import com.metro.setups.staff.jobsubcategories.entities.JobSubCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface JobSubCategoryRepository extends JpaRepository<JobSubCategories, Long> {
    List<JobSubCategories> findJobSubCategoriesByNameIgnoreCase(String name);
    List<JobSubCategories> getJobSubCategoriesByJobCategory_Id(Long id);

    boolean existsSectionByNameIgnoreCase(String name);
}
