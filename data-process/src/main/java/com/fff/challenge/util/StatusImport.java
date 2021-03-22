package com.fff.challenge.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fff.challenge.model.Inventory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

public class StatusImport {

	private File logStatusImport = new File("status-import.log"); 
	ObjectMapper objectMapper = new ObjectMapper();
	
		
	public Inventory getLastInfo() {
		Inventory lastInfo = null;
		if (logStatusImport.exists()) {		
			
//			try {
//				lastInfo = objectMapper.readValue(new File(pathLog), Inventory.class);
//			} catch (IOException e) {
//				//e.printStackTrace();
//				System.out.println("Não foi encontrado o status da ultima importação.");
//			}
			
			FileReader fileReader = null;
			BufferedReader reader = null;
			try {
				fileReader = new FileReader(logStatusImport);
				reader = new BufferedReader(fileReader);
				Stream<String> lines = reader.lines();
				
				//try {
					//lastInfo = objectMapper.readValue(lines.findFirst().get(), new TypeReference<List<Inventory>>(){});
					try {
						lastInfo = objectMapper.readValue(lines.findFirst().get(), Inventory.class);
					} catch (IOException e) {
						e.printStackTrace();
					}
				//} catch (JsonMappingException e) {			
				//	e.printStackTrace();
				//} catch (JsonProcessingException e) {
				//	e.printStackTrace();
				//}
			} catch (FileNotFoundException e) {
				//e.printStackTrace();
			} finally {
				try {
					reader.close();
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}			
			}
		} else {
			System.out.println("Não foi encontrado o status da ultima importação.");
		}
		
		return lastInfo;
	}
	
	public void saveInfo(Inventory info) {
//		try {
//			objectMapper.writeValue(new File(pathLog), info);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		FileWriter fileWrite = null;
		BufferedWriter Writer = null;
		try {
			fileWrite = new FileWriter(logStatusImport);
			Writer = new BufferedWriter(fileWrite);
			
			String valueAsString = objectMapper.writeValueAsString(info);
			System.out.println(valueAsString);
			
			Writer.write(valueAsString);
			//Writer.newLine();
			Writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				Writer.close();
				fileWrite.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		
	}
}
