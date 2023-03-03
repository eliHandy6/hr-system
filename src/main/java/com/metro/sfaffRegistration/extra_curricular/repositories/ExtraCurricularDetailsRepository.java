package com.metro.sfaffRegistration.extra_curricular.repositories;

import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.extra_curricular.specification.ExtraCurricularDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtraCurricularDetailsRepository extends JpaRepository<ExtraCurricularDetails, Long> {
    List<ExtraCurricularDetails> findExtraCurricularDetailsByEmployee(Employee employee);

}
