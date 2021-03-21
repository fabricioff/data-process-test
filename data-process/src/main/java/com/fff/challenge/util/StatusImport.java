package com.fff.challenge.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

public class StatusImport {

	private String pathLog = "status-import.log"; 

		
	public String getLastInfo() {
		String lastInfo = "";
		FileReader fileReader = null;
		BufferedReader reader = null;
		try {
			fileReader = new FileReader(pathLog);
			reader = new BufferedReader(fileReader);
			Stream<String> lines = reader.lines();
			lastInfo = lines.findFirst().get();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		
		return lastInfo;
	}
	
	public void saveInfo(String info) {
		FileWriter fileWrite = null;
		BufferedWriter Writer = null;
		try {
			fileWrite = new FileWriter(pathLog);
			Writer = new BufferedWriter(fileWrite);
			Writer.write(info);
			//bufferedWrite.newLine();
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
