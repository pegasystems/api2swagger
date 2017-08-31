package com.pega.api2swagger;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import com.google.common.base.Optional;
import com.mashape.unirest.http.HttpMethod;
import com.pega.api2swagger.builders.ModelBuilder;
import com.pega.api2swagger.builders.OperationBuilder;
import com.pega.api2swagger.builders.ParameterBuilder;
import com.pega.api2swagger.builders.RefPropertyBuilder;
import com.pega.api2swagger.builders.ResponseBuilder;
import com.pega.api2swagger.builders.SwaggerBuilder;
import com.pega.api2swagger.builders.model.FilePropertyBuilder;
import com.pega.api2swagger.dto.SwaggerGeneratorInput;
import com.pega.api2swagger.http.EndpointResponse;
import com.pega.api2swagger.http.RestClient;
import com.pega.api2swagger.schemagen.json.SwaggerModelGenerator;
import com.pega.api2swagger.updater.SwaggerUpdater;
import com.pega.api2swagger.utils.HttpUtils;
import com.pega.api2swagger.utils.LogHelper;
import com.pega.api2swagger.utils.MimeType;
import com.pega.api2swagger.utils.URLUtils;

import io.swagger.models.Swagger;

public class SwaggerGenerator {

	private final LogHelper logger = new LogHelper(SwaggerGenerator.class);
	private SwaggerGeneratorInput mUserInput;

	public SwaggerGenerator(SwaggerGeneratorInput input) {
		this.mUserInput = input;
	}
	
	public Optional<Swagger> buildSwaggerJson() {
		RestClient invoker = new RestClient(mUserInput);
		EndpointResponse response = invoker.invoke();

		if (response.getStatus() == HttpStatus.SC_UNAUTHORIZED) {
			logger.error(
					"Endpoint requires authentication, please provide authentication details by -a command line option or for more details see help.");
		}
		Swagger finalSwagger;

		if (new File(mUserInput.swaggerJSONFilePath()).exists()) {
			finalSwagger = new SwaggerUpdater().update(mUserInput.swaggerJSONFilePath(),
					generateFirstTimeSwaggerFile(response));
		} else {
			finalSwagger = generateFirstTimeSwaggerFile(response);
		}

		return Optional.of(finalSwagger);
	}
	
	private Swagger generateFirstTimeSwaggerFile(EndpointResponse response) {

		SwaggerBuilder swaggerBuilder = new SwaggerBuilder();
		swaggerBuilder.withHost(mUserInput.getHost());
		swaggerBuilder.withBasePath(mUserInput.getBasePath());
		swaggerBuilder.withInfo().withTitle("Title").withVersion("1.0");

		OperationBuilder opBuilder;

		if (mUserInput.getMethod() == HttpMethod.GET) {
			opBuilder = swaggerBuilder.withPath(mUserInput.getApiPath()).withGet();
		} else if (mUserInput.getMethod() == HttpMethod.POST) {
			opBuilder = swaggerBuilder.withPath(mUserInput.getApiPath()).withPost();
		} else if (mUserInput.getMethod() == HttpMethod.DELETE) {
			opBuilder = swaggerBuilder.withPath(mUserInput.getApiPath()).withDelete();
		} else {
			opBuilder = swaggerBuilder.withPath(mUserInput.getApiPath()).withPut();
		}
		
		opBuilder.withSummary(mUserInput.getApiPath())
				 .withDescription(mUserInput.getApiDescription())
				 .withTags(mUserInput.getApiTags());
		
		populatePathParameters(opBuilder, URLUtils.parsePathParameters(mUserInput.getEndpoint()));
		populateQueryParameters(opBuilder, mUserInput.getParameters());
			
		String firstMimeType = HttpUtils.getFirstMimeType(response);

		if (StringUtils.isBlank(firstMimeType)) {
			logger.info("no mime type to populate in response's produce section, it is recommended to emit content-type header in response. Continuing further for building swagger json..");
		} else {
			opBuilder.withProduceMimeType(firstMimeType);
		}

		ResponseBuilder responseBuilder = new ResponseBuilder();
		responseBuilder.withDescription(response.getStatusText());

		if (MimeType.JSON.getName().equals(firstMimeType) || MimeType.XML.getName().equals(firstMimeType)) {
			responseBuilder.withSchema(new RefPropertyBuilder().withReferenceTo(mUserInput.getApiName()));
			populateModels(response, swaggerBuilder);

		} else if (MimeType.ZIP.getName().equals(firstMimeType) || MimeType.OCTETSTREAM.getName().equals(firstMimeType)) {
			responseBuilder.withSchema(new FilePropertyBuilder());
		}

		opBuilder.withResponse(String.valueOf(response.getStatus()), responseBuilder);
		
		return swaggerBuilder.build();
	}

	private void populateModels(EndpointResponse response, SwaggerBuilder swaggerBuilder) {
		ModelBuilder modelBuilder = swaggerBuilder.withModelDefinition(mUserInput.getApiName());
		Map<String, ModelBuilder> models = new SwaggerModelGenerator().generate(mUserInput.getApiName(), response.getBody(), modelBuilder);

		for (Entry<String, ModelBuilder> entry : models.entrySet()) {
			swaggerBuilder.withModelNamed(entry.getKey(), entry.getValue());
		}
	}

	private void populateQueryParameters(OperationBuilder opBuilder, Map<String, Object> parameters) {
		for (Map.Entry<String, Object> param : parameters.entrySet()) {
			ParameterBuilder pathBuilder = new ParameterBuilder();
			pathBuilder.inQuery().withDescription(param.getKey()).withName(param.getKey()).required().ofTypeString();
			opBuilder.withParameter(param.getKey(), pathBuilder);
		}
	}

	private void populatePathParameters(OperationBuilder opBuilder, List<String> pathParameters) {
		for (String param : pathParameters) {
			ParameterBuilder pathBuilder = new ParameterBuilder();
			pathBuilder.inPath().withDescription(param).withName(param).ofTypeString();
			opBuilder.withParameter(param, pathBuilder);
		}
	}

	
}
