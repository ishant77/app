package com.ecommerce.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetails {
	
	private String datatype;
	private Integer id;
	private  List<Product> products;
	private int amount;
	

}
