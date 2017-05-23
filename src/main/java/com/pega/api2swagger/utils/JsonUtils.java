package com.pega.api2swagger.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	static ObjectMapper mapper = new ObjectMapper();
	
	public static String prettyPrint(String aJson){
		try {
			Object jsonObj = mapper.readValue(aJson, Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObj);
			return indented;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
