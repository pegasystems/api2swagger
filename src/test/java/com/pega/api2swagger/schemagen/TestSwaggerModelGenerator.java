package com.pega.api2swagger.schemagen;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.pega.api2swagger.builders.ModelBuilder;
import com.pega.api2swagger.builders.StringPropertyBuilder;
import com.pega.api2swagger.builders.SwaggerBuilder;
import com.pega.api2swagger.schemagen.json.SwaggerModelGenerator;

import io.swagger.models.Swagger;
import io.swagger.util.Json;

public class TestSwaggerModelGenerator {
	
@Test	
public void testGenerateSimepleString(){
	SwaggerModelGenerator generator = new SwaggerModelGenerator();
	
	String inputJson = "{\r\n\"Error\": \"Specified Node ID is invalid\"\r\n}";
	Map<String, ModelBuilder> output = generator.generate("error", inputJson);
	
	String finalSwaggerJson = Json.pretty(getSwagger("Test", output));
	System.out.println(finalSwaggerJson);
}


@Test	
public void testGenerateSimepleInteger(){
	SwaggerModelGenerator generator = new SwaggerModelGenerator();
	
	String inputJson = "{\r\n\"integer\": \"Specified Node ID is invalid\"\r\n}";
	Map<String, ModelBuilder> output = generator.generate("error", inputJson);
	
	String finalSwaggerJson = Json.pretty(getSwagger("Test", output));
	System.out.println(finalSwaggerJson);
}


@Test	
public void testGenerateSimepleInnerObject(){
	SwaggerModelGenerator generator = new SwaggerModelGenerator();
	
	String inputJson = "{\n  \"response\": {\n     \"error\":{\n          \"status\": \"success\"\n      },\n      \"code\" : 400 \n   } \n}";
	Map<String, ModelBuilder> output = generator.generate("innerObject", inputJson);
	
	String finalSwaggerJson = Json.pretty(getSwagger("Test", output));
	System.out.println(finalSwaggerJson);
}

@Test	
public void testGenerateSimepleMultipleObjects(){
	SwaggerModelGenerator generator = new SwaggerModelGenerator();
	
	String inputJson = "{\n  \"response\": {\n     \"error\":{\n          \"status\": \"success\"\n      },\n      \"code\" : 400 \n   },\n   \"prop1\": 123,\n   \"title\":\"Report\",\n   \"data\":{\n     \"name\":\"abcd\"\n   }   \n}";
	Map<String, ModelBuilder> output = generator.generate("multipleObjects", inputJson);
	
	String finalSwaggerJson = Json.pretty(getSwagger("Test", output));
	System.out.println(finalSwaggerJson);
}




@Test	
public void testGenerateSimepleSclarArrayObject(){
	SwaggerModelGenerator generator = new SwaggerModelGenerator();
	
	String inputJson = "{\n  \"response\": [\"A\", \"b\"]\n}";
	Map<String, ModelBuilder> output = generator.generate("arrayObject", inputJson);
	
	String finalSwaggerJson = Json.pretty(getSwagger("Test", output));
	System.out.println(finalSwaggerJson);
}





/// Teting
@Test
public void arrayModelBuilder(){
	Map<String, ModelBuilder> output = new HashMap<>();
	
	ModelBuilder builder = new ModelBuilder();
	builder.withArrayProperty("ArrayStringType").withItems(new StringPropertyBuilder().withExample("Test").build());
	
	output.put("ArrayBuilder", builder);
	
	
	String finalSwaggerJson = Json.pretty(getSwagger("Test", output));
	
	System.out.println(finalSwaggerJson);
}



Swagger getSwagger(String apiName, Map<String, ModelBuilder> output) {
	SwaggerBuilder builder = new SwaggerBuilder();
	builder.withHost("swagger.io");
	builder.withInfo().withTitle("Title").withVersion("1.0");

    for(Entry<String, ModelBuilder> entry: output.entrySet()){
    	builder.withModelNamed(entry.getKey(), entry.getValue());
    }
    
    return builder.build();
	
}


}
