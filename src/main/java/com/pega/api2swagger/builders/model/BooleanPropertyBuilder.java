package com.pega.api2swagger.builders.model;

import com.pega.api2swagger.builders.PropertyBuilder;

import io.swagger.models.properties.BooleanProperty;
import io.swagger.models.properties.Property;

public class BooleanPropertyBuilder implements PropertyBuilder {
	private String format;
	private Boolean example;

	public BooleanPropertyBuilder withFormat(String format) {
		this.format = format;
		return this;
	}

	public BooleanPropertyBuilder withExample(Boolean example) {
		this.example = example;
		return this;
	}

	public Property build() {
		BooleanProperty property = new BooleanProperty();
		property.setFormat(format);
		property.setExample(example);
		return property;
	}
}
