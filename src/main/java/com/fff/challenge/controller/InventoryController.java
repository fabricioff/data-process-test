package com.fff.challenge.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fff.challenge.model.Inventory;
import com.fff.challenge.repository.InventoryRepository;
import com.fff.challenge.service.InventoryService;

@RestController
public class InventoryController {
		
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private InventoryService inventoryService;	
	

	@GetMapping("/getInventory")	
	public List<Inventory> getAll() {
		return inventoryRepository.findAll();
	}
	
	@GetMapping("/getProduct")	
	public List<Inventory> getProduct(@RequestParam String product) {
		return getAll().stream()
	    		.filter(i -> i.getProduct().equalsIgnoreCase(product))
	    		.collect(Collectors.toList());
	}
	
	@PostMapping("/createInventory")
	@ResponseStatus(HttpStatus.CREATED)
	public Inventory add(@RequestBody Inventory inventory) {
		return inventoryRepository.save(inventory);
	}
	
	//curl -H "Content-Type: application/json" --data @data_1.json http://localhost:8080/importInventory
    @PostMapping("/importInventory")
    public int importInventory(@RequestBody Map<String,List<Inventory>> data_json) throws InterruptedException, ExecutionException {
    	Set<Inventory> listToSave = new HashSet<Inventory>();   
    	
    	List<Inventory> all = getAll();
    	System.out.println("Total encontrado: " + all.size());   		
    	
    	for (Entry<String, List<Inventory>> entries : data_json.entrySet()) {
	    	List<Inventory> invemtories = entries.getValue().stream()
		    	.filter(i -> !all.contains(i))
		        .collect(Collectors.toList());   	
	    	
	    	listToSave.addAll(invemtories);
	    	
	    	System.out.println("Dados filtrados: " + listToSave.size());	    	
	    	System.out.println(listToSave);
	    	
	    	List<Inventory> result = inventoryRepository.saveAll(listToSave);
	    	return result.size();
    	}
	
        return 0;
    }
        
    @GetMapping("/processInventory")
    public Map<String, List<Inventory>> processInventory(@RequestParam String product, @RequestParam String clientsAmount) {
    	List<Inventory> inventories = getProduct(product);
    	
    	 return inventoryService.dataProcessing(inventories, product, clientsAmount);
    }
    
}
