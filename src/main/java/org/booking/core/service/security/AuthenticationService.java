package org.booking.core.service.security;

import org.booking.core.domain.request.AuthenticationRequest;
import org.booking.core.domain.request.AuthenticationResponse;
import org.booking.core.domain.request.BaseRegisterRequest;

public interface AuthenticationService {

	AuthenticationResponse register(BaseRegisterRequest baseRegisterRequest);

	AuthenticationResponse businessRegister(BaseRegisterRequest baseRegisterRequest);

	AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
