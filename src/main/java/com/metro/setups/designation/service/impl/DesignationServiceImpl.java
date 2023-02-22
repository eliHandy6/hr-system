package com.metro.setups.designation.service.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.EmptySpaceExceptionHandler;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.designation.dtos.DesignationDTO;
import com.metro.setups.designation.entities.Designation;
import com.metro.setups.designation.repository.DesignationRepository;
import com.metro.setups.designation.service.DesignationService;
import com.metro.setups.sections.Entity.Section;
import com.metro.setups.sections.dtos.SectionDTO;
import com.metro.setups.sections.respositories.SectionRepository;
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
public class DesignationServiceImpl implements DesignationService {
    private final DesignationRepository designationRepository;
    private final SectionRepository sectionRepository;

    public DesignationServiceImpl(DesignationRepository designationRepository, SectionRepository sectionRepository) {
        this.designationRepository = designationRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    public ApiResponse createDesignation(DesignationDTO designationDTO) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to create designation")
                .success(false)
                .data(designationDTO)
                .build();
        if (designationExists(designationDTO.getName())) {
            throw new DuplicateResourceException(
                    "designation already exists"
            );
        }
        Section section = sectionRepository.findById(designationDTO.getSection_id()).orElseThrow(
                () -> new ResourceNotFoundException("Designation with id " + designationDTO.getSection_id() + " not found"
                ));
        Designation designation = Designation.builder()
                .name(designationDTO.getName())
                .section(section)
                .build();
        designation = designationRepository.save(designation);
        designationDTO.setDesignationId(designation.getId());
        response.setData(designationDTO);
        response.setMessage("Created Designation Successfully");
        response.setSuccess(true);
        return response;
    }

    @Override
    public ApiResponse updateDesignation(Long id, DesignationDTO designationDTO) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update designation")
                .success(false)
                .data(designationDTO)
                .build();
        Designation designation = designationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("designation with id " + id + " not found"));
        if (designationDTO.getName().trim().length() == 0)
            throw new EmptySpaceExceptionHandler("name of the designation cannot be empty");
        designation.setName(designationDTO.getName());
        Section section = sectionRepository.findById(designationDTO.getSection_id()).orElseThrow(
                () -> new ResourceNotFoundException("section with id " + designationDTO.getSection_id() + " not found")
        );
        designation.setSection(section);
        designation = designationRepository.save(designation);
        designationDTO.setDesignationId(designation.getId());
        response.setData(designationDTO);
        response.setMessage("Updated designation Successfully");
        response.setSuccess(true);
        return response;
    }

    @Override
    public ApiResponse getDesignations() {
        List<DesignationDTO> designationDTOS = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(false)
                .data(designationDTOS)
                .message("No record is existing")
                .build();
        List<Designation> designations = designationRepository.findAll(Sort.by("name").ascending());
        if (!designations.isEmpty()) {
            designationDTOS.addAll(designations.stream().map(e -> {
                DesignationDTO designationDTO = DesignationDTO.builder()
                        .name(e.getName())
                        .designationId(e.getId())
                        .section_id(e.getSection().getId())
                        .build();
                return designationDTO;
            }).toList());
        }
        response.setMessage("designations fetched successfully");
        response.setData(designationDTOS);
        response.setSuccess(true);
        return response;
    }

    @Override
    public ApiResponse selectDesignationByName(String name) {
        List<DesignationDTO> designationDTOS = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .message("No Designation is existing")
                .success(false)
                .data(designationDTOS)
                .build();
        List<Designation> res = designationRepository.findDesignationByNameIgnoreCase(name);
        if (!res.isEmpty()) {
            designationDTOS.addAll(res.stream().map(e -> {
                DesignationDTO designationDTO = DesignationDTO.builder()
                        .name(e.getName())
                        .designationId(e.getId())
                        .section_id(e.getSection().getId())
                        .build();
                return designationDTO;
            }).toList());

        }
        response.setMessage("designation fetched successfully by name");
        response.setData(designationDTOS);
        response.setSuccess(true);
        return response;
    }

    @Override
    public ApiResponse selectDesignationByID(Long id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch the section")
                .success(false)
                .build();

        Designation designation = designationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "designation with id not found  " + id));
        if (StringUtils.isNotEmpty(designation.getId().toString())) {
            DesignationDTO designationDTO = DesignationDTO.builder()
                    .name(designation.getName())
                    .designationId(designation.getId())
                    .section_id(designation.getSection().getId())
                    .build();
            designationDTO.setDesignationId(Long.valueOf(designation.getId().longValue()));
            response.setMessage("Designation fetched  successfully");
            response.setData(designationDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public boolean designationExists(String name) {
        return designationRepository.existsDesignationByNameIgnoreCase(name);
    }
}
