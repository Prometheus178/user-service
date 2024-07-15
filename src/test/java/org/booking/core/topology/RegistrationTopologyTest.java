package org.booking.core.topology;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.booking.core.config.kafka.RegistrationTopology;
import org.booking.core.domain.request.UserRegistrationRequest;
import org.booking.core.domain.response.UserRegistrationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Properties;

import static org.booking.core.config.kafka.KafkaConsumerConfig.USER_REGISTRATION_REQUESTS;
import static org.booking.core.config.kafka.KafkaConsumerConfig.USER_REGISTRATION_RESPONSES;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrationTopologyTest {

	private static final Logger log = LoggerFactory.getLogger(RegistrationTopologyTest.class);

	private TopologyTestDriver testDriver;
	private TestInputTopic<String, UserRegistrationRequest> inputTopic;
	private TestOutputTopic<String, String> outputTopic;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-app");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

		log.info("Setting up StreamsBuilder and building topology");
		StreamsBuilder streamsBuilder = new StreamsBuilder();
		RegistrationTopology registrationTopology = new RegistrationTopology(null);
		registrationTopology.process(streamsBuilder);
		Topology builtTopology = streamsBuilder.build();

		log.info("Setting up TopologyTestDriver");
		testDriver = new TopologyTestDriver(builtTopology, props);

		log.info("Creating input and output topics for testing");
		inputTopic = testDriver.createInputTopic(USER_REGISTRATION_REQUESTS,
				Serdes.String().serializer(), new JsonSerde<>(UserRegistrationRequest.class).serializer());
//		outputTopic = testDriver.createOutputTopic(USER_REGISTRATION_RESPONSES,
//				Serdes.String().deserializer(), Serdes.String().deserializer());
	}

//	@Test
//	void testProcess() {
//		log.info("Starting testProcess");
//
//		UserRegistrationRequest request = new UserRegistrationRequest();
//		// Populate request with necessary data
//		log.info("Generated test request: {}", request);
//
//		log.info("Piping request to input topic");
//		Gson gson = new Gson();
//		inputTopic.pipeInput("key1", gson.toJson(request));
//
//		log.info("Reading response from output topic");
//		System.out.println(outputTopic);
//		UserRegistrationResponse response = gson.fromJson(outputTopic.readValue(), UserRegistrationResponse.class);
//
//		log.info("Captured response: {}", response);
//
//		assertEquals("SUCCESS", response.getStatus());
//		// Add more assertions as needed to verify other fields of UserRegistrationResponse
//
//		log.info("Finished testProcess");
//	}

}