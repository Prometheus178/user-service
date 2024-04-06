package org.booking.core.service;

import org.booking.core.domain.entity.user.User;

public interface UserService {

	String getCurrentUserEmail();

	User getCurrentUser();
}
