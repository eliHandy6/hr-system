package com.metro.setups.staff.payroll.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.staff.payroll.dtos.PayrollGroupDto;
import com.metro.setups.staff.payroll.repositories.PayrollGroupRepository;
import com.metro.setups.staff.payroll.services.PayrollGroupService;
import com.metro.setups.staff.payroll.specifications.PayrollGroup;
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
public class PayrollGroupImpl implements PayrollGroupService {

    private final PayrollGroupRepository payrollGroupRepository;

    @Override
    public ApiResponse createPayrollGroup(PayrollGroupDto payrollGroupDto) {
        log.info("Saving payroll group{}", payrollGroupDto.getPayrollGroupName());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the payroll group")
                .success(false)
                .data(payrollGroupDto)
                .build();
        if (existsPayrollGroupByName(payrollGroupDto.getPayrollGroupName())) {
            throw new DuplicateResourceException(
                    "payroll group is existing"
            );
        }
        PayrollGroup payrollGroup = PayrollGroup.builder()
                .payrollGroupName(payrollGroupDto.getPayrollGroupName())
                .build();

        payrollGroup = payrollGroupRepository.save(payrollGroup);
        if (StringUtils.isNotEmpty(payrollGroup.getId().toString())) {
            payrollGroupDto.setPayrollGroupId(Long.valueOf(payrollGroup.getId().longValue()));
            response.setMessage("payroll group inserted  successfully");
            response.setData(payrollGroupDto);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updatePayrollGroup(Long id, PayrollGroupDto payrollGroupDto) {
        log.info("updating payroll group {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update payroll group ")
                .success(false)
                .data(payrollGroupDto)
                .build();

        PayrollGroup payrollGroup = payrollGroupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "payroll group not found with id  " + id));

        if (existsPayrollGroupByName(payrollGroupDto.getPayrollGroupName())) {
            throw new DuplicateResourceException(
                    "payroll group is existing"
            );
        }

        payrollGroup.setPayrollGroupName(payrollGroupDto.getPayrollGroupName());
        payrollGroup = payrollGroupRepository.save(payrollGroup);

        if (StringUtils.isNotEmpty(payrollGroup.getId().toString())) {
            payrollGroupDto.setPayrollGroupId(Long.valueOf(payrollGroup.getId().longValue()));
            response.setMessage("payroll group updated  successfully");
            response.setData(payrollGroupDto);
            response.setSuccess(true);
        }

        return response;
    }

    @Override
    public ApiResponse getPayrollGroups() {
        log.info("fetching  payroll groups  {}");
        List<PayrollGroupDto> payrollGroupDtos = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(payrollGroupDtos)
                .message("No record is existing")
                .build();

        List<PayrollGroup> payrollGroupList = payrollGroupRepository.findAll(Sort.by("payrollGroupName").ascending());
        if (!payrollGroupList.isEmpty()) {
            payrollGroupDtos.addAll(payrollGroupList.stream().map(e -> {
                PayrollGroupDto payrollGroupDto = PayrollGroupDto.builder()
                        .payrollGroupName(e.getPayrollGroupName())
                        .payrollGroupId(e.getId())
                        .build();
                return payrollGroupDto;
            }).toList());
            response.setMessage("payroll groups fetched  successfully");
            response.setData(payrollGroupDtos);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse deletePayrollGroup(Long id) {
        return null;
    }

    @Override
    public ApiResponse selectPayrollGroupByName(String name) {
        log.info("fetching  payroll  groups  {}");
        List<PayrollGroupDto> payrollGroupDtos = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(payrollGroupDtos)
                .message("No record is existing")
                .build();

        List<PayrollGroup> payrollGroupList = payrollGroupRepository.findByPayrollGroupNameContainingIgnoreCase(name.trim());
        if (!payrollGroupList.isEmpty()) {
            payrollGroupDtos.addAll(payrollGroupList.stream().map(e -> {
                PayrollGroupDto payrollGroupDto = PayrollGroupDto.builder()
                        .payrollGroupName(e.getPayrollGroupName())
                        .payrollGroupId(e.getId())
                        .build();
                return payrollGroupDto;
            }).toList());
            response.setMessage("payroll groups fetched  successfully");
            response.setData(payrollGroupDtos);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse selectPayrollGroupById(Long id) {
        log.info("selecting payroll group {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch payroll group  ")
                .success(false)
                .build();
        PayrollGroup payrollGroup = payrollGroupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "payroll group  not found with id  " + id));
        if (StringUtils.isNotEmpty(payrollGroup.getId().toString())) {
            PayrollGroupDto payrollGroupDto = PayrollGroupDto.builder()
                    .payrollGroupName(payrollGroup.getPayrollGroupName())
                    .payrollGroupId(payrollGroup.getId())
                    .build();
            response.setMessage("payroll group fetched  successfully");
            response.setData(payrollGroupDto);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public boolean existsPayrollGroupByName(String name) {
        return payrollGroupRepository.existsPayrollGroupByPayrollGroupNameContainingIgnoreCase(name);
    }
}
