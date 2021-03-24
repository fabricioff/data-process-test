package com.fff.challenge.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.fff.challenge.model.Inventory;

class InventoryServiceTest {

	@Test
	void testDistributeQuantity() {
		InventoryService service = new InventoryService();
		
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 20);
		map.put(2, 21);
		
		Map<Integer, Integer> distributeQuantity = service.distributeQuantity(map, 25);
			
		assert(distributeQuantity.get(1) == 13); 
		assert(distributeQuantity.get(2) == 12);
	}

	@Test
	void testDataProcessing() {
		InventoryService service = new InventoryService();
		List<Inventory> inventories = new ArrayList<Inventory>();
		inventories.add(new Inventory("Product-1", 84, "1.25", "T", "Industry-1", "Origin-1"));
		inventories.add(new Inventory("Product-1", 22, "2.25", "T", "Industry-2", "Origin-2"));
		inventories.add(new Inventory("Product-1", 13, "3.25", "T", "Industry-3", "Origin-3"));
		inventories.add(new Inventory("Product-1", 25, "4.25", "T", "Industry-4", "Origin-4"));
		
		int quantityTotalProd = inventories.stream().map(i -> i.getQuantity()).collect(Collectors.summingInt(Integer::intValue));
		
		Map<String, List<Inventory>> dataProcessing = service.dataProcessing(inventories, "Product-1", "2");
		
		int quantityTotalClients = 0;
		for (Entry<String, List<Inventory>> entrie : dataProcessing.entrySet()) {
			quantityTotalClients += entrie.getValue().stream().map(i -> i.getQuantity()).collect(Collectors.summingInt(Integer::intValue));
		}
		
		assert(quantityTotalClients == quantityTotalProd);
	}

}
