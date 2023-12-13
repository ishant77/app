package com.ecommerce;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.config.TopicBuilder;

import com.ecommerce.config.KafkaConfiguration;



@ExtendWith(MockitoExtension.class)
class ConfigTest {
	
	@InjectMocks
	private KafkaConfiguration kafkaConfiguration;
	
//	@Mock
//	private AdminClient client;
	
	@Test
	void successTopicTest() {
		
//		when(client.createTopics(Mockito.any())).thenReturn(null);
		
		NewTopic topic=TopicBuilder.name("ordersTopic")
				.partitions(4)
				.replicas(3)
				.build();
		
		NewTopic actual=kafkaConfiguration.orderTopic();
		
		assertEquals(actual.name(),topic.name());
		
		
		
	}
	
	@Test
	void failureEventTest() {
		
//		when(client.createTopics(Mockito.any())).thenReturn(null);
		
		NewTopic topic=TopicBuilder.name("failureTopic")
				.partitions(4)
				.replicas(3)
				.build();
		
		NewTopic actual=kafkaConfiguration.failureEvent();
		
		assertEquals(actual.name(),topic.name());
		
		
		
	}

}
