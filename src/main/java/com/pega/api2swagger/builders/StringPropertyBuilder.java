package com.pega.api2swagger.builders;

import io.swagger.models.properties.Property;
import io.swagger.models.properties.StringProperty;

public class StringPropertyBuilder implements PropertyBuilder {
    private String format;
	private String example;

    public StringPropertyBuilder withFormat(String format) {
        this.format = format;
        return this;
    }
    
    public StringPropertyBuilder withExample(String example){
    	this.example = example;
    	return this;
    }

    public Property build() {
        StringProperty property = new StringProperty();
        property.setFormat(format);
        property.setExample(example);
        return property;
    }
}
