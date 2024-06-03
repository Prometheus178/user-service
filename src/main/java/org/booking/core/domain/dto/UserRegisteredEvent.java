package org.booking.core.domain.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserRegisteredEvent {
	private Long id;
	private String username;
	private String email;
	private Set<String> roles;
	private Date registeredAt;
}
