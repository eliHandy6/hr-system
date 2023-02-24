package com.metro.setups.staff.noticeperiod.services;

import com.metro.core.ApiResponse;
import com.metro.setups.staff.noticeperiod.dtos.NoticePeriodDto;


/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface NoticePeriodService {

    ApiResponse createNoticePeriod(NoticePeriodDto noticePeriodDto);

    ApiResponse updateNoticePeriod(Long id, NoticePeriodDto noticePeriodDto);

    ApiResponse getNoticePeriods();

    ApiResponse deleteNoticePeriod(Long id);

    ApiResponse selectNoticePeriodByName(String name);

    ApiResponse selectNoticePeriodById(Long id);

    boolean existsNoticePeriodByName(String name);
}
