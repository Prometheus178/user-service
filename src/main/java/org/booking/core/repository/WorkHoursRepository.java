package org.booking.core.repository;

import org.booking.core.domain.entity.work.WorkHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkHoursRepository extends JpaRepository<WorkHours, Long> {
}
