package org.booking.core.config.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.booking.core.domain.request.UserRegistrationRequest;
import org.booking.core.domain.response.UserRegistrationResponse;
import org.booking.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import static org.booking.core.config.kafka.KafkaConsumerConfig.USER_REGISTRATION_REQUESTS;
import static org.booking.core.config.kafka.KafkaConsumerConfig.USER_REGISTRATION_RESPONSES;

@Slf4j
@RequiredArgsConstructor
@Component
public class RegistrationTopology {

	public static final Serde<String> STRING_SERDE = Serdes.String();

	private final UserRepository userRepository;

	@Autowired
	public void process(StreamsBuilder streamsBuilder) {
		log.info("Start topology logic...");
		KStream<String, UserRegistrationRequest> requestStream = streamsBuilder
				.stream(USER_REGISTRATION_REQUESTS, Consumed.with(STRING_SERDE,
						new JsonSerde<>(UserRegistrationRequest.class)));

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
