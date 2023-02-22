package com.metro.setups.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.EmptySpaceExceptionHandler;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.department.entities.Department;
import com.metro.setups.department.repositories.DepartmentRepository;
import com.metro.setups.sections.Entity.Section;
import com.metro.setups.sections.dtos.SectionDTO;
import com.metro.setups.sections.respositories.SectionRepository;
import com.metro.setups.services.SectionService;
import org.springframework.stereotype.Service;
@Service
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;
    private final DepartmentRepository departmentRepository;

    public SectionServiceImpl(SectionRepository sectionRepository, DepartmentRepository departmentRepository) {
        this.sectionRepository = sectionRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public ApiResponse createSection(SectionDTO sectionDTO) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to create section")
                .success(false)
                .data(sectionDTO)
                .build();
        if (sectionExists(sectionDTO.getName())) {
            throw new DuplicateResourceException(
                    "Section already exists"
            );
        }
        Department department = departmentRepository.findById(sectionDTO.getDepartment_id()).orElseThrow(
                () -> new ResourceNotFoundException("department with id " + sectionDTO.getDepartment_id() + " not found"
        ));
        Section section = Section.builder()
                .name(sectionDTO.getName())
                .department(department)
                .build();
        section = sectionRepository.save(section);
        sectionDTO.setSectionId(section.getId());
        response.setData(sectionDTO);
        response.setMessage("Created Section Successfully");
        response.setSuccess(true);
        return response;
    }

    @Override
    public ApiResponse updateSection(Long id, SectionDTO sectionDTO) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Failed to update the department")
                .success(false)
                .data(sectionDTO)
                .build();
        Section section = sectionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("section with id " + id + " not found"));
        if(sectionDTO.getName().trim().length() == 0) throw new EmptySpaceExceptionHandler("name of the section cannot be empty");
        section.setName(sectionDTO.getName());
        Department department = departmentRepository.findById(sectionDTO.getDepartment_id()).orElseThrow(
                () -> new ResourceNotFoundException("department with id " + sectionDTO.getDepartment_id() + " not found"
                ));
        section.setDepartment(department);
        section = sectionRepository.save(section);
        sectionDTO.setSectionId(section.getId());
        apiResponse.setData(sectionDTO);
        apiResponse.setMessage("Updated section Successfully");
        apiResponse.setSuccess(true);
        return apiResponse;
    }

    @Override
    public ApiResponse getSections() {
        return null;
    }

    @Override
    public ApiResponse deleteSectionById(Long id) {
        return null;
    }

    @Override
    public ApiResponse selectSectionByName(String name) {
        return null;
    }

    @Override
    public ApiResponse selectSectionByID(Long id) {
        return null;
    }

    @Override
    public boolean sectionExists(String name) {
        return sectionRepository.existsSectionByNameIgnoreCase(name);
    }
}
