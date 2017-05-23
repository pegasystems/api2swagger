package com.pega.api2swagger.schemagen;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pega.api2swagger.schemagen.json.JsonSchemaGeneratorUtils;

import io.restassured.module.jsv.JsonSchemaValidator;

public class TestSchemaGeneration {

	@Test
	public void schemaGenerationForObjects(){
		String json = "{\"RuleFileBundle\":{\"Size Estimate\":138646,\"Prune Age\":3600000,\"Peak Value\":63,\"Type ID\":5,\"Min Size\":120,\"Count\":63,\"Type Name\":\"RuleFileBundle\"},\"FieldValue\":{\"Size Estimate\":11043481,\"Prune Age\":2147483647,\"Peak Value\":16135,\"Type ID\":7,\"Min Size\":2147483647,\"Count\":16135,\"Type Name\":\"FieldValue\"},\"AccessInfo\":{\"Size Estimate\":15116303,\"Prune Age\":3600000,\"Peak Value\":3550,\"Type ID\":8,\"Min Size\":10000,\"Count\":3550,\"Type Name\":\"AccessInfo\"},\"Application\":{\"Size Estimate\":860873,\"Prune Age\":3600000,\"Peak Value\":30,\"Type ID\":9,\"Min Size\":100,\"Count\":21,\"Type Name\":\"Application\"},\"Class\":{\"Size Estimate\":1543328,\"Prune Age\":3600000,\"Peak Value\":2054,\"Type ID\":1,\"Min Size\":21000,\"Count\":2054,\"Type Name\":\"Class\"},\"RuleFileBinary\":{\"Size Estimate\":1984383,\"Prune Age\":3600000,\"Peak Value\":1489,\"Type ID\":4,\"Min Size\":3600,\"Count\":1489,\"Type Name\":\"RuleFileBinary\"},\"RuleFileText\":{\"Size Estimate\":6274088,\"Prune Age\":3600000,\"Peak Value\":1090,\"Type ID\":6,\"Min Size\":1200,\"Count\":1090,\"Type Name\":\"RuleFileText\"},\"Property Alias\":{\"Size Estimate\":0,\"Prune Age\":3600000,\"Peak Value\":1,\"Type ID\":3,\"Min Size\":0,\"Count\":0,\"Type Name\":\"Property Alias\"},\"Property\":{\"Size Estimate\":29412128,\"Prune Age\":3600000,\"Peak Value\":21038,\"Type ID\":0,\"Min Size\":21000,\"Count\":21012,\"Type Name\":\"Property\"},\"OverriddenPegaRules\":{\"Size Estimate\":2262886,\"Prune Age\":3600000,\"Peak Value\":6382,\"Type ID\":2,\"Min Size\":30000,\"Count\":6382,\"Type Name\":\"OverriddenPegaRules\"},\"CEPEvent\":{\"Size Estimate\":86378,\"Prune Age\":3600000,\"Peak Value\":176,\"Type ID\":10,\"Min Size\":1500,\"Count\":176,\"Type Name\":\"CEPEvent\"}}";
		MatcherAssert.assertThat(JsonSchemaGeneratorUtils.generateSchema(json), JsonSchemaValidator.matchesJsonSchema(getSchemaFile("jsonschemas/multiple-objects-schema.json")));
	}
	
	@Test
	public void schemaArrayTestForJsonNode(){
		String json = "{\"Most Recently Used Caches Statistics\": [{\"Entries Removed By Drain Prune\":0,\"Soft to Hard Count\":0.0,\"Cache Type\":\"ClassInfoConclusionImpl\",\"Target Size\":21000,\"Entries Removed By Max Prune\":0,\"Hard to Soft Count\":0.0,\"Current Size\":2029,\"Number Of Drain Prunes Happened\":0,\"Size Limit\":42500,\"Number Of Max Prunes Happened\":0,\"Age In Minutes\":60,\"Entries Removed By Limit Prune\":0,\"Key\":\"Class\",\"Number Of Limit Prunes Happened\":0,\"Soft References GCed Count\":0.0,\"Max Size\":50000,\"Maintenance Time In Seconds\":0.0}]}";
		MatcherAssert.assertThat(JsonSchemaGeneratorUtils.generateSchema(json), JsonSchemaValidator.matchesJsonSchema(getSchemaFile("jsonschemas/array-with-one-objects-schema.json")));
	}
	
	@Test
	public void complexJson(){
		String json = "{\"apiVersion\":\"2.0\",\"data\":{\"updated\":\"2010-01-07T19:58:42.949Z\",\"totalItems\":800,\"startIndex\":1,\"itemsPerPage\":1,\"items\":[{\"id\":\"hYB0mn5zh2c\",\"uploaded\":\"2007-06-05T22:07:03.000Z\",\"updated\":\"2010-01-07T13:26:50.000Z\",\"uploader\":\"GoogleDeveloperDay\",\"category\":\"News\",\"title\":\"Google Developers Day US - Maps API Introduction\",\"description\":\"Google Maps API Introduction ...\",\"tags\":[\"GDD07\",\"GDD07US\",\"Maps\"],\"thumbnail\":{\"default\":\"http://i.ytimg.com/vi/hYB0mn5zh2c/default.jpg\",\"hqDefault\":\"http://i.ytimg.com/vi/hYB0mn5zh2c/hqdefault.jpg\"},\"player\":{\"default\":\"http://www.youtube.com/watch?vu003dhYB0mn5zh2c\"},\"content\":{\"1\":\"rtsp://v5.cache3.c.youtube.com/CiILENy.../0/0/0/video.3gp\",\"5\":\"http://www.youtube.com/v/hYB0mn5zh2c?f...\",\"6\":\"rtsp://v1.cache1.c.youtube.com/CiILENy.../0/0/0/video.3gp\"},\"duration\":2840,\"aspectRatio\":\"widescreen\",\"rating\":4.63,\"ratingCount\":68,\"viewCount\":220101,\"favoriteCount\":201,\"commentCount\":22,\"status\":{\"value\":\"restricted\",\"reason\":\"limitedSyndication\"},\"accessControl\":{\"syndicate\":\"allowed\",\"commentVote\":\"allowed\",\"rate\":\"allowed\",\"list\":\"allowed\",\"comment\":\"allowed\",\"embed\":\"allowed\",\"videoRespond\":\"moderated\"}}]}}";
		MatcherAssert.assertThat(JsonSchemaGeneratorUtils.generateSchema(json), JsonSchemaValidator.matchesJsonSchema(getSchemaFile("jsonschemas/complex-schema.json")));
	}
	
	@Test
	public void schemaArrayForNode(){
		String json = "{\"Most Recently Used Caches Statistics\": [{\"Entries Removed By Drain Prune\":0,\"Soft to Hard Count\":0.0,\"Cache Type\":\"ClassInfoConclusionImpl\",\"Target Size\":21000,\"Entries Removed By Max Prune\":0,\"Hard to Soft Count\":0.0,\"Current Size\":2029,\"Number Of Drain Prunes Happened\":0,\"Size Limit\":42500,\"Number Of Max Prunes Happened\":0,\"Age In Minutes\":60,\"Entries Removed By Limit Prune\":0,\"Key\":\"Class\",\"Number Of Limit Prunes Happened\":0,\"Soft References GCed Count\":0.0,\"Max Size\":50000,\"Maintenance Time In Seconds\":0.0}]}";
		MatcherAssert.assertThat(JsonSchemaGeneratorUtils.generateSchema(json), JsonSchemaValidator.matchesJsonSchema(getSchemaFile("jsonschemas/array-with-objects-schema.json")));
	}
	
	@Test
	public void arrayScalarTopLevel(){
		String json = "{\r\n  \"array\":[\r\n     \"test\",\r\n    \"test2\"\r\n  ]\r\n\r\n}\r\n";
		MatcherAssert.assertThat(JsonSchemaGeneratorUtils.generateSchema(json), JsonSchemaValidator.matchesJsonSchema(getSchemaFile("jsonschemas/array-scalar-schema.json")));
	}
	
	@Test
	public void jsonWithOneScalarProperty(){
		String json = "{\r\n\"Error\": \"Specified Node ID is invalid\"\r\n}";
		MatcherAssert.assertThat(JsonSchemaGeneratorUtils.generateSchema(json), JsonSchemaValidator.matchesJsonSchema(getSchemaFile("jsonschemas/one-scalar-schema.json")));
	}
	
	@Test
	public void allDatatypesTopLevelAndObjectLevel(){
		String json = "{\r\n   \"bool\": true,\r\n   \"str\": \"hello\",\r\n    \"int\": 10,\r\n    \"number\": 10.9,\r\n  \"innerObj\": {\r\n    \"str\": \"hello\",\r\n    \"int\": 10,\r\n    \"number\": 10.9,\r\n    \"bool\": true\r\n  }\r\n}";
		MatcherAssert.assertThat(JsonSchemaGeneratorUtils.generateSchema(json), JsonSchemaValidator.matchesJsonSchema(getSchemaFile("jsonschemas/all-datatypes-schema.json")));
	}

	private File getSchemaFile(String resourceSchemaName) {
		URL schemaDir = this.getClass().getClassLoader().getResource(resourceSchemaName);
		try {
			return new File(schemaDir.toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	static ObjectMapper mapper = new ObjectMapper();
}
