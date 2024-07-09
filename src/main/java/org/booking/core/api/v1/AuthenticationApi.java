package org.booking.core.api.v1;

import lombok.RequiredArgsConstructor;
import org.booking.core.domain.request.AuthenticationRequest;
import org.booking.core.domain.request.TokenRequest;
import org.booking.core.domain.request.BaseRegisterRequest;
import org.booking.core.domain.response.AuthenticationResponse;
import org.booking.core.domain.response.LoggedInResponse;
import org.booking.core.service.AuthenticationServiceBean;
import org.booking.core.service.JWTAuthenticationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
		path = "/api/v1/auth",
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationApi {

	private final AuthenticationServiceBean authenticationServiceBean;
	private final JWTAuthenticationService jwtAuthenticationService;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> clientRegister(
			@RequestBody BaseRegisterRequest baseRegisterRequest) {
		return ResponseEntity.ok(authenticationServiceBean.register(baseRegisterRequest));
	}

	@PutMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticationRequest authenticationRequest) {
		return ResponseEntity.ok(authenticationServiceBean.authenticate(authenticationRequest));
	}

}
