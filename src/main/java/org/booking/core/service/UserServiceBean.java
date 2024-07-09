package org.booking.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.validator.routines.EmailValidator;
import org.booking.core.domain.entity.user.User;
import org.booking.core.domain.request.BaseRegisterRequest;
import org.booking.core.domain.request.UserRequest;
import org.booking.core.domain.response.UserResponse;
import org.booking.core.mapper.UserMapper;
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
	private final UserMapper userMapper;

	@Override
	public User create(BaseRegisterRequest baseRegisterRequest) {
		String email = baseRegisterRequest.getEmail();
		if (userRepository.findByEmail(email).isPresent()) {
			throw new AuthenticationServiceException(String.format("User with email: %s exist", email));
		}
		if (!EmailValidator.getInstance().isValid(email)){
			log.info("Incorrect email: " + email);
			throw new AuthenticationServiceException(String.format("User email: %s is not valid", email));
		}

		var user = User.builder()
				.email(email)
				.name(baseRegisterRequest.getName())
				.password(passwordEncoder.encode(baseRegisterRequest.getPassword()))
				.roles(baseRegisterRequest.getRoles())
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
			user.getRoles().add(userRequest.getRole());
			return userMapper.toResponse(userRepository.save(user));
		});
	}
	@Override

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
