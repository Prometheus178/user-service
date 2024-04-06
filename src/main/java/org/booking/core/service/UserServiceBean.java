package org.booking.core.service;

import lombok.RequiredArgsConstructor;
import org.booking.core.domain.entity.user.User;
import org.booking.core.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceBean implements UserService {

	private final UserRepository userRepository;

	@Override
	public String getCurrentUserEmail() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@Override
	public User getCurrentUser() {
		String currentUserEmail = getCurrentUserEmail();
		return userRepository.findByEmail(currentUserEmail)
				.orElseThrow(ExceptionInInitializerError::new);
	}

}
