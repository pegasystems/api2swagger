package com.pega.api2swagger.utils;

import java.util.List;

import com.pega.api2swagger.http.EndpointResponse;

public class HttpUtils {
	
	public static String getFirstMimeType(EndpointResponse response) {
		List<String> mimeTypes = response.getHeaders().get("Content-Type");
		String firstMimeType = null;
		
		if(mimeTypes == null || mimeTypes.isEmpty()){
			return "";
		}
		
		firstMimeType = mimeTypes.get(0).split(";")[0];

		return firstMimeType;
	}
}
