package org.booking.core.service;

import org.booking.core.domain.dto.UserRegisteredEvent;
import org.booking.core.domain.entity.user.User;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class EventServiceTest {

	@InjectMocks
	private EventService eventService;

	@Mock
	private KafkaTemplate<String, Object> kafkaTemplate;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}


	@Test
	public void testPublishUserRegisteredEvent() {
		// Create test data for User
		User user = Instancio.create(User.class);


		// Call the method to publish the event
		eventService.publishUserRegisteredEvent(user);

		// Capture the event published to Kafka
		ArgumentCaptor<UserRegisteredEvent> eventCaptor = ArgumentCaptor.forClass(UserRegisteredEvent.class);
	//	verify(kafkaTemplate).send(eq(USER_REGISTERED_TOPIC), eventCaptor.capture());

		// Verify the event properties
		UserRegisteredEvent capturedEvent = eventCaptor.getValue();
		assertNotNull(capturedEvent);
		assertEquals(user.getId(), capturedEvent.getId());
		assertEquals(user.getUsername(), capturedEvent.getName());
		assertEquals(user.getEmail(), capturedEvent.getEmail());
		assertEquals(user.getCreatedAt(), capturedEvent.getRegisteredAt());
	//	assertEquals(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()),
		//		capturedEvent.getRoles());
	}
}