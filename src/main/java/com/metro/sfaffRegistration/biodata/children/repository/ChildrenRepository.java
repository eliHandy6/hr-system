package com.metro.sfaffRegistration.biodata.children.repository;

import com.metro.sfaffRegistration.biodata.children.specifications.Children;
import com.metro.sfaffRegistration.biodata.employeetest.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildrenRepository extends JpaRepository<Children, Long> {
    List<Children> findChildrenByFirstNameIgnoreCaseOrLastNameIgnoreCaseOrSecondNameIgnoreCase(String fname, String sname, String lname);
    boolean existsByFirstNameIgnoreCaseOrSecondNameIgnoreCaseOrLastNameIgnoreCase(String fname, String sname, String lname);

    List<Children> findChildrenByEmployee(Employee employee);
}
