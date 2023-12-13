package com.ecommerce.service;


import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class KafkaProducer {
	
	@Autowired
	private KafkaTemplate<Integer,String> kafkaTemplate;	

	
	public CompletableFuture<SendResult<Integer,String>> saveToKafka(String topic, JSONObject object) {
			Integer key = object.optIntegerObject("id");
			String value = object.toString();
			ProducerRecord<Integer,String> producerRecord = buildProducerRecord(key,value,topic);
			CompletableFuture<SendResult<Integer,String>> savedRecord  = kafkaTemplate.send(producerRecord);
			savedRecord.whenComplete((sr,ex)->{
				if(sr!=null)	log.info("sucess");
				else if(ex!=null)	log.error("failure");
			});
			return savedRecord;
		
					
	}

	
   private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {
		
		return new ProducerRecord<>(topic,null,key,value,null);
	}
	
	

}
