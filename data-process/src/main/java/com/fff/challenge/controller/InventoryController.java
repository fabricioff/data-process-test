package com.fff.challenge.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fff.challenge.model.Inventory;
//import com.fff.challenge.repository.InventoryRepository;
import com.fff.challenge.service.InventoryService;

//@RestController
//@RequestMapping("/inventories")
public class InventoryController {
	
//	@Autowired
//	private InventoryRepository inventoryRepository;
//
//	@GetMapping	
//	public List<Inventory> getAll() {
//		return inventoryRepository.findAll();
//	}
//	
//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public Inventory add(@RequestBody Inventory inventory) {
//		return inventoryRepository.save(inventory);
//	}
	
}
