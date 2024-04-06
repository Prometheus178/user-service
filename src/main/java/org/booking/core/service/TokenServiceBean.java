package org.booking.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.booking.core.domain.entity.token.Token;
import org.booking.core.domain.entity.user.User;
import org.booking.core.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Log
@RequiredArgsConstructor
@Service
public class TokenServiceBean implements TokenService{

	private final JWTService jwtService;
	private final TokenRepository tokenRepository;

	@Override

	public String refreshToken(Token existToken) {
		var jwtToken = jwtService.refreshToken(existToken.getToken(), new Date());
		existToken.setToken(jwtToken);
		existToken.setDeleted(false);
		tokenRepository.save(existToken);
		log.info(String.format("Refreshed token for user with email: %s", existToken.getEmail()));
		return jwtToken;
	}
	@Override

	public String generateToken(User user, String email) {
		var jwtToken = jwtService.generateToken(user);
		Token token = Token.builder()
				.token(jwtToken)
				.email(email).build();
		tokenRepository.save(token);
		log.info(String.format("Generated token for user with email: %s", email));
		return jwtToken;
	}
}
