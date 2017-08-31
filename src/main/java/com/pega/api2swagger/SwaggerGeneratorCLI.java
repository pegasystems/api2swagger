package com.pega.api2swagger;

import java.util.List;
import java.util.Map;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.common.base.Optional;
import com.pega.api2swagger.dto.SwaggerGeneratorInput;
import com.pega.api2swagger.utils.FileUtils;
import com.pega.api2swagger.utils.LogHelper;
import com.pega.api2swagger.utils.MapUtils;

import io.swagger.models.Swagger;

public class SwaggerGeneratorCLI {
	
	public static LogHelper logger = new LogHelper(SwaggerGenerator.class);
	
	@Parameter(names = { "-e", "--endpoint" }, description = "Endpoint URL", required = true)
	private String endpoint;

	@Parameter(names = { "-o", "--output" }, description = "Swagger JSON will be writter to this file, file location should be absolute path", required = true)
	private String output;

	@Parameter(names = { "-m", "--method" }, description = "HTTP verb (GET/POST/PUT/DELETE) used for firing endpoint", required = true)
	private String method;

	@Parameter(names = { "-h", "--header" }, description = "Endpoint request headers should be specified as <key>=<value> pairs", required = false)
	private List<String> header;

	@Parameter(names = { "-p", "--param" }, description = "Endpoint request parameters should be specified as <key>=<value> pairs", required = false)
	private List<String> params;

	@Parameter(names = { "-pp", "--pathparam" }, description = "Endpoint request parameters should be specified as <key>=<value> pairs", required = false)
	private List<String> dynamicParameters;

	@Parameter(names = { "-a", "--authentication" }, description = "Boolean flag to specify authentication is needed", required = false)
	private boolean isAuthenticationNeeded;

	@Parameter(names = { "-username", "--username" }, description = "Username", required = false)
	private String username;

	@Parameter(names = { "-password", "--password" }, description = "Password", required = false)
	private String password;

	@Parameter(names = { "-host", "--host" }, description = "Hostname value will be populated in Swagger JSON's 'host' attribute", required = false)
	private String host;

	@Parameter(names = { "-basepath", "--basepath" }, description = "Base Path for Swagger JSON's 'basepath' attribute", required = true)
	private String basePath;

	@Parameter(names = { "-apiname", "--apiname" }, description = "Unique API name for the given endpoint", required = true)
	private String apiName;

	@Parameter(names = { "-apisummary", "--apisummary" }, description = "Endpoint Summary", required = false)
	private String apiSummary;

	@Parameter(names = { "-apidescription", "--apidescription" }, description = "Endpoint Description", required = false)
	private String apiDescription;

	@Parameter(names = { "-tag", "--apitag" }, description = "Endpoint tag", required = false)
	private List<String> apiTags;
	
	@Parameter(names = { "-help", "--help" }, help = true)
    private boolean help = false;
	
	public static void main(String args[]){
		
		SwaggerGeneratorCLI cli = new SwaggerGeneratorCLI();
		JCommander commander = null;
		try{
			commander = new JCommander(cli, args);
			commander.setProgramName("API2Swagger");
		} catch(ParameterException exception){
			logger.error(exception.getMessage());
			logger.error("For help, use --help option");
			return;
		}
		
		if(cli.help){
			commander.usage();
			return;
		}
		
		Map<String, String> headerMap = MapUtils.convertToMap(cli.header);
		Map<String, String> pathParamMap = MapUtils.convertToMap(cli.dynamicParameters);
		Map<String, Object> paramMap = MapUtils.convertToMapObject(cli.params);

		SwaggerGeneratorInput userInput = new SwaggerGeneratorInput.Builder().endpoint(cli.endpoint)
													 .method(cli.method)
													 .pathParams(pathParamMap)
													 .parameters(paramMap)
													 .headers(headerMap)
													 .swaggerJSONFilePath(cli.output)
													 .authentication(cli.isAuthenticationNeeded)
													 .username(cli.username)
													 .password(cli.password)
													 .host(cli.host)
													 .basePath(cli.basePath)
													 .apiName(cli.apiName)
													 .apiDescription(cli.apiDescription)
													 .apiSummary(cli.apiSummary)
													 .apiTags(cli.apiTags)
													 .build();

		SwaggerGenerator main = new SwaggerGenerator(userInput);
		
		logger.info("Generating Swagger json for the input [%s]", userInput);
		
		Optional<Swagger> outputSwagger = main.buildSwaggerJson();
		
		if(outputSwagger.isPresent()){
			FileUtils.writeSwaggerToFile(userInput.swaggerJSONFilePath(), outputSwagger.get());
		}
		
		logger.info("Swagger generatin is complete and output is written to "+ userInput.swaggerJSONFilePath());
		
	}
	
}
