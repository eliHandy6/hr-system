package com.metro.setups.sections.respositories;

import com.metro.setups.sections.Entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findSectionByNameIgnoreCase(String name);
    boolean existsSectionByNameIgnoreCase(String name);
}
