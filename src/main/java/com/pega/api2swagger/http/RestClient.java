package com.pega.api2swagger.http;

import java.io.IOException;
import java.util.Map;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.pega.api2swagger.dto.SwaggerGeneratorInput;
import com.pega.api2swagger.utils.LogHelper;

public class RestClient {

	private SwaggerGeneratorInput mUserInput;
	private static LogHelper logger = new LogHelper(RestClient.class);
	
	
	public RestClient(SwaggerGeneratorInput input) {
		mUserInput = input;
	}

	public EndpointResponse invoke(){
		
		String endpointURL = prepareEndpointURLForPathParms();
		logger.info("Invoking Endpoint: %s", endpointURL);
		
		HttpRequest request = null;
		switch(mUserInput.getMethod()){
		
		case DELETE:
			request = Unirest.delete(endpointURL).headers(mUserInput.getHeaders());
			break;
		case GET:
			request = Unirest.get(endpointURL).headers(mUserInput.getHeaders());
			break;
		case POST:
			request = Unirest.post(endpointURL).headers(mUserInput.getHeaders());
			break;
		case PUT:
			request = Unirest.put(endpointURL).headers(mUserInput.getHeaders());
			break;
		default:
			logger.error("Given HTTP Method '%s' is not yet supported", mUserInput.getMethod());
			throw new RuntimeException(String.format("Given HTTP Method '%s' is not yet supported", mUserInput.getMethod()));
		}
		
		if (mUserInput.isAuthenticated()) {
			request.basicAuth(mUserInput.getUsername(), mUserInput.getPassword());
		}
		
		HttpResponse<String> response = null;
		
		try {
			response = request.queryString(mUserInput.getParameters()).asString();
		} catch (UnirestException e) {
			logger.error(e, "Error occurred in invoking endpoint %s with method %s", mUserInput.getEndpoint(), mUserInput.getMethod());
			throw new RuntimeException(e);
		}
		
		try {
			Unirest.shutdown();
		} catch (IOException e) {
			logger.error("Error occurred while shutting down rest client", e);
		}
		
		return new EndpointResponse(response);
	}
	
	private String prepareEndpointURLForPathParms() {
		
		String endpoint = mUserInput.getEndpoint();
		Map<String, String> pathParams = mUserInput.getPathParams();
		
		for(Map.Entry<String, String> entry: pathParams.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			
			endpoint = endpoint.replaceAll("\\{"+ key + "\\}", value);
		}
		
		return endpoint;
	}
	
}
