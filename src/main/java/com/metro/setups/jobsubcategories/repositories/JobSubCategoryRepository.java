package com.metro.setups.jobsubcategories.repositories;

import com.metro.setups.jobsubcategories.entities.JobSubCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface JobSubCategoryRepository extends JpaRepository<JobSubCategories, Long> {
    List<JobSubCategories> findJobSubCategoriesByNameIgnoreCase(String name);
    List<JobSubCategories> getJobSubCategoriesByJobCategory_Id(Long id);

    boolean existsSectionByNameIgnoreCase(String name);
}
