package org.booking.core.service;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.booking.core.domain.entity.role.Role;
import org.booking.core.domain.entity.user.User;
import org.booking.core.domain.request.UserRequest;
import org.booking.core.domain.response.UserResponse;
import org.booking.core.mapper.UserMapper;
import org.booking.core.repository.RoleRepository;
import org.booking.core.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceBean implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final UserMapper userMapper;

	@Override
	public String getCurrentUserEmail() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@Override
	public UserResponse getCurrentUser() {
		String currentUserEmail = getCurrentUserEmail();
		User user = userRepository.findByEmail(currentUserEmail)
				.orElseThrow(ExceptionInInitializerError::new);
		return userMapper.toResponse(user);
	}

	public Optional<UserResponse> getUserById(Long id) {
		return userRepository.findById(id).map(userMapper::toResponse);
	}

	@Override
	public List<UserResponse> getAllUsers() {
		return userRepository.findAll().stream().map(userMapper::toResponse)
				.collect(Collectors.toList());
	}
	@Override

	public Optional<UserResponse> updateUser(Long id, UserRequest userRequest) {
		return userRepository.findById(id).map(user -> {
			user.setName(userRequest.getName());
			Role role = roleRepository.findByName(userRequest.getRole())
					.orElseThrow(EntityExistsException::new);
			user.getRoles().add(role);
			return userMapper.toResponse(userRepository.save(user));
		});
	}
	@Override

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
