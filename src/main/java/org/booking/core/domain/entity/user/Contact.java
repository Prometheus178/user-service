package org.booking.core.domain.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.booking.core.domain.entity.base.AbstractEntity;

@Getter
@Setter
@Builder
@Entity(name = Contact.ENTITY_NAME)
@Table(name = Contact.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
public class Contact extends AbstractEntity   {

	public static final String ENTITY_NAME = "Contact";
	public static final String TABLE_NAME = "contacts";

	private String phoneNumber;
	private String workPhoneNumber;
	private String travelPhoneNumber;

}
