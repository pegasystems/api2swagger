package com.pega.api2swagger.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class MapUtils {
	
	public static Map<String, Object> convertToMapObject(List<String> input) {
		Map<String, Object> finalMap = new HashMap<>();
		Map<String, String> headerMap = convertToMap(input);

		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			finalMap.put(entry.getKey(), entry.getValue());
		}
		return finalMap;
	}

	public static Map<String, String> convertToMap(List<String> input) {

		if (input == null) {
			return new HashMap<>();
		}

		String allHeaders = Joiner.on("@@@").join(input);
		Map<String, String> headerMap = Splitter.on("@@@").withKeyValueSeparator("=").split(allHeaders);
		return headerMap;
	}
}
