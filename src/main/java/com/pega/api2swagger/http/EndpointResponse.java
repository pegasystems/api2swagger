package com.pega.api2swagger.http;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;

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
		
		String statusText = mResponse.getStatusText();
		
		if(StringUtils.isNotBlank(statusText)){
			return statusText;
		}

		return HttpStatus.getStatusText(mResponse.getStatus());
	}
	
	public HashMap<String, List<String>> getHeaders(){
		return mResponse.getHeaders();
	}
}
