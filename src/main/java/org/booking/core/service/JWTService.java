package org.booking.core.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JWTService {

	public static final Long EXPIRATION_TIME_IN_MINUTES = 30L;
	public static final String SECRET_KEY = "9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9";

	private static Date getCreationTime() {
		return new Date(System.currentTimeMillis());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> extraClaims = new HashMap<>();
		Date creationTime = getCreationTime();
		return Jwts.builder().setClaims(extraClaims).
				setSubject(userDetails.getUsername())
				.setIssuedAt(creationTime)
				.setExpiration(getExpirationTime(creationTime, EXPIRATION_TIME_IN_MINUTES))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public String refreshToken(String token, Date currentDate) {
		DecodedJWT decodedToken = JWT.decode(token);
		return Jwts.builder().setClaims(new HashMap<>())
				.setSubject(decodedToken.getSubject())
				.setIssuedAt(currentDate)
				.setExpiration(getExpirationTime(currentDate, EXPIRATION_TIME_IN_MINUTES))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	private Date getExpirationTime(Date creationTime, Long minutes) {
		long milliseconds = creationTime.getTime();
		long updatedMilliseconds = milliseconds + (60000 * minutes);
		return new Date(updatedMilliseconds);
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}