package org.booking.core.domain.entity.role;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.booking.core.domain.entity.base.AbstractEntity;

@Getter
@Setter
@Builder
@Entity(name = Role.ENTITY_NAME)
@Table(name = Role.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbstractEntity {

	public static final String ENTITY_NAME = "Role";
	public static final String TABLE_NAME = "roles";

	private String name;
}
