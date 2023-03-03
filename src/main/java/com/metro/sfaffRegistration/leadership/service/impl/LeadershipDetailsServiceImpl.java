package com.metro.sfaffRegistration.leadership.service.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.biodata.employeetest.repository.EmployeeRepository;
import com.metro.sfaffRegistration.leadership.data.LeadershipDetailsDTO;
import com.metro.sfaffRegistration.leadership.repositories.LeadershipDetailsRepository;
import com.metro.sfaffRegistration.leadership.service.LeadershipDetailsService;
import com.metro.sfaffRegistration.leadership.specification.LeadershipDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class LeadershipDetailsServiceImpl implements LeadershipDetailsService {
    private final EmployeeRepository employeeRepository;
    private final LeadershipDetailsRepository leadershipDetailsRepository;

    public LeadershipDetailsServiceImpl(EmployeeRepository employeeRepository, LeadershipDetailsRepository leadershipDetailsRepository) {
        this.employeeRepository = employeeRepository;
        this.leadershipDetailsRepository = leadershipDetailsRepository;
    }

    @Override
    public ApiResponse createLeadershipDetails(LeadershipDetailsDTO leadershipDetailsDTO, Long id) {
        log.info("Saving leadership details for staff with id ", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to Save Leadership details for employee")
                .success(false)
                .data(leadershipDetailsDTO)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        LeadershipDetails leadershipDetails = LeadershipDetails.builder()
                .beginDate(leadershipDetailsDTO.getBeginDate())
                .endDate(leadershipDetailsDTO.getEndDate())
                .clubName(leadershipDetailsDTO.getClubName())
                .positionName(leadershipDetailsDTO.getPositionName())
                .achievementDetails(leadershipDetailsDTO.getAchievementDetails())
                .employee(employee)
                .build();
        leadershipDetails = leadershipDetailsRepository.save(leadershipDetails);
        if (StringUtils.isNotEmpty(leadershipDetails.getId().toString())) {
            leadershipDetailsDTO.setId(Long.valueOf(leadershipDetails.getId().longValue()));
            leadershipDetailsDTO.setEmployee_id(leadershipDetails.getEmployee().getId());
            response.setMessage("Successfully Saved Leadership Details of the Employee");
            response.setData(leadershipDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }
    //update take leadership id in
    @Override
    public ApiResponse updateLeadershipDetails(Long id, LeadershipDetailsDTO leadershipDetailsDTO, Long leadership_id) {
        log.info("Updating leadership details for staff with id ", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update Leadership details for employee")
                .success(false)
                .data(leadershipDetailsDTO)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        List<LeadershipDetails> list = leadershipDetailsRepository.findLeadershipDetailsByEmployee(employee);
        boolean state = false;
        for(LeadershipDetails details : list){
            if (details.getId() == leadership_id) state = true;
        }
        if(!state) throw new ResourceNotFoundException("Leadership position id does not fall in the category of this employee");
        LeadershipDetails leadershipDetails = leadershipDetailsRepository.findById(leadership_id).orElseThrow(
                () -> new ResourceNotFoundException("Leadership details not found")
        );
        leadershipDetails.setAchievementDetails(leadershipDetailsDTO.getAchievementDetails());
        leadershipDetails.setClubName(leadershipDetailsDTO.getClubName());
        leadershipDetails.setPositionName(leadershipDetailsDTO.getPositionName());
        leadershipDetails.setEndDate(leadershipDetailsDTO.getEndDate());
        leadershipDetails.setBeginDate(leadershipDetailsDTO.getBeginDate());
        leadershipDetails = leadershipDetailsRepository.save(leadershipDetails);
        if (StringUtils.isNotEmpty(leadershipDetails.getId().toString())) {
            leadershipDetailsDTO.setId(Long.valueOf(leadershipDetails.getId().longValue()));
            leadershipDetailsDTO.setEmployee_id(leadershipDetails.getEmployee().getId());
            response.setMessage("Successfully Updated the Leadership Details");
            response.setData(leadershipDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse selectLeadershipDetailsByID(Long id) {
        log.info("Getting Licenses and certification details for Staff with ID : ", id);
        List<LeadershipDetailsDTO> leadershipDetailsDTOList = new LinkedList<>();
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get all Leadership details for employee")
                .success(false)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        List<LeadershipDetails> list = leadershipDetailsRepository.findLeadershipDetailsByEmployee(employee);
        if (!list.isEmpty()) {
            leadershipDetailsDTOList.addAll(list.stream().map(e -> {
                LeadershipDetailsDTO leadershipDetailsDTO = LeadershipDetailsDTO.builder()
                        .Id(e.getId())
                        .clubName(e.getClubName())
                        .positionName(e.getPositionName())
                        .beginDate(e.getBeginDate())
                        .endDate(e.getEndDate())
                        .achievementDetails(e.getAchievementDetails())
                        .employee_id(e.getEmployee().getId())
                        .build();
                return leadershipDetailsDTO;
            }).toList());
            response.setSuccess(true);
            response.setMessage("Retrieved all Leadership Details for Employee with ID "+ id);
            response.setData(leadershipDetailsDTOList);
        }
        return response;
    }

    @Override
    public boolean leadershipDetailsExists(Employee employee) {
        return  leadershipDetailsRepository.existsLeadershipDetailsByEmployee(employee);
    }
}
