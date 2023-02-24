package com.metro.setups.staff.noticeperiod.repositories;

import com.metro.setups.staff.noticeperiod.specifications.NoticePeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface NoticePeriodRepository extends JpaRepository<NoticePeriod, Long> {

    boolean existsNoticePeriodByNoticePeriodNameContainingIgnoreCase(String name);

    List<NoticePeriod> findByNoticePeriodNameContainingIgnoreCase(String name);
}
