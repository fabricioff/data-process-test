package com.fff.challenge.controller;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fff.challenge.model.Inventory;
import com.fff.challenge.service.InventoryFBService;

@RestController
public class InventoryFBController {

//	@Autowired
//    private InventoryFBService inventoryService;
//
//	@GetMapping("/getAllInventory")
//    public List<Inventory> getAllInventory() throws InterruptedException, ExecutionException{
//        return inventoryService.getInventories();
//    }
//	
//	@GetMapping("/getInventoryDetails")
//    public Inventory getInventory(@RequestParam String product) throws InterruptedException, ExecutionException{
//        return inventoryService.getProductDetails(product);
//    }
//
//    @PostMapping("/createInventory")
//    public String createInventory(@RequestBody Inventory inventory) throws InterruptedException, ExecutionException {
//        return inventoryService.saveProduct(inventory);
//    }
//    
//    //curl -H "Content-Type: application/json" --data @data.json http://localhost:8080/importInventory
//    @PostMapping("/importInventory")
//    public String importInventory(@RequestBody Map<String,List<Inventory>> inventories) throws InterruptedException, ExecutionException {
//        return inventoryService.importInventory(inventories);
//    }
//
//    @PutMapping("/updateInventory")
//    public String updateInventory(@RequestBody Inventory inventory) throws InterruptedException, ExecutionException {
//        return "Endpoint in construction...";
//    }
//
//    @DeleteMapping("/deleteInventory")
//    public String deleteInventory(@RequestParam String name){
//        return inventoryService.deleteProduct(name);
//    }
}
