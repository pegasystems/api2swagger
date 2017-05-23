# API2Swagger

<a href="https://www.pega.com">
<img src="https://www.pega.com/profiles/pegasystems/themes/custom/pegas/pegakit/public/images/logos/pega-logo.svg" width="200" alt="Pegasystems"/>
</a>

This document illustrate about library that we have developed to generate swagger documentation given a REST API. We had a need to generate swagger documentation for more than 50 APIs which is be impossible to handcraft and maintain. 

We have scoured if there is any off-the-shelf open-source library which would satisfy all our needs, but unfortunately there isn't one, so we developed this library which would take API URL as input and generate swagger document adhereing to swagger model schema by generating JSON schema from JSON response data. 

We have seen that people were looking such auto-conversion tool in open source community for non-bean based response schema.

# Features!

  - Swagger doc generation with usage of builders
  - JSON schema generation from JSON response data
  - Capability of updating existing Swagger JSON document with new API.
  - Capability of modifying existing Swagger JSON with API related modifications - which includes adding new request parameters, response changes.
  - Capability of recognizing 'FILE' downloads via REST API because Swagger JSON document has special way treating for FILE downloads

### Installation

This project is built on Maven, you can checkout the project and do maven build, just perform maven command which will generate a jar with all the neccesary dependencies:

```bat
mvn clean build
```
### Command Line Arguments

Various command line options are:

| Argument | Human Readable Form |Description |
| ---------- | ---------- | ---------- |
| -e | --endpoint | specify endpoint URL, this URL will be invoked and response will be generated. |
| -o | \-\-output | output file to which Swagger JSON will be written |
| -m | \-\-method | specify HTTP verb - GET, POST on which endpoint URL will be invoked |
| -h | \-\-header |specify any request headers that should be sent when endpoint URL is invoked |
| -p | \-\-param | specify request param in key value params e.g: param=value, you can specify multiple \-p arguments |
| -pp | \-\-pathparam | specify path parameter values part of endpoint URL, path parameters should  be specified in endpoint as "{pathParameterName}" and its value specified using this argument. You can specify multiple path parameters. please refer examples section for more details. |
| -a | \-\-authentication |Acts as a boolean command line arguments specifying whether authentication is required for invoking endpoint URL |
| -username | \-\-username | Username for authentication|
| -password | \-\-password | Password for authentication |
| -host | \-\-host | Hostname value will be populated in Swagger JSON's 'host' attribute |
| -apipath | \-\-apipath | Base Path for Swagger document |
| -apiname | \-\-apiname | Unique API name for a given endpoint URL |
| -apidescription | \-\-apidescription | API Description for a given endpoint URL |
| -apisummary | \-\-apisummary | Summary for given endpoint URL |
| -tag | \-\-apiTag | Tag name for given endpoint URL. You can provide multiple tag names for the endpoint |
| -basepath | \-\-basepath | Base Path for Swagger JSON's 'basepath' attribute, Swagger API path is calculated from base path |
| -help | \-\-help | Display help |

### CLI Examples

GET API without any parameters and authentication:

```sh
<JAVA_HOME>/bin/java" -jar %JAR% -e "http://httpbin.org/ip" -o "SWAGGER-DOC-FILE-PATH" -m "GET" -apiname "UniqueApiName" -tag "GetIPAddress" -apiSummary "API to fetch IP Address" -apidescription "Fetch IP address in origin attribute"
```
GET API with authentication using -a, --username and --password:

```sh
<JAVA_HOME>/bin/java" -jar %JAR% -e "http://httpbin.org/ip" -o "SWAGGER-DOC-FILE-PATH" -m "GET" -a --username "someusername" --password "somePassword" -apiname "UniqueApiName" -tag "GetIPAddress" -apisummary "API to fetch IP Address" -apidescription "Fetch IP address in origin attribute"
```
GET API with query parameters using -p argument:

```sh
<JAVA_HOME>/bin/java" -jar %JAR% -e "http://httpbin.org/ip" -o "SWAGGER-DOC-FILE-PATH" -m "GET" -a --username "someusername" --password "somePassword" -p "param1=value1" -p "param2=value2" -apiname "UniqueApiName" -tag "GetIPAddress" -apisummary "API to fetch IP Address" -apidescription "Fetch IP address in origin attribute"
```

GET API with path parameters using -pp argument:

```sh
<JAVA_HOME>/bin/java" -jar %JAR% -e "http://httpbin.org/ip/{pathparam1}/{pathparam2}" -o "SWAGGER-DOC-FILE-PATH" -m "GET" -a --username "someusername" --password "somePassword" -pp "pathparam1=value1" -pp "pathparam2=value2" -apiname "UniqueApiName" -tag "GetIPAddress" -apisummary "API to fetch IP Address" -apidescription "Fetch IP address in origin attribute"
```

GET API with path parameters using -pp argument and query parameters using -p:

```sh
<JAVA_HOME>/bin/java" -jar %JAR% -e "http://httpbin.org/ip/{pathparam1}/{pathparam2}" -o "SWAGGER-DOC-FILE-PATH" -m "GET" -a --username "someusername" --password "somePassword" -pp "pathparam1=value1" -pp "pathparam2=value2" -p "queryparam=value" -apiname "UniqueApiName" -tag "GetIPAddress" -apisummary "API to fetch IP Address" -apidescription "Fetch IP address in origin attribute"
```

POST API with path parameters using -pp argument and query parameters using -p:

```sh
<JAVA_HOME>/bin/java" -jar %JAR% -e "http://httpbin.org/ip/{pathparam1}/{pathparam2}" -o "SWAGGER-DOC-FILE-PATH" -m "POST" -a --username "someusername" --password "somePassword" -pp "pathparam1=value1" -pp "pathparam2=value2" -p "queryparam=value" -apiname "UniqueApiName" -tag "GetIPAddress" -apisummary "API to fetch IP Address" -apidescription "Fetch IP address in origin attribute"
```

DELETE API with parameters:

```sh
<JAVA_HOME>/bin/java" -jar %JAR% -e "http://httpbin.org/ip/{pathparam1}/{pathparam2}" -o "SWAGGER-DOC-FILE-PATH" -m "DELETE" -a --username "someusername" --password "somePassword" -pp "pathparam1=value1" -pp "pathparam2=value2" -p "queryparam=value" -apiname "UniqueApiName" -tag "GetIPAddress" -apisummary "API to fetch IP Address" -apidescription "Fetch IP address in origin attribute"
```

### Swagger Model generation using APIs

You can use `SwaggerGenerator` class for generating Swagger JSON, just pass `SwaggerGeneratorInput` to generator class..voila it will generate `Swagger` model for you!

### Example for generating Swagger Model using API

```java
SwaggerGeneratorInput userInput = new SwaggerGeneratorInput.Builder()
                                 .endpoint(endpoint)
								 .method(method)
								 .pathParams(pathParamMap)
								 .parameters(paramMap)
								 .headers(headerMap)
								 .swaggerJSONFilePath(outputFilePath)
								 .authentication(isAuthenticationNeeded)
								 .username(username)
								 .password(password)
								 .host(host)
								 .basePath(basePath)
								 .apiName(apiName)
								 .apiDescription(apiDescription)
								 .apiSummary(apiSummary)
								 .apiTags(apiTags)
								 .build();
SwaggerGenerator main = new SwaggerGenerator(userInput);
Optional<Swagger> outputSwagger = main.buildSwaggerJson();
if(outputSwagger.isPresent()){
		//then do something...
}
```

### Development

Want to contribute? Great!
Open an issue or email me providing your feedback or open a pull request.

### Todos

 - Consider JSON request data
 - Consider to emit response headers in Swagger JSON document
 - Consider to generate Swagger document in XML format
 - Fix minor bugs
