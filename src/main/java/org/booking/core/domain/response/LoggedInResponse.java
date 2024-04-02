package org.booking.core.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoggedInResponse {

	private boolean unauthorized = true;
	private String userEmail;
	private List<String> role;

}
