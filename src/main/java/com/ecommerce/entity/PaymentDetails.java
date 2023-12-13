package com.ecommerce.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentDetails {
	
	private String datatype;
	private Integer id;
	private String payment_method;
	private int amount;
	private String payment_status;

}
