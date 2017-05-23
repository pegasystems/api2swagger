package com.pega.api2swagger.builders.model;

import com.pega.api2swagger.builders.PropertyBuilder;

import io.swagger.models.properties.IntegerProperty;
import io.swagger.models.properties.Property;

public class IntegerPropertyBuilder implements  PropertyBuilder {
	
	private String format;
	private Long example;

	public IntegerPropertyBuilder withFormat(String format) {
		this.format = format;
		return this;
	}

	public IntegerPropertyBuilder withExample(Long example) {
		this.example = example;
		return this;
	}

	public Property build() {
		IntegerProperty property = new IntegerProperty();
		property.setFormat(format);
		property.setExample(example);
		return property;
	}
}
