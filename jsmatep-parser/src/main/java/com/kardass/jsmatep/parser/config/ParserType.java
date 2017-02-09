package com.kardass.jsmatep.parser.config;

/**
 * Types of supported parsers.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public enum ParserType {

	FIXED_LENGTH(0), 
	CSV(1), 
	VARIABLE_LENGTH(2); 

	private final Integer type;

	private ParserType(Integer type) {
		this.type = type;
	}

	public Integer getTypeAsInt() {
		return type;
	}
	
}
