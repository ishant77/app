package com.ecommerce.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
	
	private Integer productid;
	private String productname;
	private int quantity;
	private int price;  

}
