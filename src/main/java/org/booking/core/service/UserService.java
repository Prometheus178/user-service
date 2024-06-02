package org.booking.core.service;

import org.booking.core.domain.request.UserRequest;
import org.booking.core.domain.response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

	String getCurrentUserEmail();

	UserResponse getCurrentUser();

	Optional<UserResponse> getUserById(Long id);

	public List<UserResponse> getAllUsers();

	Optional<UserResponse> updateUser(Long id, UserRequest userRequest);

	void deleteUser(Long id);
}

