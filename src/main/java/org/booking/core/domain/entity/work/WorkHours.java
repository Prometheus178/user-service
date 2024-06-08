package org.booking.core.domain.entity.work;

import jakarta.persistence.*;
import lombok.*;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity(name = WorkHours.ENTITY_NAME)
@Table(name = WorkHours.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
public class WorkHours extends AbstractEntity {


	public static final String ENTITY_NAME = "WorkHours";
	public static final String TABLE_NAME = "work_hours";

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
}
