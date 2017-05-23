package com.pega.api2swagger.updater;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class TestSwaggerUpdater {

	private final SwaggerParser mParser = new SwaggerParser();

	@Test
	public void testAddNewPath() {
		String baseSwaggerJson = "{\n \"swagger\": \"2.0\",\n \"info\": {\n" +
				"\"description\": \"Pega Operations API provides a set of built-in"+
				"REST services for Pega 7 applications monitoring. All APIs require"+
				"authentication. Provide the credentials for a Pega operator in the"+
				"Authorization header for each request. Pega recommends accessing APIs"+
				"using TLS 1.2.\",\n \"version\": \"v1\",\n \"title\": \"Pega API\"\n"+
				"},\n \"host\": \"localhost:8080\",\n \"basePath\":"+
				"\"/prweb/PRRestService\",\n \"paths\": {\n },\n \"definitions\": {\n"+
				"\n }\n}";

		
		String newSwaggerJson ="{\n  \"swagger\" : \"2.0\",\n  \"info\" : {\n    \"description\" : \"Pega Operations API provides a set of built-in REST services for Pega 7 applications monitoring. All APIs require authentication. Provide the credentials for a Pega operator in the Authorization header for each request. Pega recommends accessing APIs using TLS 1.2.\",\n    \"version\" : \"v1\",\n    \"title\" : \"Pega API\"\n  },\n  \"host\" : \"localhost:8080\",\n  \"basePath\" : \"/prweb/PRRestService\",\n  \"paths\" : {\n    \"/assignments\" : {\n      \"get\" : {\n        \"tags\" : [ \"assignments\" ],\n        \"summary\" : \"Get assignments\",\n        \"description\" : \"Returns the authenticated user's list of assignments.\",\n        \"operationId\" : \"getAssignments\",\n        \"produces\" : [ \"application/json\" ],\n        \"parameters\" : [ ],\n        \"responses\" : {\n          \"200\" : {\n            \"description\" : \"OK\",\n            \"schema\" : {\n              \"$ref\" : \"#/definitions/AssignmentsResponse\"\n            }\n          }\n        }\n      }\n    }\n  },\n  \"definitions\" : {\n    \"AssignmentsResponse\" : {\n      \"properties\" : {\n        \"pxObjClass\" : {\n          \"type\" : \"string\",\n          \"example\" : \"Pega-API-CaseManagement\"\n        },\n        \"assignments\" : {\n          \"type\" : \"array\",\n          \"items\" : {\n            \"$ref\" : \"#/definitions/Assignment\"\n          }\n        }\n      }\n    },\n    \"Assignment\" : {\n      \"properties\" : {\n        \"caseID\" : {\n          \"type\" : \"string\",\n          \"example\" : \"MYCO-PAC-WORK E-278\"\n        },\n        \"executedDeadlineTime\" : {\n          \"type\" : \"string\",\n          \"format\" : \"date-time\",\n          \"example\" : \"2015-02-05T13:00:44.443Z\"\n        },\n        \"executedGoalTime\" : {\n          \"type\" : \"string\",\n          \"format\" : \"date-time\",\n          \"example\" : \"2015-02-05T08:00:44.443Z\"\n        },\n        \"ID\" : {\n          \"type\" : \"string\",\n          \"example\" : \"ASSIGN-WORKLIST MYCO-PAC-WORK E-278!PZDEFAULTSTAGESTEP\"\n        },\n        \"name\" : {\n          \"type\" : \"string\",\n          \"example\" : \"Default step\"\n        },\n        \"pxObjClass\" : {\n          \"type\" : \"string\",\n          \"example\" : \"Pega-API-CaseManagement-Assignment\"\n        },\n        \"routedTo\" : {\n          \"type\" : \"string\",\n          \"example\" : \"user1\"\n        },\n        \"scheduledDeadlineTime\" : {\n          \"type\" : \"string\",\n          \"format\" : \"date-time\",\n          \"example\" : \"2015-02-05T13:00:44.443Z\"\n        },\n        \"scheduledGoalTime\" : {\n          \"type\" : \"string\",\n          \"format\" : \"date-time\",\n          \"example\" : \"2015-02-05T13:00:44.443Z\"\n        },\n        \"type\" : {\n          \"type\" : \"string\",\n          \"example\" : \"Worklist\"\n        },\n        \"urgency\" : {\n          \"type\" : \"string\",\n          \"example\" : \"10\"\n        },\n        \"instructions\" : {\n          \"type\" : \"string\",\n          \"example\" : \"Default stage\"\n        }\n      }\n    }\n  }\n}\n";
	
		
		String expectedSwagger ="{\n  \"swagger\" : \"2.0\",\n  \"info\" : {\n    \"description\" : \"Pega Operations API provides a set of built-inREST services for Pega 7 applications monitoring. All APIs requireauthentication. Provide the credentials for a Pega operator in theAuthorization header for each request. Pega recommends accessing APIsusing TLS 1.2.\",\n    \"version\" : \"v1\",\n    \"title\" : \"Pega API\"\n  },\n  \"host\" : \"localhost:8080\",\n  \"basePath\" : \"/prweb/PRRestService\",\n  \"paths\" : {\n    \"/assignments\" : {\n      \"get\" : {\n        \"tags\" : [ \"assignments\" ],\n        \"summary\" : \"Get assignments\",\n        \"description\" : \"Returns the authenticated user's list of assignments.\",\n        \"operationId\" : \"getAssignments\",\n        \"produces\" : [ \"application/json\" ],\n        \"parameters\" : [ ],\n        \"responses\" : {\n          \"200\" : {\n            \"description\" : \"OK\",\n            \"schema\" : {\n              \"$ref\" : \"#/definitions/AssignmentsResponse\"\n            }\n          }\n        }\n      }\n    }\n  },\n  \"definitions\" : {\n    \"AssignmentsResponse\" : {\n      \"properties\" : {\n        \"pxObjClass\" : {\n          \"type\" : \"string\",\n          \"example\" : \"Pega-API-CaseManagement\"\n        },\n        \"assignments\" : {\n          \"type\" : \"array\",\n          \"items\" : {\n            \"$ref\" : \"#/definitions/Assignment\"\n          }\n        }\n      }\n    },\n    \"Assignment\" : {\n      \"properties\" : {\n        \"caseID\" : {\n          \"type\" : \"string\",\n          \"example\" : \"MYCO-PAC-WORK E-278\"\n        },\n        \"executedDeadlineTime\" : {\n          \"type\" : \"string\",\n          \"format\" : \"date-time\",\n          \"example\" : \"2015-02-05T13:00:44.443Z\"\n        },\n        \"executedGoalTime\" : {\n          \"type\" : \"string\",\n          \"format\" : \"date-time\",\n          \"example\" : \"2015-02-05T08:00:44.443Z\"\n        },\n        \"ID\" : {\n          \"type\" : \"string\",\n          \"example\" : \"ASSIGN-WORKLIST MYCO-PAC-WORK E-278!PZDEFAULTSTAGESTEP\"\n        },\n        \"name\" : {\n          \"type\" : \"string\",\n          \"example\" : \"Default step\"\n        },\n        \"pxObjClass\" : {\n          \"type\" : \"string\",\n          \"example\" : \"Pega-API-CaseManagement-Assignment\"\n        },\n        \"routedTo\" : {\n          \"type\" : \"string\",\n          \"example\" : \"user1\"\n        },\n        \"scheduledDeadlineTime\" : {\n          \"type\" : \"string\",\n          \"format\" : \"date-time\",\n          \"example\" : \"2015-02-05T13:00:44.443Z\"\n        },\n        \"scheduledGoalTime\" : {\n          \"type\" : \"string\",\n          \"format\" : \"date-time\",\n          \"example\" : \"2015-02-05T13:00:44.443Z\"\n        },\n        \"type\" : {\n          \"type\" : \"string\",\n          \"example\" : \"Worklist\"\n        },\n        \"urgency\" : {\n          \"type\" : \"string\",\n          \"example\" : \"10\"\n        },\n        \"instructions\" : {\n          \"type\" : \"string\",\n          \"example\" : \"Default stage\"\n        }\n      }\n    }\n  }\n}"; 
		
		
		SwaggerUpdater updater = new SwaggerUpdater();
		Swagger swagger = updater.update(mParser.parse(baseSwaggerJson), mParser.parse(newSwaggerJson));
	
		assertEquals(mParser.parse(expectedSwagger), swagger);
	}
	
	@Test
	public void testMergePathResponces() {
	
	}
	
	
	@Test
	public void testMergePathOperations() {
	
	}
	
	private Swagger getExistingSwagger() {
		// String existingSwagger = "{\n \"swagger\": \"2.0\",\n \"info\": {\n
		// \"description\": \"Pega Operations API provides a set of built-in
		// REST services for Pega 7 applications monitoring. All APIs require
		// authentication. Provide the credentials for a Pega operator in the
		// Authorization header for each request. Pega recommends accessing APIs
		// using TLS 1.2.\",\n \"version\": \"v1\",\n \"title\": \"Pega API\"\n
		// },\n \"host\": \"localhost:8080\",\n \"basePath\":
		// \"/prweb/PRRestService\",\n \"paths\": {\n },\n \"definitions\": {\n
		// \n }\n}";

		String existingSwagger = "{\n  \"swagger\" : \"2.0\",\n  \"info\" : {\n    \"description\" : \"Pega Operations API provides a set of built-in REST services for Pega 7 applications monitoring. All APIs require authentication. Provide the credentials for a Pega operator in the Authorization header for each request. Pega recommends accessing APIs using TLS 1.2.\",\n    \"version\" : \"v1\",\n    \"title\" : \"Pega API\"\n  },\n  \"host\" : \"localhost:8080\",\n  \"basePath\" : \"/prweb/PRRestService\",\n  \"paths\" : {\n    \"/assignments\" : {\n      \"get\" : {\n        \"tags\" : [ \"assignments\" ],\n        \"summary\" : \"Get assignments\",\n        \"description\" : \"Returns the authenticated user's list of assignments.\",\n        \"operationId\" : \"getAssignments\",\n        \"produces\" : [ \"application/json\" ],\n        \"parameters\" : [ ],\n        \"responses\" : {\n          \"200\" : {\n            \"description\" : \"OK\",\n            \"schema\" : {\n              \"$ref\" : \"#/definitions/AssignmentsResponse\"\n            }\n          }\n        }\n      }\n    }\n  },\n  \"definitions\" : {\n    \"AssignmentsResponse\" : {\n      \"properties\" : {\n        \"pxObjClass\" : {\n          \"type\" : \"string\",\n          \"example\" : \"Pega-API-CaseManagement\"\n        },\n        \"assignments\" : {\n          \"type\" : \"array\",\n          \"items\" : {\n            \"$ref\" : \"#/definitions/Assignment\"\n          }\n        }\n      }\n    },\n    \"Assignment\" : {\n      \"properties\" : {\n        \"caseID\" : {\n          \"type\" : \"string\",\n          \"example\" : \"MYCO-PAC-WORK E-278\"\n        },\n        \"executedDeadlineTime\" : {\n          \"type\" : \"string\",\n          \"format\" : \"date-time\",\n          \"example\" : \"2015-02-05T13:00:44.443Z\"\n        },\n        \"executedGoalTime\" : {\n          \"type\" : \"string\",\n          \"format\" : \"date-time\",\n          \"example\" : \"2015-02-05T08:00:44.443Z\"\n        },\n        \"ID\" : {\n          \"type\" : \"string\",\n          \"example\" : \"ASSIGN-WORKLIST MYCO-PAC-WORK E-278!PZDEFAULTSTAGESTEP\"\n        },\n        \"name\" : {\n          \"type\" : \"string\",\n          \"example\" : \"Default step\"\n        },\n        \"pxObjClass\" : {\n          \"type\" : \"string\",\n          \"example\" : \"Pega-API-CaseManagement-Assignment\"\n        },\n        \"routedTo\" : {\n          \"type\" : \"string\",\n          \"example\" : \"user1\"\n        },\n        \"scheduledDeadlineTime\" : {\n          \"type\" : \"string\",\n          \"format\" : \"date-time\",\n          \"example\" : \"2015-02-05T13:00:44.443Z\"\n        },\n        \"scheduledGoalTime\" : {\n          \"type\" : \"string\",\n          \"format\" : \"date-time\",\n          \"example\" : \"2015-02-05T13:00:44.443Z\"\n        },\n        \"type\" : {\n          \"type\" : \"string\",\n          \"example\" : \"Worklist\"\n        },\n        \"urgency\" : {\n          \"type\" : \"string\",\n          \"example\" : \"10\"\n        },\n        \"instructions\" : {\n          \"type\" : \"string\",\n          \"example\" : \"Default stage\"\n        }\n      }\n    }\n  }\n}\n";
		return mParser.parse(existingSwagger);

	}

	private Swagger getnewSwagger() {
		// String newSwagger = "{\n \"swagger\": \"2.0\",\n \"info\": {\n
		// \"description\": \"A\",\n \"version\": \"1\",\n \"title\": \"Pega
		// API\"\n },\n \"host\": \"localhost:8080\",\n \"basePath\":
		// \"/prweb/PRRestService\",\n \"paths\": {\n \"/assignments\": {\n
		// \"get\": {\n \"tags\": [\n \"assignments\"\n ],\n \"summary\": \"Get
		// assignments\",\n \"description\": \"Returns the authenticated user's
		// list of assignments.\",\n \"operationId\": \"getAssignments\",\n
		// \"produces\": [\n \"application/json\"\n ],\n \"parameters\": [],\n
		// \"responses\": {\n \"200\": {\n \"description\": \"OK\",\n
		// \"schema\": {\n \"$ref\": \"#/definitions/AssignmentsResponse\"\n }\n
		// }\n }\n }\n }\n },\n \n \"definitions\": {\n
		// \n\"AssignmentsResponse\": {\n \"properties\": {\n \"pxObjClass\":
		// {\n \"type\": \"string\",\n \"example\":
		// \"Pega-API-CaseManagement\"\n },\n \"assignments\": {\n \"type\":
		// \"array\",\n \"items\": {\n \"$ref\": \"#/definitions/Assignment\"\n
		// }\n }\n }\n },\n\n \"Assignment\": {\n \"properties\": {\n
		// \"caseID\": {\n \"type\": \"string\",\n \"example\": \"MYCO-PAC-WORK
		// E-278\"\n },\n \"executedDeadlineTime\": {\n \"type\": \"string\",\n
		// \"format\": \"date-time\",\n \"example\":
		// \"2015-02-05T13:00:44.443Z\"\n },\n \"executedGoalTime\": {\n
		// \"type\": \"string\",\n \"format\": \"date-time\",\n \"example\":
		// \"2015-02-05T08:00:44.443Z\"\n },\n \"ID\": {\n \"type\":
		// \"string\",\n \"example\": \"ASSIGN-WORKLIST MYCO-PAC-WORK
		// E-278!PZDEFAULTSTAGESTEP\"\n },\n \"name\": {\n \"type\":
		// \"string\",\n \"example\": \"Default step\"\n },\n \"pxObjClass\":
		// {\n \"type\": \"string\",\n \"example\":
		// \"Pega-API-CaseManagement-Assignment\"\n },\n \"routedTo\": {\n
		// \"type\": \"string\",\n \"example\": \"user1\"\n },\n
		// \"scheduledDeadlineTime\": {\n \"type\": \"string\",\n \"format\":
		// \"date-time\",\n \"example\": \"2015-02-05T13:00:44.443Z\"\n },\n
		// \"scheduledGoalTime\": {\n \"type\": \"string\",\n \"format\":
		// \"date-time\",\n \"example\": \"2015-02-05T13:00:44.443Z\"\n },\n
		// \"type\": {\n \"type\": \"string\",\n \"example\": \"Worklist\"\n
		// },\n \"urgency\": {\n \"type\": \"string\",\n \"example\": \"10\"\n
		// },\n \"instructions\": {\n \"type\": \"string\",\n \"example\":
		// \"Default stage\"\n }\n }\n }\t\n }\n}";

		String newSwagger = "{\n  \"swagger\": \"2.0\",\n  \"info\": {\n    \"description\": \"A\",\n    \"version\": \"1\",\n    \"title\": \"Pega API\"\n  },\n  \"host\": \"localhost:8080\",\n  \"basePath\": \"/prweb/PRRestService\",\n  \"paths\": {\n  \"/assignments\": {\n      \"get\": {\n        \"tags\": [\n          \"assignments\"\n        ],\n        \"summary\": \"Get assignments\",\n        \"description\": \"Returns the authenticated user's list of assignments.\",\n        \"operationId\": \"getAssignments\",\n        \"produces\": [\n          \"application/json\"\n        ],\n        \"parameters\": [],\n        \"responses\": {\n          \"400\": {\n            \"description\": \"OK\",\n            \"schema\": {\n              \"$ref\": \"#/definitions/AssignmentsResponse\"\n            }\n          }\n          }\n        }\n      }\n    },\n  \n  \"definitions\": {\n  \n\"AssignmentsResponse\": {\n      \"properties\": {\n        \"pxObjClass\": {\n          \"type\": \"string\",\n          \"example\": \"Pega-API-CaseManagement\"\n        },\n        \"assignments\": {\n          \"type\": \"array\",\n          \"items\": {\n            \"$ref\": \"#/definitions/Assignment\"\n          }\n        }\n      }\n    },\n\n \"Assignment\": {\n      \"properties\": {\n        \"caseID\": {\n          \"type\": \"string\",\n          \"example\": \"MYCO-PAC-WORK E-278\"\n        },\n        \"executedDeadlineTime\": {\n          \"type\": \"string\",\n          \"format\": \"date-time\",\n          \"example\": \"2015-02-05T13:00:44.443Z\"\n        },\n        \"executedGoalTime\": {\n          \"type\": \"string\",\n          \"format\": \"date-time\",\n          \"example\": \"2015-02-05T08:00:44.443Z\"\n        },\n        \"ID\": {\n          \"type\": \"string\",\n          \"example\": \"ASSIGN-WORKLIST MYCO-PAC-WORK E-278!PZDEFAULTSTAGESTEP\"\n        },\n        \"name\": {\n          \"type\": \"string\",\n          \"example\": \"Default step\"\n        },\n        \"pxObjClass\": {\n          \"type\": \"string\",\n          \"example\": \"Pega-API-CaseManagement-Assignment\"\n        },\n        \"routedTo\": {\n          \"type\": \"string\",\n          \"example\": \"user1\"\n        },\n        \"scheduledDeadlineTime\": {\n          \"type\": \"string\",\n          \"format\": \"date-time\",\n          \"example\": \"2015-02-05T13:00:44.443Z\"\n        },\n        \"scheduledGoalTime\": {\n          \"type\": \"string\",\n          \"format\": \"date-time\",\n          \"example\": \"2015-02-05T13:00:44.443Z\"\n        },\n        \"type\": {\n          \"type\": \"string\",\n          \"example\": \"Worklist\"\n        },\n        \"urgency\": {\n          \"type\": \"string\",\n          \"example\": \"10\"\n        },\n        \"instructions\": {\n          \"type\": \"string\",\n          \"example\": \"Default stage\"\n        }\n      }\n    }\t\n  }\n}";
		return mParser.parse(newSwagger);
	}

}