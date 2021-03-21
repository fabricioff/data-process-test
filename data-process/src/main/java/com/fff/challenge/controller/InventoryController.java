package com.fff.challenge.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fff.challenge.model.Inventory;
import com.fff.challenge.repository.InventoryRepository;
import com.fff.challenge.util.StatusImport;

@RestController
//@RequestMapping("/inventories")
public class InventoryController {
	
	@Autowired
	private InventoryRepository inventoryRepository;

	@GetMapping("/getAllInventory")	
	public List<Inventory> getAll() {
		return inventoryRepository.findAll();
	}
	
	@PostMapping("/createInventory")
	@ResponseStatus(HttpStatus.CREATED)
	public Inventory add(@RequestBody Inventory inventory) {
		return inventoryRepository.save(inventory);
	}
	
	//curl -H "Content-Type: application/json" --data @data.json http://localhost:8080/importInventory
    @PostMapping("/importInventory")
    public int importInventory(@RequestBody Map<String,List<Inventory>> data_json) throws InterruptedException, ExecutionException {
    	StatusImport status = new StatusImport();
    	System.out.println(status.getLastInfo());
    	
//    	Set<Inventory> listToSave = new HashSet<Inventory>();
    	for (Entry<String, List<Inventory>> entries : data_json.entrySet()) {
//    		
//    		Map<String, Inventory> mapProducts = new HashMap<String, Inventory>();    		
    		for (Inventory inventoryJson : data_json.get(entries.getKey())) {
    			Inventory save = inventoryRepository.save(inventoryJson);
    			status.saveInfo(save.toString());
//    			
//    			Inventory toUpdate = mapProducts.get(inventoryJson.getProduct());//    			
//    			if (toUpdate == null) {
//    				listToSave.add(inventoryJson);
//    			}
			}
		}
//    	
//    	List<Inventory> result = inventoryRepository.saveAndFlush(listToSave);
    	
        return 0;//result.size();
    }
}
