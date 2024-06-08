package org.booking.core.repository;

import org.booking.core.domain.entity.work.NonWorkingDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NonWorkingDaysRepository extends JpaRepository<NonWorkingDays, Long> {
}
