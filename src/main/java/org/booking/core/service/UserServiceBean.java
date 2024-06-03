package org.booking.core.service;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.validator.routines.EmailValidator;
import org.booking.core.domain.entity.role.Role;
import org.booking.core.domain.entity.role.RoleClassification;
import org.booking.core.domain.entity.user.User;
import org.booking.core.domain.request.BaseRegisterRequest;
import org.booking.core.domain.request.UserRequest;
import org.booking.core.domain.response.UserResponse;
import org.booking.core.mapper.UserMapper;
import org.booking.core.repository.RoleRepository;
import org.booking.core.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log
@RequiredArgsConstructor
@Service
public class UserServiceBean implements UserService {

	private final EventService eventService;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final UserMapper userMapper;

	@Override
	public User create(BaseRegisterRequest baseRegisterRequest, RoleClassification roleClassification) {
		String email = baseRegisterRequest.getEmail();
		if (userRepository.findByEmail(email).isPresent()) {
			throw new AuthenticationServiceException(String.format("User with email: %s exist", email));
		}
		if (!EmailValidator.getInstance().isValid(email)){
			log.info("Incorrect email: " + email);
			throw new AuthenticationServiceException(String.format("User email: %s is not valid", email));
		}

		Role role = roleRepository.findByName(roleClassification.name()).orElseThrow(
				() -> new AuthenticationServiceException(String.format("Role %s not found",
						roleClassification.name()))
		);
		var user = User.builder()
				.email(email)
				.name(baseRegisterRequest.getUsername())
				.password(passwordEncoder.encode(baseRegisterRequest.getPassword()))
				.role(role)
				.build();
		User createdUser = userRepository.save(user);
		eventService.publishUserRegisteredEvent(createdUser);
		return createdUser;
	}

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
