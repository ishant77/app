package com.ecommerce.controller;



import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.DeliveryDetails;
import com.ecommerce.entity.OrderDetails;
import com.ecommerce.entity.PaymentDetails;
import com.ecommerce.service.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class Controller {
	
	@Autowired
	private KafkaProducer publisher;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public static final String E = "Error ";
	
	public static final String S = "Successfully Published ";
	
	public static final String TOPIC="ordersTopic";
	
	
	@PostMapping("/saveOrderDetails")
	public ResponseEntity<String> saveOderDetails(@RequestBody OrderDetails order){
		order.setDatatype("orderdetails");
		try {
			JSONObject json = new JSONObject(objectMapper.writeValueAsString(order));
			publisher.saveToKafka(TOPIC, json);
			log.info(S);
			return new ResponseEntity<>("Published",HttpStatus.OK);
		}
		catch(JsonProcessingException e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(E+e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	@PostMapping("/savePaymentDetails")
	public ResponseEntity<String> savePaymentDetails(@RequestBody PaymentDetails payment){
		payment.setDatatype("paymentdetails");
		try {
		JSONObject json = new JSONObject(objectMapper.writeValueAsString(payment));
		publisher.saveToKafka(TOPIC, json);
		log.info(S);
		return new ResponseEntity<>("Published",HttpStatus.OK);
		}
		catch(JsonProcessingException e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(E+e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/saveDeliveryDetails")
	public ResponseEntity<String> saveDeliveryDetails(@RequestBody DeliveryDetails delivery){
		delivery.setDatatype("deliverydetails");
		try {
		JSONObject json = new JSONObject(objectMapper.writeValueAsString(delivery));
		publisher.saveToKafka(TOPIC, json);
		log.info(S);
		return new ResponseEntity<>("Published",HttpStatus.OK);
		}
		catch(JsonProcessingException e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(E+e.getMessage(),HttpStatus.BAD_REQUEST);
		}	
		
	}

}
