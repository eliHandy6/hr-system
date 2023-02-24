package com.metro.setups.shifdetails.services.impl;

import com.metro.core.ApiResponse;
import com.metro.setups.shifdetails.dtos.ShiftDetailsDTO;
import com.metro.setups.shifdetails.repositories.ShiftDetailRepository;
import com.metro.setups.shifdetails.services.ShiftDetailsService;

public class ShiftDetailsServiceImpl implements ShiftDetailsService {
    private final ShiftDetailRepository shiftDetailRepository;

    public ShiftDetailsServiceImpl(ShiftDetailRepository shiftDetailRepository) {
        this.shiftDetailRepository = shiftDetailRepository;
    }

    @Override
    public ApiResponse createShiftDetails(ShiftDetailsDTO shiftDetailsDTO) {
        return null;
    }

    @Override
    public ApiResponse updateShiftDetails(Long id, ShiftDetailsDTO shiftDetailsDTO) {
        return null;
    }

    @Override
    public ApiResponse getShiftDetails() {
        return null;
    }

    @Override
    public ApiResponse selectShiftDetailsByName(String name) {
        return null;
    }

    @Override
    public ApiResponse selectShiftDetailsByID(Long id) {
        return null;
    }

    @Override
    public boolean shiftDetailsExists(String name) {
        return false;
    }
}
