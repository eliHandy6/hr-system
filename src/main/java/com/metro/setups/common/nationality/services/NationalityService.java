package com.metro.setups.common.nationality.services;

import com.metro.core.ApiResponse;
import com.metro.setups.common.nationality.dtos.NationalityDto;


/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface NationalityService {

    ApiResponse createNationality(NationalityDto ethnicGroupDto);

    ApiResponse editNationality(Long id, NationalityDto ethnicGroupDto);

    ApiResponse getAllNationalities();

    ApiResponse deleteNationality(Long id);

    ApiResponse selectNationalityByName(String name);

    ApiResponse selectByNationalityId(Long id);

    boolean existsByNationalityName(String name);
}
