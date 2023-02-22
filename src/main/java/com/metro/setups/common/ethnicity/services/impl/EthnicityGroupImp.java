package com.metro.setups.common.ethnicity.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;

import com.metro.setups.common.ethnicity.dtos.EthnicGroupDto;
import com.metro.setups.common.ethnicity.repositories.EthnicGroupRepository;
import com.metro.setups.common.ethnicity.services.EthnicGroupService;
import com.metro.setups.common.ethnicity.specifications.EthnicGroup;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/

@Service
@RequiredArgsConstructor
@Slf4j
public class EthnicityGroupImp implements EthnicGroupService {

    private final EthnicGroupRepository ethnicGroupRepository;

    @Override
    public ApiResponse createEthnicGroup(EthnicGroupDto ethnicGroupDto) {
        log.info("Saving ethnic group{}", ethnicGroupDto.getEthnicGroupName());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the ethnic group")
                .success(false)
                .data(ethnicGroupDto)
                .build();
        if (existsEthnicGroupName(ethnicGroupDto.getEthnicGroupName())) {
            throw new DuplicateResourceException(
                    "ethnic group is existing"
            );
        }
        EthnicGroup ethnicGroup = EthnicGroup.builder()
                .ethnicGroupName(ethnicGroupDto.getEthnicGroupName())
                .build();

        ethnicGroup = ethnicGroupRepository.save(ethnicGroup);
        if (StringUtils.isNotEmpty(ethnicGroup.getId().toString())) {
            ethnicGroupDto.setEthnicGroupId(Long.valueOf(ethnicGroup.getId().longValue()));
            response.setMessage("ethnic group inserted  successfully");
            response.setData(ethnicGroupDto);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateEthnicGroup(Long id, EthnicGroupDto ethnicGroupDto) {
        log.info("updating ethnic group {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update ethnic group ")
                .success(false)
                .data(ethnicGroupDto)
                .build();

        EthnicGroup ethnicGroup = ethnicGroupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "ethnic group not found with id  " + id));

        if (existsEthnicGroupName(ethnicGroupDto.getEthnicGroupName())) {
            throw new DuplicateResourceException(
                    "ethnic group is existing"
            );
        }

        ethnicGroup.setEthnicGroupName(ethnicGroupDto.getEthnicGroupName());
        ethnicGroup = ethnicGroupRepository.save(ethnicGroup);

        if (StringUtils.isNotEmpty(ethnicGroup.getId().toString())) {
            ethnicGroupDto.setEthnicGroupId(Long.valueOf(ethnicGroup.getId().longValue()));
            response.setMessage("ethnic group updated  successfully");
            response.setData(ethnicGroupDto);
            response.setSuccess(true);
        }

        return response;
    }

    @Override
    public ApiResponse getEthnicGroups() {
        log.info("fetching  ethnic groups categories {}");
        List<EthnicGroupDto> ethnicGroupDtos = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(ethnicGroupDtos)
                .message("No record is existing")
                .build();

        List<EthnicGroup> ethnicGroupList = ethnicGroupRepository.findAll(Sort.by("ethnicGroupName").ascending());
        if (!ethnicGroupList.isEmpty()) {
            ethnicGroupDtos.addAll(ethnicGroupList.stream().map(e -> {
                EthnicGroupDto ethnicGroupDto = EthnicGroupDto.builder()
                        .ethnicGroupName(e.getEthnicGroupName())
                        .ethnicGroupId(e.getId())
                        .build();
                return ethnicGroupDto;
            }).toList());
            response.setMessage("Ethnic groups fetched  successfully");
            response.setData(ethnicGroupDtos);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse deleteEthnicGroup(Long id) {
        return null;
    }

    @Override
    public ApiResponse selectEthnicGroupByName(String name) {
        log.info("fetching  ethnic groups  {}");
        List<EthnicGroupDto> ethnicGroupDtos = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(ethnicGroupDtos)
                .message("No record is existing")
                .build();

        List<EthnicGroup> ethnicGroupList = ethnicGroupRepository.findByEthnicGroupNameContainingIgnoreCase(name.trim());
        if (!ethnicGroupList.isEmpty()) {
            ethnicGroupDtos.addAll(ethnicGroupList.stream().map(e -> {
                EthnicGroupDto ethnicGroupDto = EthnicGroupDto.builder()
                        .ethnicGroupName(e.getEthnicGroupName())
                        .ethnicGroupId(e.getId())
                        .build();
                return ethnicGroupDto;
            }).toList());
            response.setMessage("ethnic groups fetched  successfully");
            response.setData(ethnicGroupDtos);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse selectEthnicGroupByID(Long id) {
        log.info("selecting ethnic group {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch ethnic group status ")
                .success(false)
                .build();
        EthnicGroup ethnicGroup = ethnicGroupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "ethnic group  not found with id  " + id));
        if (StringUtils.isNotEmpty(ethnicGroup.getId().toString())) {
            EthnicGroupDto ethnicGroupDto = EthnicGroupDto.builder()
                    .ethnicGroupName(ethnicGroup.getEthnicGroupName())
                    .ethnicGroupId(ethnicGroup.getId())
                    .build();
            response.setMessage("ethnic group fetched  successfully");
            response.setData(ethnicGroupDto);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public boolean existsEthnicGroupName(String name) {
        return ethnicGroupRepository.existsEthnicGroupByEthnicGroupNameContainingIgnoreCase(name);
    }
}
