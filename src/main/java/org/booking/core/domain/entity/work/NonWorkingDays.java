package org.booking.core.domain.entity.work;

import jakarta.persistence.*;
import lombok.*;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.user.User;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Entity(name = NonWorkingDays.ENTITY_NAME)
@Table(name = NonWorkingDays.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
public class NonWorkingDays extends AbstractEntity {

	public static final String ENTITY_NAME = "NonWorkingDays";
	public static final String TABLE_NAME = "non_working_days";

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	private String dayOfWeek;
	private LocalDate date;
}
