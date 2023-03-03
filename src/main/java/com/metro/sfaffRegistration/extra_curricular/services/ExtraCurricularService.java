package com.metro.sfaffRegistration.extra_curricular.services;

import com.metro.core.ApiResponse;
import com.metro.sfaffRegistration.extra_curricular.dtos.ExtraCurricularDetailsDTO;
import com.metro.sfaffRegistration.extra_curricular.specification.ExtraCurricularDetails;
import com.metro.sfaffRegistration.leadership.data.LeadershipDetailsDTO;

public interface ExtraCurricularService {
    ApiResponse createExtraCurricularDetails(ExtraCurricularDetailsDTO extraCurricularDetailsDTO, Long id);
    ApiResponse updateExtraCurricularDetails(Long id, ExtraCurricularDetailsDTO extraCurricularDetailsDTO, Long curricular_id);
    ApiResponse selectExtraCurricularDetailsByID(Long id);
}
