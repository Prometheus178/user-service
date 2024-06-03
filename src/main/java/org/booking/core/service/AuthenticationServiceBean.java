package org.booking.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.validator.routines.EmailValidator;
import org.booking.core.domain.entity.role.Role;
import org.booking.core.domain.entity.role.RoleClassification;
import org.booking.core.domain.entity.token.Token;
import org.booking.core.domain.entity.user.User;
import org.booking.core.domain.request.AuthenticationRequest;
import org.booking.core.domain.request.BaseRegisterRequest;
import org.booking.core.domain.response.AuthenticationResponse;
import org.booking.core.repository.RoleRepository;
import org.booking.core.repository.TokenRepository;
import org.booking.core.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log
@RequiredArgsConstructor
@Service
public class AuthenticationServiceBean implements AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenRepository tokenRepository;
	private final TokenService tokenService;
	private final UserService userService;

	@Override
	public AuthenticationResponse register(BaseRegisterRequest baseRegisterRequest) {
		return register(baseRegisterRequest, RoleClassification.CUSTOMER);
	}

	@Override
	public AuthenticationResponse businessRegister(BaseRegisterRequest baseRegisterRequest) {
		return register(baseRegisterRequest, RoleClassification.MANAGER);
	}

	private AuthenticationResponse register(BaseRegisterRequest baseRegisterRequest, RoleClassification roleClassification) {
		User user = userService.create(baseRegisterRequest, roleClassification);
		var jwtToken = tokenService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
		String email = authenticationRequest.getEmail();
		var user = userRepository.findByEmail(email)
				.orElseThrow(
						() -> new UsernameNotFoundException(String.format("User with email %s not found", email))
				);

		boolean isEquals = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
		if (isEquals) {
			Optional<Token> optionalExistToken = tokenRepository.findByEmail(email);
			String jwtToken;
			if (optionalExistToken.isPresent()) {
				jwtToken = tokenService.refreshToken(optionalExistToken.get());
			} else {
				jwtToken = tokenService.generateToken(user);
			}
			return AuthenticationResponse.builder()
					.token(jwtToken)
					.build();
		} else {
			throw new RuntimeException("password is incorrect");
		}
	}

}
