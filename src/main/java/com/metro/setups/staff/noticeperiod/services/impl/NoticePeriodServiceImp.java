package com.metro.setups.staff.noticeperiod.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.staff.noticeperiod.dtos.NoticePeriodDto;
import com.metro.setups.staff.noticeperiod.repositories.NoticePeriodRepository;
import com.metro.setups.staff.noticeperiod.services.NoticePeriodService;
import com.metro.setups.staff.noticeperiod.specifications.NoticePeriod;
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
public class NoticePeriodServiceImp implements NoticePeriodService {

    private final NoticePeriodRepository noticePeriodRepository;

    @Override
    public ApiResponse createNoticePeriod(NoticePeriodDto noticePeriodDto) {
        log.info("Saving notice period{}", noticePeriodDto.getNoticePeriod());
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the notice period")
                .success(false)
                .data(noticePeriodDto)
                .build();
        if (existsNoticePeriodByName(noticePeriodDto.getNoticePeriod())) {
            throw new DuplicateResourceException(
                    "notice period is existing"
            );
        }
        NoticePeriod noticePeriod = NoticePeriod.builder()
                .noticePeriodName(noticePeriodDto.getNoticePeriod())
                .build();

        noticePeriod = noticePeriodRepository.save(noticePeriod);
        if (StringUtils.isNotEmpty(noticePeriod.getId().toString())) {
            noticePeriodDto.setNoticePeriodId(Long.valueOf(noticePeriod.getId().longValue()));
            response.setMessage("notice period inserted  successfully");
            response.setData(noticePeriodDto);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateNoticePeriod(Long id, NoticePeriodDto noticePeriodDto) {
        log.info("updating notice period {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update notice period ")
                .success(false)
                .data(noticePeriodDto)
                .build();

        NoticePeriod noticePeriod = noticePeriodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "notice period not found with id  " + id));

        if (existsNoticePeriodByName(noticePeriodDto.getNoticePeriod())) {
            throw new DuplicateResourceException(
                    "notice period is existing"
            );
        }

        noticePeriod.setNoticePeriodName(noticePeriodDto.getNoticePeriod());
        noticePeriod = noticePeriodRepository.save(noticePeriod);

        if (StringUtils.isNotEmpty(noticePeriod.getId().toString())) {
            noticePeriodDto.setNoticePeriodId(Long.valueOf(noticePeriod.getId().longValue()));
            response.setMessage("notice period  updated  successfully");
            response.setData(noticePeriodDto);
            response.setSuccess(true);
        }

        return response;
    }

    @Override
    public ApiResponse getNoticePeriods() {
        log.info("fetching  notice period {}");
        List<NoticePeriodDto> noticePeriodDtos = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(noticePeriodDtos)
                .message("No record is existing")
                .build();

        List<NoticePeriod> noticePeriodList = noticePeriodRepository.findAll(Sort.by("noticePeriodName").ascending());
        if (!noticePeriodList.isEmpty()) {
            noticePeriodDtos.addAll(noticePeriodList.stream().map(e -> {
                NoticePeriodDto noticePeriodDto = NoticePeriodDto.builder()
                        .noticePeriod(e.getNoticePeriodName())
                        .noticePeriodId(e.getId())
                        .build();
                return noticePeriodDto;
            }).toList());
            response.setMessage(" notice period fetched  successfully");
            response.setData(noticePeriodDtos);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse deleteNoticePeriod(Long id) {
        return null;
    }

    @Override
    public ApiResponse selectNoticePeriodByName(String name) {
        log.info("fetching   notice period  {}");
        List<NoticePeriodDto> noticePeriodDtos = new ArrayList<>();
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .data(noticePeriodDtos)
                .message("No record is existing")
                .build();

        List<NoticePeriod> noticePeriodList = noticePeriodRepository.findByNoticePeriodNameContainingIgnoreCase(name.trim());
        if (!noticePeriodList.isEmpty()) {
            noticePeriodDtos.addAll(noticePeriodList.stream().map(e -> {
                NoticePeriodDto noticePeriodDto = NoticePeriodDto.builder()
                        .noticePeriod(e.getNoticePeriodName())
                        .noticePeriodId(e.getId())
                        .build();
                return noticePeriodDto;
            }).toList());
            response.setMessage(" notice period fetched  successfully");
            response.setData(noticePeriodDtos);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse selectNoticePeriodById(Long id) {
        log.info("selecting  notice period {}", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to fetch  notice period ")
                .success(false)
                .build();
        NoticePeriod noticePeriod = noticePeriodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                " notice period  not found with id  " + id));
        if (StringUtils.isNotEmpty(noticePeriod.getId().toString())) {
            NoticePeriodDto noticePeriodDto = NoticePeriodDto.builder()
                    .noticePeriod(noticePeriod.getNoticePeriodName())
                    .noticePeriodId(noticePeriod.getId())
                    .build();
            response.setMessage("notice period fetched  successfully");
            response.setData(noticePeriodDto);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public boolean existsNoticePeriodByName(String name) {
        return noticePeriodRepository.existsNoticePeriodByNoticePeriodNameContainingIgnoreCase(name);
    }
}
