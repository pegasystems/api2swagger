package com.pega.api2swagger.builders;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.swagger.models.Swagger;

public class SwaggerBuilder {

    private final InfoBuilder infoBuilder = new InfoBuilder();
    private final Map<String, PathBuilder> paths = Maps.newLinkedHashMap();
    private final List<TagDefinitionBuilder> tags = Lists.newArrayList();
    private final Map<String, ModelBuilder> definitions = Maps.newLinkedHashMap();
    private final Map<String, ParameterBuilder> parameters = Maps.newLinkedHashMap();
    private final Map<String, ResponseBuilder> responses = Maps.newLinkedHashMap();
	private String basepath;
	private String host;

    public InfoBuilder withInfo() {
        return infoBuilder;
    }

    public PathBuilder withPath(String path) {
        PathBuilder pathBuilder = new PathBuilder();
        paths.put(path, pathBuilder);
        return pathBuilder;
    }

    public TagDefinitionBuilder defineTag(String tagName) {
        TagDefinitionBuilder tagDefinitionBuilder = new TagDefinitionBuilder();
        tags.add(tagDefinitionBuilder);
        return tagDefinitionBuilder.withName(tagName);
    }

    public ModelBuilder withModelDefinition(String modelIdentifier) {
        ModelBuilder modelBuilder = new ModelBuilder();
        definitions.put(modelIdentifier, modelBuilder);
        return modelBuilder;
    }

    public ParameterBuilder withParameterDefinition(String parameterIdentifier) {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        withParameterDefinition(parameterIdentifier, parameterBuilder);
        return parameterBuilder;
    }

    public SwaggerBuilder withParameterDefinition(String parameterIdentifier, ParameterBuilder parameterBuilder) {
        parameters.put(parameterIdentifier, parameterBuilder);
        return this;
    }

    public SwaggerBuilder withResponseDefinition(String responseIdentifier, ResponseBuilder responseBuilder) {
        responses.put(responseIdentifier, responseBuilder);
        return this;
    }

    public Swagger build() {
        Swagger swagger = new Swagger();
        swagger.basePath(basepath);
        swagger.host(host);
        swagger.setInfo(infoBuilder.build());
        
        for(TagDefinitionBuilder tagBuilder: tags){
        	swagger.addTag(tagBuilder.build());
        }
        
        for(Map.Entry<String, PathBuilder> entry: paths.entrySet()){
        	 swagger.path(entry.getKey(), entry.getValue().build());
        }
        
        for(Entry<String, ModelBuilder> entry: definitions.entrySet()){
       	 swagger.addDefinition(entry.getKey(), entry.getValue().build());
        }
        
        for(Entry<String, ParameterBuilder> entry: parameters.entrySet()){
          	 swagger.addParameter(entry.getKey(), entry.getValue().build());
        }
        
        for(Entry<String, ResponseBuilder> entry: responses.entrySet()){
         	 swagger.response(entry.getKey(), entry.getValue().build());
       }
        
        return swagger;
    }

	public void withBasePath(String basepath) {
		this.basepath = basepath;
	}
	
	public void withHost(String host) {
		this.host = host;
	}

	public void withModelNamed(String aModelName, ModelBuilder aModelBuilder) {
		definitions.put(aModelName, aModelBuilder);
	}
}
