package com.kardass.jsmatep.codegenerator.java;

public class PropertyConfiguration {
	
	private String name;

	private String type;

	public PropertyConfiguration() {
		super();
	}
	
	public PropertyConfiguration(String propertyName, String type) {
		super();
		this.name = propertyName;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String propertyName) {
		this.name = propertyName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
