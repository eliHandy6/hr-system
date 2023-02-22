package com.metro.setups.services;

import com.metro.core.ApiResponse;
import com.metro.setups.sections.dtos.SectionDTO;
import com.metro.setups.staff.category.dtos.StaffCategoryDto;

public interface SectionService {
    ApiResponse createSection(SectionDTO sectionDTO);

    ApiResponse updateSection(Long id, StaffCategoryDto staffCategoryDto);

    ApiResponse getSections();

    ApiResponse deleteSectionById(Long id);

    ApiResponse selectSectionByName(String name);

    ApiResponse selectSectionByID(Long id);
    boolean sectionExists(String name);
}
