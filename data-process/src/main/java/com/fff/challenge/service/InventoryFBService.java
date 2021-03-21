package com.fff.challenge.service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;


import com.fff.challenge.model.Inventory;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;


@Service
public class InventoryFBService {
	
	public static final String COL_NAME="inventory";
	
	static private Firestore dbFirestore = FirestoreClient.getFirestore();
	static private CollectionReference collection = dbFirestore.collection(COL_NAME);
	
	public String saveProduct(Inventory inventory) throws InterruptedException, ExecutionException {		       
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME)
        		.document(inventory.getProduct())
        		.set(inventory);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }
	
	public String importInventory(Map<String,List<Inventory>> inventories) throws InterruptedException, ExecutionException {		
		String lastOperation = "Nenhum produto importado";
		List<Inventory> list = inventories.get(inventories.keySet().toArray()[0]);
		for (Inventory inventory : list) {
			if (getProductDetails(inventory.getProduct()) == null) {
				DocumentReference document = collection.document();
				//inventory.setId(document.getId());
				lastOperation = document.set(inventory).get().getUpdateTime().toString();;
			} else {
				System.out.println("Produto já existente: " + inventory.getProduct());
			}
		}
		return lastOperation;
    }
	
	public List<Inventory> getInventories() throws InterruptedException, ExecutionException {
		ApiFuture<QuerySnapshot> apiFuture = collection.get();
		List<QueryDocumentSnapshot> documents = apiFuture.get().getDocuments();
		List<Inventory> inventories = new ArrayList<Inventory>();
		
		for (QueryDocumentSnapshot document : documents) {
			inventories.add(document.toObject(Inventory.class));
		}
		System.out.println("Total de produtos no estoque: " + inventories.size());
		return inventories;        
    }
	
	public Inventory getProductDetails(String name) throws InterruptedException, ExecutionException {
		Inventory inventory = null;
		
        Query query = collection.whereEqualTo("product", name);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();        
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
        
        if (!documents.isEmpty()) {
        	System.out.println("Produto encontrado!");
        	for (DocumentSnapshot document : documents) {
	        	inventory = document.toObject(Inventory.class);
	        }
        } else {        	
        	System.out.println("Nenhum produto encontrado!");
        }
        
        return inventory;
    }
	
    public String updateProductDetails(Inventory inventory) throws InterruptedException, ExecutionException {      	
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(inventory.getProduct())
        		.set(inventory);        
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String deleteProduct(String product) {        
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(product).delete();
        return String.format("O produto(%s) foi removido do estoque.", product);
    }
	
}
