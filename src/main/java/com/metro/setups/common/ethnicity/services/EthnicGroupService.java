package com.metro.setups.common.ethnicity.services;

import com.metro.core.ApiResponse;
import com.metro.setups.common.ethnicity.dtos.EthnicGroupDto;


/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface EthnicGroupService {

    ApiResponse createEthnicGroup(EthnicGroupDto ethnicGroupDto);

    ApiResponse updateEthnicGroup(Long id, EthnicGroupDto ethnicGroupDto);

    ApiResponse getEthnicGroups();

    ApiResponse deleteEthnicGroup(Long id);

    ApiResponse selectEthnicGroupByName(String name);

    ApiResponse selectEthnicGroupByID(Long id);

    boolean existsEthnicGroupName(String name);
}
