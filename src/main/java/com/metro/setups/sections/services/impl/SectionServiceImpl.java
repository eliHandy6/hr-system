package com.metro.setups.sections.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.EmptySpaceExceptionHandler;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.department.entities.Department;
import com.metro.setups.department.repositories.DepartmentRepository;
import com.metro.setups.sections.Entity.Section;
import com.metro.setups.sections.dtos.SectionDTO;
import com.metro.setups.sections.respositories.SectionRepository;
import com.metro.setups.sections.services.SectionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
 **/

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
        List<SectionDTO> sections = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(false)
                .data(sections)
                .message("No record is existing")
                .build();
        List<Section> section = sectionRepository.findAll(Sort.by("name").ascending());
        if (!section.isEmpty()) {
            sections.addAll(section.stream().map(e -> {
                SectionDTO sectionDTO = SectionDTO.builder()
                        .name(e.getName())
                        .sectionId(e.getId())
                        .department_id(e.getDepartment().getId())
                        .build();
                return sectionDTO;
            }).toList());
        }
        response.setMessage("sections fetched successfully");
        response.setData(sections);
        response.setSuccess(true);
        return response;
    }



    @Override
    public ApiResponse selectSectionByName(String name) {
        List<SectionDTO> sections = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .message("No section is existing")
                .success(false)
                .data(sections)
                .build();
        List<Section> res = sectionRepository.findSectionByNameIgnoreCase(name);
        if (!res.isEmpty()) {
            sections.addAll(res.stream().map(e -> {
                SectionDTO sectionDTO = SectionDTO.builder()
                        .name(e.getName())
                        .sectionId(e.getId())
                        .department_id(e.getDepartment().getId())
                        .build();
                return sectionDTO;
            }).toList());

        }
        response.setMessage("sections fetched successfully by name");
        response.setData(sections);
        response.setSuccess(true);
        return response;
    }

    @Override
    public ApiResponse selectSectionByID(Long id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch the section")
                .success(false)
                .build();

        Section section = sectionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "section with id not found  " + id));
        if (StringUtils.isNotEmpty(section.getId().toString())) {
            SectionDTO sectionDTO = SectionDTO.builder()
                    .name(section.getName())
                    .sectionId(section.getId())
                    .department_id(section.getDepartment().getId())
                    .build();
            sectionDTO.setSectionId(Long.valueOf(section.getId().longValue()));
            response.setMessage("Section fetched  successfully");
            response.setData(sectionDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public boolean sectionExists(String name) {
        return sectionRepository.existsSectionByNameIgnoreCase(name);
    }
}
