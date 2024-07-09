package org.booking.core.service;

import org.booking.core.domain.request.AuthenticationRequest;
import org.booking.core.domain.request.TokenRequest;
import org.booking.core.domain.request.BaseRegisterRequest;
import org.booking.core.domain.response.AuthenticationResponse;
import org.booking.core.domain.response.LoggedInResponse;

public interface AuthenticationService {

	AuthenticationResponse register(BaseRegisterRequest baseRegisterRequest);

	AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
