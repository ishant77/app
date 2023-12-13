package com.ecommerce.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {
	
	//Creating the topic if not present
		@Bean
		public NewTopic orderTopic() {
			return TopicBuilder.name("ordersTopic")
					.partitions(4)
					.replicas(3)
					.build();
			
			
		}

		//Creating the Failure-topic if not present
		@Bean
		public NewTopic failureEvent() {
			return TopicBuilder.name("failureTopic")
					.partitions(4)
					.replicas(3)
					.build();
			
			
		}

}
