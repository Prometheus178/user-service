package org.booking.core.service;

import lombok.RequiredArgsConstructor;
import org.booking.core.domain.dto.UserRegisteredEvent;
import org.booking.core.domain.entity.role.Role;
import org.booking.core.domain.entity.user.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.booking.core.config.KafkaTopicConfig.USER_REGISTERED_TOPIC;

@Service
@RequiredArgsConstructor
public class EventService {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void publishUserRegisteredEvent(User user) {
		UserRegisteredEvent event = new UserRegisteredEvent();
		event.setId(user.getId());
		event.setUsername(user.getUsername());
		event.setEmail(user.getEmail());
		event.setRoles(user.getRoles().stream().map( Role::getName).collect(Collectors.toSet()));
		event.setRegisteredAt(user.getCreatedAt());
		kafkaTemplate.send(USER_REGISTERED_TOPIC, event);
	}


}
