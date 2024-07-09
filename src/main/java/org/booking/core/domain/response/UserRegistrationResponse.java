package org.booking.core.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationResponse {
	private String correlationId;
	private String status;
	private String message;
}
