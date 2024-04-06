package org.booking.core.api.v1.authentication;

import lombok.RequiredArgsConstructor;
import org.booking.core.domain.request.TokenRequest;
import org.booking.core.domain.response.LoggedInResponse;
import org.booking.core.service.JWTAuthenticationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
		path = "/api/v1/auth/inner",
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class InnerAuthenticationApi {

	private final JWTAuthenticationService jwtAuthenticationService;

	//todo call by registered user
	@PutMapping("/authenticate")
	public ResponseEntity<LoggedInResponse> authenticate(@RequestBody TokenRequest tokenRequest){
		return ResponseEntity.ok(jwtAuthenticationService.authenticate(tokenRequest));
	}
}
