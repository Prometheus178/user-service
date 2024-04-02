package org.booking.core.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.booking.core.domain.entity.role.Role;
import org.booking.core.domain.entity.token.Token;
import org.booking.core.domain.entity.user.User;
import org.booking.core.domain.request.TokenRequest;
import org.booking.core.domain.response.LoggedInResponse;
import org.booking.core.repository.TokenRepository;
import org.booking.core.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log
@Service
@RequiredArgsConstructor
public class JWTAuthenticationService {

	private final TokenRepository tokenRepository;
	private final UserRepository userRepository;

	public LoggedInResponse authenticate(TokenRequest tokenRequest) {
		log.info("Begin handle request");
		var jwtToken = tokenRequest.getToken();
		DecodedJWT decodedJWT = JWT.decode(jwtToken);
		var userEmail = decodedJWT.getSubject();
		LoggedInResponse loggedInResponse = new LoggedInResponse();
		if (userEmail == null) {
			return loggedInResponse;
		}
		Optional<Token> optionalToken = tokenRepository.findByEmail(userEmail);
		Token token;
		if (optionalToken.isEmpty()) {
			return loggedInResponse;
		} else {
			token = optionalToken.get();
		}
		if (token.isDeleted()) {
			return loggedInResponse;
		}

		Date currentTime = new Date();
		Date tokenExpirationTime = decodedJWT.getExpiresAt();
		if (tokenExpirationTime.before(currentTime)) {
			log.warning("Token has expired");
			// todo update this
//			if (isProlongationAvailable(currentTime, tokenExpirationTime)) {
//				String refreshedToken = jwtService.refreshToken(jwtToken, currentTime);
//				token.setToken(refreshedToken);
//				tokenRepository.save(token);
//				httpServletResponse.setHeader("authorization-fresh-optionalToken", refreshedToken);
//				log.info("Token is refreshed");
//
//				authenticated(httpServletRequest, userEmail);
//			}
			return loggedInResponse;
		}
		return authenticated(userEmail);
	}

	private LoggedInResponse authenticated(String userEmail) {
		log.info("Complete handle request");
		Optional<User> optionalUser = userRepository.findByEmail(userEmail);
		if (optionalUser.isEmpty()) {
			return new LoggedInResponse();
		} else {
			User user = optionalUser.get();
			LoggedInResponse loggedInResponse = new LoggedInResponse();
			loggedInResponse.setUnauthorized(false);
			loggedInResponse.setUserEmail(userEmail);
			List<String> roleNames = user.getRoles().stream()
					.map(Role::getName)
					.collect(Collectors.toList());
			loggedInResponse.setRole(roleNames);
			return loggedInResponse;
		}
	}


	public boolean isProlongationAvailable(Date currentDate, Date date) {
		long timeDifferenceMillis = currentDate.getTime() - date.getTime();
		return timeDifferenceMillis < (60 * 1000 * JWTService.EXPIRATION_TIME_IN_MINUTES);
	}
}
