package org.booking.core.api.v1.authentication;

import lombok.RequiredArgsConstructor;
import org.booking.core.domain.request.TokenRequest;
import org.booking.core.domain.response.LoggedInResponse;
import org.booking.core.service.JWTAuthenticationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
		path = "/api/v1/inner",
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class InnerAuthenticationApi {

	private final JWTAuthenticationService jwtAuthenticationService;

	@PutMapping("/authenticate")
	public ResponseEntity<LoggedInResponse> authenticate(TokenRequest tokenRequest){
		return ResponseEntity.ok(jwtAuthenticationService.authenticate(tokenRequest));
	}
}
