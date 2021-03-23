package com.fff.challenge.controller;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fff.challenge.model.Inventory;
import com.fff.challenge.repository.InventoryRepository;

@RestController
//@RequestMapping("/inventories")
public class InventoryController {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	private List<Inventory> list;

	@GetMapping("/getProducts")	
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
    	Set<Inventory> listToSave = new HashSet<Inventory>();   
    	
    	List<Inventory> all = getAll();
    	System.out.println("Total encontrado: " + all.size());
    	
    	for (Entry<String, List<Inventory>> entries : data_json.entrySet()) {
	    	List<Inventory> invemtories = entries.getValue().stream()
		    	//.map(Map.Entry::getValue)
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
    	List<Inventory> all = getAll();
    	
    	double priceTotal = 0.0;
    	double volumeTotal = 0.0;
    	int quantityTotal = 0;
    	int div = Integer.parseInt(clientsAmount);
    	
    	List<Inventory> inventories = all.stream()
    		.filter(i -> i.getProduct().equalsIgnoreCase(product))
    		.collect(Collectors.toList());
    	
    	inventories.stream().forEach(i -> System.out.println(i));
    	
    	String label = "logista-";
    	Map<String, List<Inventory>> result = new HashMap<String, List<Inventory>>();
    	for (Inventory inv : inventories) {
    		
    		int qtde = inv.getQuantity() / div;
    		int totalQtde = 0;
    		
    		for(int i = 1; i < (div+1); i++) {    			
    			if (result.get(label + i) == null) {    			
    				result.put(label + i, new ArrayList<Inventory>());
    			}
    			
    			totalQtde += qtde;
    			int aux = 0;
    			if (totalQtde == inv.getQuantity() - 1)
    				aux = 1;
    			
        		result.get(label + i).add(new Inventory(inv.getProduct(), (qtde + aux), inv.getPrice().toString(), inv.getType(), inv.getIndustry(), inv.getOrigin()));    	    	
        	}
		}
    	
    
    	String log = "==[ LOG DATA PROCESSING ]===============";
    	log += String.format("\n > Clientes................: %s", clientsAmount);
    	log += String.format("\n > Produto.................: %s", product);
    	for (Entry<String, List<Inventory>> entries : result.entrySet()) {
    		log += String.format("\n > Logista: %s", entries.getKey());
    		double priceByClient = 0.0;
    		double volumeByClient = 0.0;
    		int quantityByClient = 0;
    		for (Inventory i : entries.getValue()) {
    			log += "\n > " + i;
    			priceByClient += i.getPrice();
    			volumeByClient += i.getVolume();
    			quantityByClient += i.getQuantity();
			}
    		log += String.format("\n > Quantidade........: %d", quantityByClient);
        	log += String.format("\n > Preços............: %f", priceByClient);
        	log += String.format("\n > Media preços....... %f", priceByClient/result.size());
        	log += String.format("\n > Volume............: %f", volumeByClient);	
        	
        	priceTotal += priceByClient;
			volumeTotal += volumeByClient;
			quantityTotal += quantityByClient;
		}
    	
    	log += "\n -------------------------------";
    	log += String.format("\n > Total quantidade........: %d", quantityTotal);
    	log += String.format("\n > Total preços............: %f", priceTotal);
    	log += String.format("\n > Media preços............: %f", priceTotal/result.size());
    	log += String.format("\n > Total Volume............: %f", volumeTotal);
    	
    	System.out.println(log);
    	
    	
    	return result;
    }
    
    private static String arredondar(double media) {
    	   DecimalFormat df = new DecimalFormat("0.00");
    	   df.setRoundingMode(RoundingMode.HALF_UP);
    	   return df.format(media);
    	}
}
