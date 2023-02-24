package com.metro.setups.shifdetails.services;

import com.metro.core.ApiResponse;
import com.metro.setups.shifdetails.dtos.ShiftDetailsDTO;

public interface ShiftDetailsService {
    ApiResponse createShiftDetails(ShiftDetailsDTO shiftDetailsDTO);

    ApiResponse updateShiftDetails(Long id,  ShiftDetailsDTO shiftDetailsDTO);

    ApiResponse getShiftDetails();

    ApiResponse selectShiftDetailsByName(String name);

    ApiResponse selectShiftDetailsByID(Long id);
    boolean shiftDetailsExists(String name);
}
