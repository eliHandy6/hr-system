package com.metro.setups.sections.services;

import com.metro.core.ApiResponse;
import com.metro.setups.sections.dtos.SectionDTO;

/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
 **/

public interface SectionService {
    ApiResponse createSection(SectionDTO sectionDTO);

    ApiResponse updateSection(Long id, SectionDTO sectionDTO);

    ApiResponse getSections();

    ApiResponse selectSectionByName(String name);

    ApiResponse selectSectionByID(Long id);
    boolean sectionExists(String name);
}
