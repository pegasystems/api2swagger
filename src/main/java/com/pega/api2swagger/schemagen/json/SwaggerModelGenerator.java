package com.pega.api2swagger.schemagen.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.pega.api2swagger.builders.ArrayPropertyBuilder;
import com.pega.api2swagger.builders.ModelBuilder;
import com.pega.api2swagger.builders.RefPropertyBuilder;
import com.pega.api2swagger.builders.StringPropertyBuilder;
import com.pega.api2swagger.builders.model.BooleanPropertyBuilder;
import com.pega.api2swagger.builders.model.IntegerPropertyBuilder;
import com.pega.api2swagger.builders.model.NumberPropertyBuilder;

import io.swagger.models.properties.Property;

public class SwaggerModelGenerator {
	private ObjectMapper mapper = new ObjectMapper();
	private Map<String, ModelBuilder> models = new HashMap<>(); 
	private static final String UNKNOWN = "unknown";
	private static final String OBJECT = "object";
	
	public Map<String, ModelBuilder> generate(String apiName, String jsonData , ModelBuilder rootModelBuilder){
		
		if(StringUtils.isBlank(jsonData))
			return models;
		
	 	JsonNode rootNode = parseJson(jsonData);
	 	createModelByJsonNode(rootNode, apiName, rootModelBuilder);
	   	return models;
	}
	
	public Map<String, ModelBuilder> generate(String apiName, String jsonData){
		return generate(apiName,jsonData, new ModelBuilder());	   	
	}
	
	private void createModelByJsonNode(JsonNode node, String apiName, ModelBuilder rootModelBuilder){
		if (node.getNodeType() == JsonNodeType.OBJECT) {
			createObjectModel(node, rootModelBuilder, apiName);
			models.put(apiName, rootModelBuilder);
		} else if (node.getNodeType() == JsonNodeType.ARRAY) {
			ArrayPropertyBuilder arrayPropertyBuilder = rootModelBuilder.withArrayProperty(apiName);
			createArrayModel(node, arrayPropertyBuilder, apiName + "-item");
			models.put(apiName, rootModelBuilder);
		}		
	}
		
	private void createArrayModel(JsonNode node, ArrayPropertyBuilder modelBuilder, String objectApiName) {
		String arrayType = findArrayType(node);
		if (arrayType.equals(OBJECT)) {
			modelBuilder.withItems(new RefPropertyBuilder().withReferenceTo(objectApiName).build());
			ModelBuilder innerModelBuilder = new ModelBuilder();
			createObjectModel(node.elements().next(), innerModelBuilder, objectApiName);
			models.put(objectApiName, innerModelBuilder);
		} else if (arrayType.equals(UNKNOWN)) {
			throw new RuntimeException(
					"unknown array type as the array is empty, cannot proceed please have some type in array");
		} else {
			modelBuilder.withItems(createSclarProperty(arrayType, node));
		}
	}
	
	private Property createSclarProperty(String arrayType, JsonNode node) {
		Property property = null;
		Iterator<JsonNode> nodes = node.elements();
		while (nodes.hasNext()) {
			JsonNode leafNode = nodes.next();
			JsonNodeType type = leafNode.getNodeType();
			switch (type) {
			case STRING:
				property = new StringPropertyBuilder().withExample(leafNode.asText()).build();
				break;
			case BOOLEAN:
				property = new BooleanPropertyBuilder().withExample(leafNode.asBoolean()).build();
				break;
			case NUMBER:
				if (leafNode.isInt() || leafNode.isLong()) {
					property = new IntegerPropertyBuilder().withExample(leafNode.asLong()).build();
				} else if (leafNode.isFloat() || leafNode.isDouble()) {
					property = new NumberPropertyBuilder().withExample(leafNode.asDouble()).build();
				}
				break;
			default:
				break;
			}
		}
		return property;
	}

	private String findArrayType(JsonNode leafNode) {
		Iterator<JsonNode> nodes = leafNode.elements();
		while(nodes.hasNext()){
			JsonNode node = nodes.next();
			return node.getNodeType().name().toLowerCase();
		}
		return UNKNOWN;
	}

	private void createObjectModel(JsonNode node, ModelBuilder modelBuilder, String apiName) {
		Iterator<String> fieldNames = node.fieldNames();

		while (fieldNames.hasNext()) {
			String field = fieldNames.next();
			JsonNode leafNode = node.get(field);

			if (leafNode.getNodeType() == JsonNodeType.NUMBER) {
				if (leafNode.isInt() || leafNode.isLong()) {
					modelBuilder.withIntegerPropertyNamed(field).withExample(leafNode.asLong());
				} else if (leafNode.isFloat() || leafNode.isDouble()) {
					modelBuilder.withNumberPropertyNamed(field).withExample(leafNode.asDouble());
				}
			} else if (leafNode.getNodeType() == JsonNodeType.BOOLEAN) {
				modelBuilder.withBooleanPropertyNamed(field).withExample(leafNode.asBoolean());
			} else if (leafNode.getNodeType() == JsonNodeType.STRING) {
				modelBuilder.withStringPropertyNamed(field).withExample(leafNode.asText());
			} else if (leafNode.getNodeType() == JsonNodeType.OBJECT) {
				String refName = apiName+"-"+field;
				modelBuilder.withReferencePropertyNamed(field).withReferenceTo(refName);
				ModelBuilder objModelBuilder = new ModelBuilder();
				createObjectModel(leafNode, objModelBuilder, refName);
				models.put(refName, objModelBuilder);
			}else if(leafNode.getNodeType() == JsonNodeType.ARRAY){
				createArrayModel(leafNode, modelBuilder.withArrayProperty(field), apiName+"-"+field);				
			}
		}		
	}

	JsonNode parseJson(String json) {
		JsonNode node = null;

		try {
			node = mapper.readValue(json, JsonNode.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return node;
	}
	

}
