package org.booking.core.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;


@Configuration
@EnableKafkaStreams
public class KafkaConsumerConfig {

	public static final String USER_REGISTRATION_REQUESTS = "user-registration-requests";
	public static final String USER_REGISTRATION_RESPONSES = "user-registration-responses";

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public NewTopic topicBuilder() {
		return TopicBuilder
				.name(USER_REGISTRATION_REQUESTS)
				.partitions(2)
				.replicas(1)
				.build();
	}

	@Bean
	public NewTopic userRegistrationResponseTopic() {
		return TopicBuilder
				.name(USER_REGISTRATION_RESPONSES)
				.partitions(2)
				.replicas(1)
				.build();
	}
}
