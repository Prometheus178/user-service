package org.booking.core.repository;

import org.booking.core.domain.entity.work.BreakTimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreakTimesRepository extends JpaRepository<BreakTimes, Long> {
}
