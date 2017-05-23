package com.pega.api2swagger.builders;

import java.util.Arrays;

import org.junit.Test;

import io.swagger.models.Swagger;
import io.swagger.models.properties.IntegerProperty;
import io.swagger.util.Json;

public class TestSwaggerBuilders {
	
	@Test
	public void testSwaggerBasic(){
		
		SwaggerBuilder builder = new SwaggerBuilder();
		builder.withInfo().withTitle("Test Title").withVersion("1.0");
		
		ResponseBuilder responseBuilder = new ResponseBuilder();
		responseBuilder.withDescription("200 description");
		responseBuilder.withSchema(new RefPropertyBuilder().withReferenceTo("TestRef"));
		builder.withPath("/conclusion").withGet().withTags(Arrays.asList("Test")).withResponse("200", responseBuilder);
	
		ModelBuilder mBuilder = builder.withModelDefinition("TestRef");
		mBuilder.withReferencePropertyNamed("DefTestRef").withReferenceTo("some reference");
		mBuilder.withStringPropertyNamed("TestModelProperty").withExample("myexample").withFormat("myformat");
		mBuilder.withStringPropertyNamed("secondProperty").withExample("secondExample");
		
		IntegerProperty intProperty = new IntegerProperty();
		intProperty.example(10);
		intProperty.setDescription("IntegerTest");
		mBuilder.withArrayProperty("arrayProperty").withItems(intProperty);
		
		RefPropertyBuilder refBuilder = new RefPropertyBuilder().withReferenceTo("finalArrayRef");
		mBuilder.withArrayProperty("refArrayProperty").withItems(refBuilder.build());
		
		Swagger swagger = builder.build();
		Json.prettyPrint(swagger);
	}
	
}
