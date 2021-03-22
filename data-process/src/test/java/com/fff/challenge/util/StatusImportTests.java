package com.fff.challenge.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fff.challenge.model.Inventory;

@SpringBootTest
public class StatusImportTests {

	StatusImport status = new StatusImport();
	Inventory inventory = new Inventory("Product-1", 1, "4.25", "S" , "Industry-1", "origin-1");
	
	@Test
	void saveInfo() {		
		status.saveInfo(inventory);
		
		Inventory lastInfo = status.getLastInfo();
				
		assert(lastInfo.equals(inventory));
	}
}
