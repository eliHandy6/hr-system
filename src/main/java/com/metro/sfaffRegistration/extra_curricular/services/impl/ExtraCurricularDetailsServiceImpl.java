package com.metro.sfaffRegistration.extra_curricular.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.biodata.employeetest.repository.EmployeeRepository;
import com.metro.sfaffRegistration.extra_curricular.dtos.ExtraCurricularDetailsDTO;
import com.metro.sfaffRegistration.extra_curricular.repositories.ExtraCurricularDetailsRepository;
import com.metro.sfaffRegistration.extra_curricular.services.ExtraCurricularService;
import com.metro.sfaffRegistration.extra_curricular.specification.ExtraCurricularDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class ExtraCurricularDetailsServiceImpl implements ExtraCurricularService {
    private final EmployeeRepository employeeRepository;
    private final ExtraCurricularDetailsRepository extraCurricularDetailsRepository;

    public ExtraCurricularDetailsServiceImpl(EmployeeRepository employeeRepository, ExtraCurricularDetailsRepository extraCurricularDetailsRepository) {
        this.employeeRepository = employeeRepository;
        this.extraCurricularDetailsRepository = extraCurricularDetailsRepository;
    }


    @Override
    public ApiResponse createExtraCurricularDetails(ExtraCurricularDetailsDTO extraCurricularDetailsDTO, Long id) {
        log.info("Saving Extra curricular details for employee with ID " + id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to Save Extra-Curricular details for employee")
                .success(false)
                .data(extraCurricularDetailsDTO)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        ExtraCurricularDetails extraCurricularDetails = ExtraCurricularDetails.builder()
                .achievementDetails(extraCurricularDetailsDTO.getAchievements())
                .hobbyName(extraCurricularDetailsDTO.getHobbyName())
                .sportName(extraCurricularDetailsDTO.getSportName())
                .socialClubName(extraCurricularDetailsDTO.getSocialClubName())
                .employee(employee)
                .build();
        extraCurricularDetails = extraCurricularDetailsRepository.save(extraCurricularDetails);
        if (StringUtils.isNotEmpty(extraCurricularDetails.getId().toString())) {
            extraCurricularDetailsDTO.setId(Long.valueOf(extraCurricularDetails.getId().longValue()));
            extraCurricularDetailsDTO.setEmployee_id(extraCurricularDetails.getEmployee().getId());
            response.setMessage("Successfully Saved Extra-curricular Details of the Employee");
            response.setData(extraCurricularDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse updateExtraCurricularDetails(Long id, ExtraCurricularDetailsDTO extraCurricularDetailsDTO, Long curricular_id) {
        log.info("Updating Extra Curricular details for staff with id ", id);
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update ExtraCurricular details for employee")
                .success(false)
                .data(extraCurricularDetailsDTO)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        List<ExtraCurricularDetails> list = extraCurricularDetailsRepository.findExtraCurricularDetailsByEmployee(employee);
        boolean state = false;
        for(ExtraCurricularDetails details : list){
            if (details.getId() == curricular_id) state = true;
        }
        if(!state) throw new ResourceNotFoundException("Cannot Modify another persons details");
        ExtraCurricularDetails extraCurricularDetails = extraCurricularDetailsRepository.findById(curricular_id).orElseThrow(
                () -> new ResourceNotFoundException("Resource to modify not found")
        );
        extraCurricularDetails.setAchievementDetails(extraCurricularDetailsDTO.getAchievements());
        extraCurricularDetails.setHobbyName(extraCurricularDetailsDTO.getHobbyName());
        extraCurricularDetails.setSportName(extraCurricularDetailsDTO.getSportName());
        extraCurricularDetails.setSocialClubName(extraCurricularDetailsDTO.getSocialClubName());
        extraCurricularDetails = extraCurricularDetailsRepository.save(extraCurricularDetails);
        if (StringUtils.isNotEmpty(extraCurricularDetails.getId().toString())) {
            extraCurricularDetailsDTO.setId(Long.valueOf(extraCurricularDetails.getId().longValue()));
            extraCurricularDetailsDTO.setEmployee_id(extraCurricularDetails.getEmployee().getId());
            response.setMessage("Successfully Updated the Extra curricular Details");
            response.setData(extraCurricularDetailsDTO);
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public ApiResponse selectExtraCurricularDetailsByID(Long id) {
        log.info("Retrieving Extra Curricular details for staff with id ", id);
        List<ExtraCurricularDetailsDTO> extraCurricularDetailsDTOList = new LinkedList<>();
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update Extra Curricular details for employee")
                .success(false)
                .build();
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"
                ));
        List<ExtraCurricularDetails> list = extraCurricularDetailsRepository.findExtraCurricularDetailsByEmployee(employee);
        if (!list.isEmpty()) {
            extraCurricularDetailsDTOList.addAll(list.stream().map(e -> {
                ExtraCurricularDetailsDTO extraCurricularDetailsDTO = ExtraCurricularDetailsDTO.builder()
                        .Id(e.getId())
                        .socialClubName(e.getSocialClubName())
                        .sportName(e.getSportName())
                        .hobbyName(e.getHobbyName())
                        .achievements(e.getAchievementDetails())
                        .employee_id(e.getEmployee().getId())
                        .build();
                return extraCurricularDetailsDTO;
            }).toList());
            response.setSuccess(true);
            response.setMessage("Retrieved all Extra-Curricular Details for Employee with ID "+ id);
            response.setData(extraCurricularDetailsDTOList);
        }
        return response;
    }
}
