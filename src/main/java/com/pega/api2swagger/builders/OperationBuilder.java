package com.pega.api2swagger.builders;

import static com.google.common.collect.Lists.newArrayList;
import java.util.List;
import io.swagger.models.Operation;

public class OperationBuilder {
    private final List<String> tags = newArrayList();
    private final List<ParameterBuilder> parameters = newArrayList();
    private final List<Consumer<Operation>> responses = newArrayList();
    private String summary;
    private String description;
    private List<String> produces = newArrayList();
    
    public OperationBuilder withTags(List<String> tag) {
        tags.addAll(tag);
        return this;
    }

    public ParameterBuilder withParameter(String name) {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        withParameter(name, parameterBuilder);
        return parameterBuilder;
    }

    public OperationBuilder withParameter(String name, ParameterBuilder parameterBuilder) {
        parameterBuilder.withName(name);
        parameters.add(parameterBuilder);
        return this;
    }
    
    public OperationBuilder withSummary(String aSummary) {
    	summary = aSummary;
    	return this;
    }
    
    public OperationBuilder withProduceMimeType(String aMimeType) {
    	produces.add(aMimeType);
    	return this;
    }
    
    public OperationBuilder withDescription(String aDescription) {
    	description = aDescription;
    	return this;
    }

    public OperationBuilder withResponse(final String httpStatusCode, final ResponseBuilder responseBuilder) {
    	
    	this.responses.add(new Consumer<Operation>() {
			@Override
			public void accept(Operation operation) {
				operation.addResponse(httpStatusCode, responseBuilder.build());
			}
		});
    	
        return this;
    }

    public Operation build() {
        Operation operation = new Operation();
        if (!tags.isEmpty()) {
            operation.setTags(newArrayList(tags));
        }
        
        for(ParameterBuilder builder: parameters){
        	operation.addParameter(builder.build());
        }
        
        for(Consumer<Operation> consumer: responses){
        	consumer.accept(operation);
        }
        
        operation.setDescription(description);
        operation.setSummary(summary);
        operation.setProduces(produces);
        
        return operation;
    }
}
