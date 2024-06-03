package org.booking.core.service;

import org.booking.core.domain.entity.token.Token;
import org.booking.core.domain.entity.user.User;

public interface TokenService {

	String generateToken(User user);

	String refreshToken(Token existToken);

}
