package com.metro.setups.biodatasetups.kin.services;

import com.metro.core.ApiResponse;
import com.metro.setups.biodatasetups.children.dtos.ChildrenDTO;
import com.metro.setups.biodatasetups.kin.dtos.NextOfKinDTO;

public interface NextOfKinService {
    ApiResponse createNextOfKinDetails(NextOfKinDTO nextOfKinDTO);

    ApiResponse updateNextOfKinDetails(Long id, NextOfKinDTO nextOfKinDTO);

    ApiResponse getNextOfKinDetails();

    ApiResponse selectNextOfKinDetailsByName(String name);

    ApiResponse selectNextOfKinDetailsByID(Long id);

    boolean nextOfKinDetailsExists(String name);
}
