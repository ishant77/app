package com.ecommerce.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryDetails {
	
	private String datatype;
	private Integer id;
	private String status;
	private String address;
	private int pincode;

}
