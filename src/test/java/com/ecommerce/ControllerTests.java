package com.ecommerce;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;

import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ecommerce.controller.Controller;
import com.ecommerce.entity.DeliveryDetails;
import com.ecommerce.entity.OrderDetails;
import com.ecommerce.entity.PaymentDetails;
import com.ecommerce.entity.Product;
import com.ecommerce.service.KafkaProducer;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(Controller.class)
class ControllerTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	KafkaProducer kafkaProducer;
	
	@Mock
	ObjectMapper objectMapper;
	
	
	
	@Test
	void saveOrderDetails() throws Exception {
		List<Product> list = new ArrayList<>();
		list.add(new Product(1,"abc",23,899));
		list.add(new Product(2,"def",4,999));
		OrderDetails orderDetails = new OrderDetails("orderdetails",1,list,1189);
		JSONObject json = new JSONObject(orderDetails);
		String serializedobj = json.toString();
		
		ProducerRecord<Integer,String> expectedProducerRecord = new ProducerRecord<>("ordersTopic",null,orderDetails.getId(),serializedobj,null);
		SendResult<Integer,String> sendResult= new SendResult<>(expectedProducerRecord,null);
		CompletableFuture<SendResult<Integer,String>> expectedresult = CompletableFuture.completedFuture(sendResult);
		
		when(objectMapper.writeValueAsString(any(OrderDetails.class))).thenReturn(serializedobj);
		when(kafkaProducer.saveToKafka(any(String.class),any(JSONObject.class))).thenReturn(expectedresult);
		
		RequestBuilder request = MockMvcRequestBuilders
				.post("/saveOrderDetails")
				.content(serializedobj)
				.contentType(MediaType.APPLICATION_JSON);
				
		MvcResult mvcResult = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn();
		assertEquals("Published",mvcResult.getResponse().getContentAsString());
	}
	
	
	@Test
	void savePaymentDetails() throws Exception {
		
		PaymentDetails paymentDetails = new PaymentDetails("paymentdetails",1,"credit_card",1898,"done");
		JSONObject json = new JSONObject(paymentDetails);
		String serializedobj = json.toString();
		
		ProducerRecord<Integer,String> expectedProducerRecord = new ProducerRecord<>("ordersTopic",null,paymentDetails.getId(),serializedobj,null);
		SendResult<Integer,String> sendResult= new SendResult<>(expectedProducerRecord,null);
		CompletableFuture<SendResult<Integer,String>> expectedresult = CompletableFuture.completedFuture(sendResult);
		
		when(objectMapper.writeValueAsString(any(PaymentDetails.class))).thenReturn(serializedobj);
		when(kafkaProducer.saveToKafka(any(String.class),any(JSONObject.class))).thenReturn(expectedresult);
		
		RequestBuilder request = MockMvcRequestBuilders
				.post("/savePaymentDetails")
				.content(serializedobj)
				.contentType(MediaType.APPLICATION_JSON);
				
		MvcResult mvcResult = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn();
		assertEquals("Published",mvcResult.getResponse().getContentAsString());
	}
		
	
	@Test
	void saveDeliveryDetails() throws Exception {
		DeliveryDetails deliveryDetails = new DeliveryDetails( "deliverydetails",1,"delivered","abc",8989);
		JSONObject json = new JSONObject(deliveryDetails);
		
		String serializedobj = json.toString();
		
		ProducerRecord<Integer,String> expectedProducerRecord = new ProducerRecord<>("ordersTopic",null,deliveryDetails.getId(),serializedobj,null);
		SendResult<Integer,String> sendResult= new SendResult<>(expectedProducerRecord,null);
		CompletableFuture<SendResult<Integer,String>> expectedresult = CompletableFuture.completedFuture(sendResult);
		when(objectMapper.writeValueAsString(any(DeliveryDetails.class))).thenReturn(serializedobj);
		
		when(kafkaProducer.saveToKafka(any(String.class),any(JSONObject.class))).thenReturn(expectedresult);
		
		RequestBuilder request = MockMvcRequestBuilders
				.post("/saveDeliveryDetails")
				.content(serializedobj)
				.contentType(MediaType.APPLICATION_JSON);
				
		MvcResult mvcResult = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn();
		assertEquals("Published",mvcResult.getResponse().getContentAsString());
	}
		
	

}
