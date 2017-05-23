package com.pega.api2swagger.schemagen.json;

import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pega.api2swagger.utils.JsonUtils;

public class JsonSchemaGeneratorUtils {

	private static final String EXAMPLE = "example";
	private static final String INTEGER = "integer";
	private static final String NUMBER = "number";
	private static final String STRING = "string";
	private static final String BOOLEAN = "boolean";
	private static final String UNKNOWN = "unknown";
	private static final String PROPERTIES = "properties";
	private static final String ARRAY = "array";
	private static final String OBJECT = "object";
	private static final String TYPE = "type";
	private static final String ITEMS = "items";
	static ObjectMapper mapper = new ObjectMapper();

	public static String generateSchema(String json) {
 
		if(StringUtils.isBlank(json))
			return "";
		
		ObjectNode rootNode = mapper.getNodeFactory().objectNode();
		JsonNode node = null;

		try {
			node = mapper.readValue(json, JsonNode.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		rootNode = createSchemaByJsonNode(node);

		return JsonUtils.prettyPrint(rootNode.toString());
	}
	
	private static ObjectNode createSchemaByJsonNode(JsonNode node) {
		   ObjectNode resultNodeNew = mapper.getNodeFactory().objectNode(); 
		  if (node.getNodeType() == JsonNodeType.OBJECT) {
			  ObjectNode objResult  = leafNodes(node);
			  resultNodeNew.put(TYPE, OBJECT);
			  resultNodeNew.set(PROPERTIES, objResult);
			} else if (node.getNodeType() == JsonNodeType.ARRAY) {				
				ObjectNode items = identifyAndProcessArrayNode(node);
				resultNodeNew =  items;
			}
		return resultNodeNew;
	}

	private static ObjectNode identifyAndProcessArrayNode(JsonNode node) {
		ObjectNode arrayProperties = mapper.getNodeFactory().objectNode();
		String arrayType = findArrayType(node);
		ObjectNode items;
		if (arrayType.equals(OBJECT)) {
			arrayProperties = traverseArray(((ArrayNode) node));
			items = createArraySchemaOfObjectType(arrayProperties);
		} else if (arrayType.equals(UNKNOWN)) {
			throw new RuntimeException(
					"unknown array type as the array is empty, cannot proceed please have some type in array");
		} else {
			items = scalarArray(arrayType);
		}
		return items;
	}

	private static ObjectNode traverseArray(ArrayNode aNode) {
		ObjectNode resultNode = traverseArrayNodes(aNode.elements());
		return resultNode;
	}

	private static ObjectNode leafNodes(JsonNode intermediateNode) {
		Iterator<String> fieldNames = intermediateNode.fieldNames();
		ObjectNode resultNode = traverseLeafNodes(intermediateNode, fieldNames);
		return resultNode;
	}

	private static ObjectNode traverseLeafNodes(JsonNode intermediateNode, Iterator<String> fieldNames) {
		ObjectNode resultNode = mapper.getNodeFactory().objectNode();

		while (fieldNames.hasNext()) {
			String field = fieldNames.next();
			JsonNode leafNode = intermediateNode.get(field);

			if (leafNode.getNodeType() == JsonNodeType.NUMBER) {
				
				ObjectNode child = mapper.getNodeFactory().objectNode();
				
				if(leafNode.isInt() || leafNode.isLong()){
					child.put(TYPE, INTEGER);
					child.put(EXAMPLE, leafNode.asInt() + "");
				}else if(leafNode.isFloat() || leafNode.isDouble()){
					child.put(TYPE, NUMBER);
					child.put(EXAMPLE, leafNode.asInt() + "");
				}

				resultNode.set(field, child);
			} else if (leafNode.getNodeType() == JsonNodeType.BOOLEAN) {
				ObjectNode child = mapper.getNodeFactory().objectNode();
				child.put(TYPE, BOOLEAN);
				child.put(EXAMPLE, leafNode.asBoolean() + "");
				resultNode.set(field, child);
			} else if (leafNode.getNodeType() == JsonNodeType.STRING) {
				ObjectNode child = mapper.getNodeFactory().objectNode();
				child.put(TYPE, STRING);
				child.put(EXAMPLE, leafNode.asText() );
				resultNode.set(field, child);
			} else if (leafNode.getNodeType() == JsonNodeType.OBJECT) {
				ObjectNode child = mapper.getNodeFactory().objectNode();
				child = createSchemaByJsonNode(leafNode);
				resultNode.set(field, child);
			}else if(leafNode.getNodeType() == JsonNodeType.ARRAY){

				ObjectNode items = identifyAndProcessArrayNode(leafNode);
				
				resultNode.set(field, items);
			}
		}
		return resultNode;
	}

	private static ObjectNode createArraySchemaOfObjectType(ObjectNode arrayProperties) {
		ObjectNode items;
		ObjectNode properties = mapper.getNodeFactory().objectNode();
		properties.put(TYPE, OBJECT);
		properties.set(PROPERTIES, arrayProperties);
		items = mapper.getNodeFactory().objectNode();
		items.put(TYPE, ARRAY);
		items.set(ITEMS, properties);
		return items;
	}

	private static ObjectNode scalarArray(String arrayType) {
		ObjectNode items = mapper.getNodeFactory().objectNode();
		items.put(TYPE, ARRAY);
		
		ObjectNode arrayTypeNode = mapper.getNodeFactory().objectNode();
		arrayTypeNode.put(TYPE, arrayType);
		
		items.set(ITEMS, arrayTypeNode);
		return items;
	}

	private static String findArrayType(JsonNode leafNode) {
		Iterator<JsonNode> nodes = leafNode.elements();
		while(nodes.hasNext()){
			JsonNode node = nodes.next();
			return node.getNodeType().name().toLowerCase();
		}
		return UNKNOWN;
	}

	private static ObjectNode traverseArrayNodes(Iterator<JsonNode> fieldNames) {
		ObjectNode resultNode = mapper.getNodeFactory().objectNode();
		while (fieldNames.hasNext()) {
			JsonNode field = fieldNames.next();
			resultNode = leafNodes(field);
		}
		return resultNode;
	}

}
