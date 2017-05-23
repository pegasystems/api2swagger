package com.pega.api2swagger.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.beust.jcommander.internal.Lists;

public class URLUtils {
public static String calculateAPIPath(String endpoint, String basePath) {
		
		String indexOfRestService = StringUtils.substringAfter(endpoint, basePath);
		return indexOfRestService;
	}

	public static String calculateHostIncludingContext(String endpoint) {
		String indexOfRestService = StringUtils.substringBefore(endpoint, "PRRestService");
		return indexOfRestService;
	}

	public static List<String> parsePathParameters(String endpoint){
		
		List<String> pathParameters = Lists.newArrayList();
		Pattern pattern = Pattern.compile("\\{(\\b\\w*\\b)\\}");
		Matcher matcher = pattern.matcher(endpoint);
		
		while(matcher.find()){
			pathParameters.add(matcher.group(1));
		}
		
		return pathParameters;
	}
}
