package com.pega.api2swagger.dto;

import java.util.List;
import java.util.Map;

import com.mashape.unirest.http.HttpMethod;
import com.pega.api2swagger.utils.URLUtils;

public class SwaggerGeneratorInput {

	private String endpoint;
	private Map<String, String> pathParams;
	private Map<String, String> headers;
	private Map<String, Object> parameters;
	private String swaggerJSONFile;
	private HttpMethod method;
	private boolean authentication;
	private String username;
	private String password;
	private String host;
	private String basePath;
	private String apiPath;
	private String apiName;
	private String apiSummary;
	private String apiDescription;
	private List<String> apiTags;
	
	private SwaggerGeneratorInput(String endpoint, HttpMethod method, Map<String, String> pathParams, Map<String, String> headers, Map<String, Object> parameters, String swaggerJSONFilePath, 
			boolean isAuthentication, String username, String password, String host, String basePath, String apiPath, String apiName, String apiSummary, String apiDescription, List<String> apiTags){
		this.endpoint = endpoint;
		this.pathParams = pathParams;
		this.headers = headers;
		this.parameters = parameters;
		this.swaggerJSONFile = swaggerJSONFilePath;
		this.method = method;
		this.authentication = isAuthentication;
		this.username = username;
		this.password = password;
		this.host = host;
		this.basePath = basePath;
		this.apiPath = apiPath;
		this.apiName = apiName;
		this.apiSummary = apiSummary;
		this.apiDescription = apiDescription;
		this.apiTags = apiTags;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAuthenticated() {
		return authentication;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getEndpoint() {
		return endpoint;
	}
	public Map<String, String> getPathParams() {
		return pathParams;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public Map<String, Object> getParameters() {
		return parameters;
	}
	public String swaggerJSONFilePath() {
		return swaggerJSONFile;
	}
	
	public String getApiSummary() {
		return apiSummary;
	}

	public String getApiDescription() {
		return apiDescription;
	}

	public List<String> getApiTags() {
		return apiTags;
	}



	public static class Builder {

		private String endpoint;
		private Map<String, String> pathParams;
		private Map<String, String> headers;
		private Map<String, Object> parameters;
		private String swaggerJSONFilePath;
		private HttpMethod method;
		private boolean authentication;
		private String username;
		private String password;
		private String host;
		private String basePath;
		private String apiName;
		private String apiSummary;
		private String apiDescription;
		private List<String> apiTags;
		
		public Builder endpoint(String aEndPoint){
			endpoint = aEndPoint;
			return this;
		}
		
		public Builder pathParams(Map<String, String> pathParams) {
			this.pathParams = pathParams;
			return this;
		}
		
		public Builder headers(Map<String, String> headers) {
			this.headers = headers;
			return this;
		}
		
		public Builder swaggerJSONFilePath(String aSwaggerJSONFilePath) {
			this.swaggerJSONFilePath = aSwaggerJSONFilePath;
			return this;
		}
		
		public Builder parameters(Map<String, Object> parameters) {
			this.parameters = parameters;
			return this;
		}
		
		public Builder username(String username) {
			this.username = username;
			return this;
		}
		
		public Builder password(String password) {
			this.password = password;
			return this;
		}
		
		public Builder method(String aMethod){
			method = Enum.valueOf(HttpMethod.class, aMethod);
			return this;
		}
		
		public SwaggerGeneratorInput build(){
			return new SwaggerGeneratorInput(endpoint, method, pathParams, headers, parameters, swaggerJSONFilePath, authentication, username, 
											password, host, basePath, URLUtils.calculateAPIPath(endpoint, basePath), apiName, apiSummary, apiDescription, apiTags);
		}

		public Builder authentication(boolean isAuthenticationNeeded) {
			authentication = isAuthenticationNeeded;
			return this;
		}
		
		public Builder host(String host){
			this.host = host;
			return this;
		}
		
		public Builder basePath(String basePath){
			this.basePath = basePath;
			return this;
		}
		
		public Builder apiName(String apiName){
			this.apiName = apiName;
			return this;
		}
		
		public Builder apiSummary(String summary){
			this.apiSummary = summary;
			return this;
		}
		
		public Builder apiDescription(String description){
			this.apiDescription = description;
			return this;
		}
		
		public Builder apiTags(List<String> apiTags){
			this.apiTags = apiTags;
			return this;
		}
	}

	public String getHost() {
		return this.host;
	}
	
	public String getBasePath() {
		return this.basePath;
	}
	
	public String getApiPath() {
		return this.apiPath;
	}
	
	public String getApiName() {
		return this.apiName;
	}

	@Override
	public String toString() {
		return "SwaggerGeneratorInput [endpoint=" + endpoint + ", pathParams=" + pathParams + ", headers=" + headers
				+ ", parameters=" + parameters + ", swaggerJSONFile=" + swaggerJSONFile + ", method=" + method
				+ ", authentication=" + authentication + ", username=" + username + ", password=" + password + ", host="
				+ host + ", basePath=" + basePath + ", apiPath=" + apiPath + ", apiName=" + apiName + ", apiSummary="
				+ apiSummary + ", apiDescription=" + apiDescription + ", apiTags=" + apiTags + "]";
	}
}
