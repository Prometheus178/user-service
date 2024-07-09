package org.booking.core.topology;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.booking.core.domain.response.UserRegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.stereotype.Component;

import static org.booking.core.config.kafka.KafkaTopicConfig.USER_REGISTRATION_REQUESTS;
import static org.booking.core.config.kafka.KafkaTopicConfig.USER_REGISTRATION_RESPONSES;

@Slf4j
@Component
public class RegistrationTopology {


	public static final Serde<String> STRING_SERDE = Serdes.String();

	@Autowired
	public void buildPipeline(StreamsBuilder streamsBuilder) {
		log.info("Start topology logic...");
		KStream<String, String> requestStream = streamsBuilder
				.stream(USER_REGISTRATION_REQUESTS, Consumed.with(STRING_SERDE, STRING_SERDE));

		KStream<String, String> responseStream = requestStream
				.mapValues(value -> {
					UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
					userRegistrationResponse.setStatus("SUCCESS");
					return new Gson().toJson(userRegistrationResponse);
				});

		responseStream.to(USER_REGISTRATION_RESPONSES, Produced.with(STRING_SERDE, STRING_SERDE));
		log.info("Finish topology logic...");
	}


}
