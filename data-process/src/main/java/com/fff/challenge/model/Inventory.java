package com.fff.challenge.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Inventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//private Long id;
	private String id;
	
	private String product;
	
	private Integer quantity;
	
	private String price;
	
	private String type;
	
	private String industry;
	
	private String origin;
	
}
