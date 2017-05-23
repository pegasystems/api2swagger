package com.pega.api2swagger.builders;

import static java.lang.Boolean.TRUE;

import com.google.common.base.Optional;

import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import io.swagger.models.parameters.RefParameter;

public class ParameterBuilder {
    private Optional<String> maybeReferenceToADefinition = Optional.absent();
    private Optional<String> maybeReferenceToAParameterDefinition = Optional.absent();
    private Optional<String> maybeAName = Optional.absent();
    private Optional<String> maybeType = Optional.absent();
    private Optional<String> maybeDescription = Optional.absent();
    private Optional<Boolean> maybeRequired = Optional.absent();
    private boolean headerParameter;
    private boolean pathParameter;
   // private boolean bodyParameter;
    private boolean queryParameter;
    
    public ParameterBuilder inHeader() {
        this.headerParameter = true;
        return this;
    }

    public ParameterBuilder inPath() {
        this.pathParameter = true;
        return this;
    }
    
    public ParameterBuilder inQuery(){
    	this.queryParameter = true;
    	return this;
    }

    public ParameterBuilder referencingModelDefinition(String definitionId) {
        maybeReferenceToADefinition = Optional.of(definitionId);
        return this;
    }

    public ParameterBuilder referencingParameterDefinition(String parameterKey) {
        maybeReferenceToAParameterDefinition = Optional.fromNullable(parameterKey);
        return this;
    }

    public ParameterBuilder withName(String name) {
        maybeAName = Optional.fromNullable(name);
        return this;
    }

    public ParameterBuilder ofTypeString() {
        maybeType = Optional.of("string");
        return this;
    }

    public ParameterBuilder withDescription(String description) {
        maybeDescription = Optional.of(description);
        return this;
    }

    public ParameterBuilder required() {
        maybeRequired = Optional.of(TRUE);
        return this;
    }

    public Parameter build() {
        if (queryParameter) {
        	QueryParameter queryParameter = new QueryParameter();
           /* if(maybeReferenceToADefinition.isPresent()){
            	bodyParameter.setSchema(new RefModel(maybeReferenceToADefinition.get()));
            }*/
            
            if(maybeAName.isPresent()){
            	queryParameter.setName(maybeAName.get());
            }
            if(maybeType.isPresent()){
            	queryParameter.setType(maybeType.get());
            }
            
            if(maybeDescription.isPresent()){
            	queryParameter.setDescription(maybeDescription.get());
            }
            
            if(maybeRequired.isPresent()){
            	queryParameter.setRequired(maybeRequired.get());
            }
            
            return queryParameter;
        }

        if (maybeReferenceToAParameterDefinition.isPresent()) {
            return new RefParameter(maybeReferenceToAParameterDefinition.get());
        }

        if (headerParameter) {
            HeaderParameter headerParameter = new HeaderParameter();
            
            if(maybeAName.isPresent()){
            	headerParameter.setName(maybeAName.get());
            }
            
            if(maybeType.isPresent()){
            	headerParameter.setType(maybeType.get());
            }
            
            if(maybeDescription.isPresent()){
            	headerParameter.setDescription(maybeDescription.get());
            }
            
            if(maybeRequired.isPresent()){
            	headerParameter.setRequired(maybeRequired.get());
            }
            
            return headerParameter;
        }

        if (pathParameter) {
            PathParameter pathParameter = new PathParameter();
            pathParameter.setName(maybeAName.get());
            pathParameter.setType(maybeType.get());
            
            if(maybeDescription.isPresent()){
            	pathParameter.setDescription(maybeDescription.get());
            }
            return pathParameter;
        }
        throw new UnsupportedOperationException("Where do you want this parameter to be?");
    }
}
