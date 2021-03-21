package com.fff.challenge.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
public class Inventory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String product;
	
	private Integer quantity;
	
	private Double price;
	
	private String type;
	
	private String industry;
	
	private String origin;

	@JsonCreator
	public Inventory(String product, Integer quantity, @JsonProperty("price") String price, String type, String industry, String origin) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.price = Double.valueOf(price.replace("$", ""));
		this.type = type;
		this.industry = industry;
		this.origin = origin;
	}
	
	
}
