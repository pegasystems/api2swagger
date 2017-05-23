package com.pega.api2swagger.builders;

import com.google.common.base.Optional;

import io.swagger.models.RefResponse;
import io.swagger.models.Response;

public class ResponseBuilder {
    private Optional<String> maybeResponseDefinitionIdentifier = Optional.absent();
    private Optional<String> maybeDescription = Optional.absent();
    private Optional<PropertyBuilder> maybePropertyBuilder = Optional.absent();

    public ResponseBuilder withDescription(String description) {
        maybeDescription = Optional.of(description);
        return this;
    }

    public ResponseBuilder referencingResponseDefinition(String responseDefinitionIdentifier){
        maybeResponseDefinitionIdentifier = Optional.fromNullable(responseDefinitionIdentifier);
        return this;
    }

    public ResponseBuilder withSchema(PropertyBuilder propertyBuilder){
        this.maybePropertyBuilder = Optional.of(propertyBuilder);
        return this;
    }

    public Response build() {
    	
    	Response response = null;
    	
    	if(maybeResponseDefinitionIdentifier.isPresent()){
    		response = new RefResponse(maybeResponseDefinitionIdentifier.get());
    	}else{
    		response = new Response();
    	}
    	
    	if(maybePropertyBuilder.isPresent()){
    		response.setSchema(maybePropertyBuilder.get().build());
    	}
    	
    	if(maybeDescription.isPresent()){
    		response.setDescription(maybeDescription.get());
    	}
    	
        return response;
    }
}
