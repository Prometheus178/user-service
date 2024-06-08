package org.booking.core.domain.entity.work;

import jakarta.persistence.*;
import lombok.*;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.user.User;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@Entity(name = BreakTimes.ENTITY_NAME)
@Table(name = BreakTimes.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
public class BreakTimes extends AbstractEntity {

	public static final String ENTITY_NAME = "BreakTimes";
	public static final String TABLE_NAME = "break_times";

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
}
