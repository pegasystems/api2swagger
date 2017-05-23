package com.pega.api2swagger.builders;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.pega.api2swagger.builders.model.BooleanPropertyBuilder;
import com.pega.api2swagger.builders.model.IntegerPropertyBuilder;
import com.pega.api2swagger.builders.model.NumberPropertyBuilder;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.Property;

public class ModelBuilder {
    private final Map<String, PropertyBuilder> propertyBuilders = Maps.newLinkedHashMap();
    private String type;

    public ModelBuilder withTypeObject() {
        type = "object";
        return this;
    }

    public ModelBuilder withTypeString() {
        type = "string";
        return this;
    }

    public RefPropertyBuilder withReferencePropertyNamed(String propertyName){
        RefPropertyBuilder builder = new RefPropertyBuilder();
        propertyBuilders.put(propertyName, builder);
        return builder;
    }

    public StringPropertyBuilder withStringPropertyNamed(String propertyName) {
        StringPropertyBuilder propertyBuilder = new StringPropertyBuilder();
        propertyBuilders.put(propertyName, propertyBuilder);
        return propertyBuilder;
    }
    public IntegerPropertyBuilder withIntegerPropertyNamed(String propertyName) {
    	IntegerPropertyBuilder propertyBuilder = new IntegerPropertyBuilder();
        propertyBuilders.put(propertyName, propertyBuilder);
        return propertyBuilder;
    }
    
    public BooleanPropertyBuilder withBooleanPropertyNamed(String propertyName) {
    	BooleanPropertyBuilder propertyBuilder = new BooleanPropertyBuilder();
        propertyBuilders.put(propertyName, propertyBuilder);
        return propertyBuilder;
    }
    
    public NumberPropertyBuilder withNumberPropertyNamed(String propertyName) {
    	NumberPropertyBuilder propertyBuilder = new NumberPropertyBuilder();
        propertyBuilders.put(propertyName, propertyBuilder);
        return propertyBuilder;
    }

    public Model build() {
        ModelImpl model = new ModelImpl();
        model.setType(type);
        
        Map<String, Property> properties = new HashMap<>();
        
        for(Entry<String, PropertyBuilder> entry: propertyBuilders.entrySet()){
        	properties.put(entry.getKey(), entry.getValue().build());
        }
        
        model.setProperties(properties);
        return model;
    }

	public ArrayPropertyBuilder withArrayProperty(String name) {
		ArrayPropertyBuilder arrayBuilder = new ArrayPropertyBuilder();
		propertyBuilders.put(name, arrayBuilder);
		return arrayBuilder;
	}
	
	public ObjectPropertyBuilder withObjectProperty(String name){
		ObjectPropertyBuilder objectBuilder = new ObjectPropertyBuilder();
		propertyBuilders.put(name, objectBuilder);
		return objectBuilder;
	}
}
