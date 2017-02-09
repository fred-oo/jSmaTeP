package com.kardass.jsmatep.codegenerator.java;

import java.util.ArrayList;
import java.util.List;

public class ValueObjectConfiguration {

	private String className;
	
	private List<PropertyConfiguration> properties = new ArrayList<PropertyConfiguration>();

	public ValueObjectConfiguration(String className) {
		super();
		setClassName(className);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public List<String> getAllPropertyNames() {
		final List<String> result = new ArrayList<String>();
		for (PropertyConfiguration aField : properties) {
			result.add(aField.getName());
		}
		return result;
	}
	
	public void addProperty(PropertyConfiguration p) {
		properties.add(p);
	}
	
	public List<PropertyConfiguration> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyConfiguration> properties) {
		this.properties = properties;
	}

}
