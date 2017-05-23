package com.pega.api2swagger.builders.model;

import com.pega.api2swagger.builders.PropertyBuilder;

import io.swagger.models.properties.DecimalProperty;
import io.swagger.models.properties.Property;

public class NumberPropertyBuilder implements PropertyBuilder {
	private String format;
	private Double example;

	public NumberPropertyBuilder withFormat(String format) {
		this.format = format;
		return this;
	}

	public NumberPropertyBuilder withExample(Double example) {
		this.example = example;
		return this;
	}

	public Property build() {
		DecimalProperty property = new DecimalProperty();
		property.setFormat(format);
		property.setExample(example);
		return property;
	}
}
