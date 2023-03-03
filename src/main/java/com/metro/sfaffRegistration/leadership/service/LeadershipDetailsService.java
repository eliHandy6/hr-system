package com.metro.sfaffRegistration.leadership.service;

import com.metro.core.ApiResponse;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.leadership.data.LeadershipDetailsDTO;

public interface LeadershipDetailsService {
    ApiResponse createLeadershipDetails(LeadershipDetailsDTO leadershipDetailsDTO, Long id);
    ApiResponse updateLeadershipDetails(Long id, LeadershipDetailsDTO leadershipDetailsDTO, Long leadership_id);
    ApiResponse selectLeadershipDetailsByID(Long id);
    boolean leadershipDetailsExists(Employee employee);

}
