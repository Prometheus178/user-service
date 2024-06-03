package org.booking.core.service;

import org.booking.core.domain.entity.role.RoleClassification;
import org.booking.core.domain.entity.user.User;
import org.booking.core.domain.request.BaseRegisterRequest;
import org.booking.core.domain.request.UserRequest;
import org.booking.core.domain.response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

	User create(BaseRegisterRequest baseRegisterRequest, RoleClassification roleClassification);

	String getCurrentUserEmail();

	UserResponse getCurrentUser();

	Optional<UserResponse> getUserById(Long id);

	public List<UserResponse> getAllUsers();

	Optional<UserResponse> updateUser(Long id, UserRequest userRequest);

	void deleteUser(Long id);
}

