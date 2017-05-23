package com.pega.api2swagger.builders.model;

import com.pega.api2swagger.builders.PropertyBuilder;

import io.swagger.models.properties.FileProperty;
import io.swagger.models.properties.Property;

public class FilePropertyBuilder implements PropertyBuilder {

	@Override
	public Property build() {
		 FileProperty property = new FileProperty();
		return property;
	}

}
