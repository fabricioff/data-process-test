package com.fff.challenge.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fff.challenge.model.Inventory;

@Service
public class InventoryService {
	
	private static final String label = "logista-";
	
	public  Map<Integer, Integer> distributeQuantity(Map<Integer, Integer> quantityByClients, int quantity) {
    	Map<Integer, Integer> distributeByClients = new HashMap<Integer, Integer>(); 
    	boolean hasRedistribute = false;
    	int qtdeMenorClient = Integer.MAX_VALUE;
    	int idMenorClient = 0;
    	
    	
    	for (Entry<Integer, Integer> entries : quantityByClients.entrySet()) {			
			if (entries.getValue() < qtdeMenorClient) { 
				qtdeMenorClient = entries.getValue();
				idMenorClient = entries.getKey();
			}			
		}
    	    
    	int resto = quantity % quantityByClients.size();
    	if (resto > 0) {
    		hasRedistribute = true;
    	}
    	
    	int quantityByClient = quantity / quantityByClients.size();
    	for (Entry<Integer, Integer> entries : quantityByClients.entrySet()) {
    		if (entries.getKey() == idMenorClient && hasRedistribute) {
    			distributeByClients.put(entries.getKey(), quantityByClient + resto);
    		} else {
    			distributeByClients.put(entries.getKey(), quantityByClient);
    		}
    	}
    	
    	return distributeByClients;
    }
	
	public Map<String, List<Inventory>> dataProcessing(List<Inventory> inventories, String product, String clientsAmount) {
		Map<String, List<Inventory>> result = new HashMap<String, List<Inventory>>();
     	
    	double priceTotal = 0.0;
    	double volumeTotal = 0.0;
    	int quantityTotal = 0;
    	int div = Integer.parseInt(clientsAmount);
    	
    	List<Inventory> products = inventories.stream()
    		.filter(i -> i.getProduct().equalsIgnoreCase(product))
    		.collect(Collectors.toList());
    	
    	String log = 
    		   "==[ LOG DATA PROCESSING ]===============";
    	log += String.format("\n > Clientes................: %s", clientsAmount);
    	log += String.format("\n > Produto.................: %s", product);
    	log += "\n--------------------------------";
    	log += "\n" + products.stream().map(i -> i.toString()).collect(Collectors.joining("\n"));
    	log += "\n--------------------------------";
    	Integer quantityProd = products.stream().map(i -> i.getQuantity()).collect(Collectors.summingInt(Integer::intValue));
    	Double priceProd = products.stream().map(i -> i.getPrice()).collect(Collectors.summingDouble(Double::doubleValue));
    	Double volumeProd = products.stream().map(i -> i.getVolume()).collect(Collectors.summingDouble(Double::doubleValue));
    	log += String.format("\n > Quantidade total.....: %d", quantityProd);
    	log += String.format("\n > Preço total..........: %f", priceProd);
    	log += String.format("\n > Preço medio..........: %f", priceProd/products.size());
    	log += String.format("\n > Volume...............: %f", volumeProd);
    	log += "\n--------------------------------";
    	
    	Map<Integer, Integer> totalQuantityByClient = new HashMap<Integer, Integer>();
    	for(int i=1; i<div+1; i++) totalQuantityByClient.put(i, 0);
    		
    	for (Inventory inv : products) {
    		int totalQtdeByProduct = 0;
    		
    		Map<Integer, Integer> distributeQuantity = distributeQuantity(totalQuantityByClient, inv.getQuantity());
    		
    		for(int i = 1; i < (div+1); i++) {    			
    			if (result.get(label + i) == null) {    			
    				result.put(label + i, new ArrayList<Inventory>());
    			}
    			
    			int qtde = distributeQuantity.get(i);    			
    			totalQtdeByProduct += qtde;

    			totalQuantityByClient.put(i, totalQuantityByClient.get(i) + qtde);
    			
        		result.get(label + i).add(new Inventory(inv.getProduct(), qtde, inv.getPrice().toString(), inv.getType(), inv.getIndustry(), inv.getOrigin()));    	    	
        	}
    		
    		if (totalQtdeByProduct != inv.getQuantity()) {
    			System.out.println("Divisão FALHOU - {totalQtdeByProduct - inv.getQuantity(): " + (totalQtdeByProduct - inv.getQuantity()) + "}");
    			System.out.println(inv);
    		}
		}
    	
    	
    	for (Entry<String, List<Inventory>> entries : result.entrySet()) {    		
    		log += "\n\n--------------------------------";
    		log += String.format("\n > Logista: \"%s\"", entries.getKey());
    		log += "\n" + entries.getValue().stream().map(i -> i.toString()).collect(Collectors.joining("\n"));
    		
    		Integer quantity = entries.getValue().stream().map(i -> i.getQuantity()).collect(Collectors.summingInt(Integer::intValue));
    		Double price = entries.getValue().stream().map(i -> i.getPrice()).collect(Collectors.summingDouble(Double::doubleValue));
    		Double volume = entries.getValue().stream().map(i -> i.getVolume()).collect(Collectors.summingDouble(Double::doubleValue));    		
    		log += String.format("\n > Quantidade........: %d", quantity);
        	log += String.format("\n > Preços............: %f", price);
        	log += String.format("\n > Media preços....... %f", price/entries.getValue().size());
        	log += String.format("\n > Volume............: %f", volume);	
        	
        	quantityTotal += quantity;
        	priceTotal = price;
			volumeTotal += volume;
		}
    	
    	log += "\n\n--------------------------------";
    	log += String.format("\n > Total quantidade........: %d", quantityTotal);
    	log += String.format("\n > Total preços............: %f", priceTotal);
    	log += String.format("\n > Media preços............: %f", priceTotal/products.size());
    	log += String.format("\n > Total Volume............: %f", volumeTotal);
    	
    	System.out.println(log);
    	
    	
    	return result;
	}

}
