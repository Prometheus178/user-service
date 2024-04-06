package org.booking.core.domain.entity.token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.booking.core.domain.entity.base.AbstractEntity;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = Token.ENTITY_NAME)
@Table(name = Token.TABLE_NAME)
public class Token extends AbstractEntity {

	public static final String TABLE_NAME = "tokens";
	public static final String ENTITY_NAME = "Token";

	private String email;

	@Column(length = 765)
	private String token;

}
