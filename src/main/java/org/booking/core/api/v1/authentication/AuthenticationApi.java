package org.booking.core.api.v1.authentication;

import lombok.RequiredArgsConstructor;
import org.booking.core.domain.request.AuthenticationRequest;
import org.booking.core.domain.request.AuthenticationResponse;
import org.booking.core.domain.request.BaseRegisterRequest;
import org.booking.core.service.security.AuthenticationServiceBean;
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

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> clientRegister(
			@RequestBody BaseRegisterRequest baseRegisterRequest) {
		return ResponseEntity.ok(authenticationServiceBean.register(baseRegisterRequest));
	}

	@PostMapping("/business/register")
	public ResponseEntity<AuthenticationResponse> businessRegister(
			@RequestBody BaseRegisterRequest baseRegisterRequest) {
		return ResponseEntity.ok(authenticationServiceBean.businessRegister(baseRegisterRequest));
	}

	@PutMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticationRequest authenticationRequest) {
		return ResponseEntity.ok(authenticationServiceBean.authenticate(authenticationRequest));
	}
}
