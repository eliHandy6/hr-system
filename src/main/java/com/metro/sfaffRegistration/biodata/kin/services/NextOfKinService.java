package com.metro.sfaffRegistration.biodata.kin.services;

import com.metro.core.ApiResponse;
import com.metro.sfaffRegistration.biodata.kin.dtos.NextOfKinDTO;

public interface NextOfKinService {
    ApiResponse createNextOfKinDetails(NextOfKinDTO nextOfKinDTO, Long id);
    ApiResponse updateNextOfKinDetails(Long id, NextOfKinDTO nextOfKinDTO, Long kin_id);
    ApiResponse selectNextOfKinDetailsByID(Long id);

}
