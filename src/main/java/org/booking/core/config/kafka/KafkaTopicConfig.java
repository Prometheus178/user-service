package org.booking.core.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
	public static final String MANAGEMENT_SERVICE = "management-service";

	public static final String USER_REGISTRATION_REQUESTS = "user-registration-requests";
	public static final String USER_REGISTRATION_RESPONSES = "user-registration-responses";

	@Bean
	public NewTopic bookingCoreTopic(){
		return TopicBuilder.name(USER_REGISTRATION_RESPONSES).build();
	}
}
