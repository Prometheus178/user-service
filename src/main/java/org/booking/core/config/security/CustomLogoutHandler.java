package org.booking.core.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.booking.core.domain.entity.token.Token;
import org.booking.core.repository.TokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static org.booking.core.config.security.JwtAuthenticationFilter.AUTHORIZATION;
import static org.booking.core.config.security.JwtAuthenticationFilter.BEARER_;

@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

	private final TokenRepository tokenRepository;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		final String authHeader = request.getHeader(AUTHORIZATION);
		if (authHeader == null || !authHeader.startsWith(BEARER_)) {
			return;
		}
		var jwt = authHeader.substring(7);
		var optionalToken = tokenRepository.findByToken(jwt);
		if (optionalToken.isPresent()) {
			Token token = optionalToken.get();
			token.setDeleted(true);
			tokenRepository.save(token);
			SecurityContextHolder.clearContext();
		}
	}
}
