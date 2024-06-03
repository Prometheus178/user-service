package org.booking.core.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	public static final String USER_REGISTERED_TOPIC = "user-registered_0";

	@Bean
	public NewTopic userRegisteredTopic(){
		return TopicBuilder.name(USER_REGISTERED_TOPIC).build();
	}
}
