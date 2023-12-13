package com.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import com.ecommerce.service.KafkaProducer;

@ExtendWith(MockitoExtension.class)
class KafkaProducerTests {
	
	@InjectMocks
	KafkaProducer kafkaProducer;
	
	@Mock
	KafkaTemplate<Integer,String> kafkaTemplate;
	
	@Test
	void publisherTest(){
		JSONObject json = new JSONObject();
		json.put("id", 1);
		json.put("value", "val");
		ProducerRecord<Integer,String> expectedProducerRecord = new ProducerRecord<>("ordersTopic",null,json.getInt("id"),json.toString(),null);
		SendResult<Integer,String> sendResult= new SendResult<>(expectedProducerRecord,null);
		CompletableFuture<SendResult<Integer,String>> expectedresult = CompletableFuture.completedFuture(sendResult);
		
		when(kafkaTemplate.send(any(ProducerRecord.class))).thenReturn(expectedresult);
		
		CompletableFuture<SendResult<Integer,String>> result = kafkaProducer.saveToKafka("orderdetailtopic", json);
		
		assertEquals(result,expectedresult);

	}
	
	
	

		
	
	
	

}
