package org.booking.core.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.booking.core.domain.entity.token.Token;
import org.booking.core.repository.TokenRepository;
import org.booking.core.service.JWTService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Log
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	public static final String AUTHORIZATION = "authorization";
	public static final String BEARER_ = "Bearer ";

	private final JWTService jwtService;
	private final UserDetailsService userDetailService;
	private final TokenRepository tokenRepository;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
									@NonNull HttpServletResponse httpServletResponse,
									@NonNull FilterChain filterChain) throws ServletException, IOException {
		String authHeader = httpServletRequest.getHeader(AUTHORIZATION);
		if (authHeader != null && authHeader.startsWith(BEARER_) && SecurityContextHolder.getContext().getAuthentication() == null) {
			var jwtToken = authHeader.substring(7);
			DecodedJWT decodedJWT = JWT.decode(jwtToken);
			var userEmail = decodedJWT.getSubject();
			if (userEmail == null) {
				throw new RuntimeException("Incorrect token");
			}
			Token token = tokenRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("Token not found"));
			if (token.isDeleted()) {
				filterChain.doFilter(httpServletRequest, httpServletResponse);
				return;
			}

			Date currentTime = new Date();
			Date tokenExpirationTime = decodedJWT.getExpiresAt();
			if (tokenExpirationTime.before(currentTime)) {
				log.warning("Token has expired");
				if (isProlongationAvailable(currentTime, tokenExpirationTime)) {
					String refreshedToken = jwtService.refreshToken(jwtToken, currentTime);
					token.setToken(refreshedToken);
					tokenRepository.save(token);
					httpServletResponse.setHeader("authorization-fresh-token", refreshedToken);
					log.info("Token is refreshed");

					authenticated(httpServletRequest, userEmail);
				}
			} else {
				authenticated(httpServletRequest, userEmail);
			}
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

	private void authenticated(HttpServletRequest httpServletRequest, String userEmail) {
		UserDetails userDetails = this.userDetailService.loadUserByUsername(userEmail);
		UsernamePasswordAuthenticationToken authenticationToken = defineAuthToken(httpServletRequest, userDetails);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

	private UsernamePasswordAuthenticationToken defineAuthToken(HttpServletRequest httpServletRequest, UserDetails userDetails) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails,
				null,
				userDetails.getAuthorities());
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
		return authenticationToken;
	}

	public boolean isProlongationAvailable(Date currentDate, Date date) {
		long timeDifferenceMillis = currentDate.getTime() - date.getTime();
		return timeDifferenceMillis < (60 * 1000 * JWTService.EXPIRATION_TIME_IN_MINUTES);
	}
}
