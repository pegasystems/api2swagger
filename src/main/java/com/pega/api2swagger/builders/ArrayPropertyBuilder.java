package com.pega.api2swagger.builders;

import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.Property;

public class ArrayPropertyBuilder implements PropertyBuilder {

	private Property properties;
	private ArrayProperty arrayProperty;

	public ArrayPropertyBuilder() {
		arrayProperty = new ArrayProperty();
	}
	
	public void withItems(Property properties){
		this.properties = properties;
	}
	
	@Override
	public Property build() {
		arrayProperty.setItems(this.properties);
        return arrayProperty;
	}

}
