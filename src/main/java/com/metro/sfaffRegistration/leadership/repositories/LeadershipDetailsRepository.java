package com.metro.sfaffRegistration.leadership.repositories;

import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import com.metro.sfaffRegistration.leadership.specification.LeadershipDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadershipDetailsRepository extends JpaRepository<LeadershipDetails, Long> {
    List<LeadershipDetails> findLeadershipDetailsByEmployee(Employee employee);
    boolean existsLeadershipDetailsByEmployee(Employee employee);

}
