package com.pega.api2swagger.http;

import java.util.HashMap;
import java.util.List;

import com.mashape.unirest.http.HttpResponse;

public class EndpointResponse {

	HttpResponse<String> mResponse;
	
	public EndpointResponse(HttpResponse<String> response) {
		mResponse = response;
	}
	
	public String getBody(){
		return mResponse.getBody();
	}
	
	public int getStatus(){
		return mResponse.getStatus();
	}
	
	public String getStatusText(){
		return mResponse.getStatusText();
	}
	
	public HashMap<String, List<String>> getHeaders(){
		return mResponse.getHeaders();
	}
}
