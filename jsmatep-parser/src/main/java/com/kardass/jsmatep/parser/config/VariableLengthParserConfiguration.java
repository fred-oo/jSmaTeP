package com.kardass.jsmatep.parser.config;

import java.math.BigInteger;
import java.util.List;

import com.kardass.jsmatep.parser.config.field.Field;

/**
 * Parser configuration for variable length data records.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class VariableLengthParserConfiguration extends ParserConfiguration {

	/** Data records min. length */
	private final Integer minRecordLength;
	
	/** Data records max. length */
	private final Integer maxRecordLength;
	
	/** Filler to reach max. length */
	private final String fillWith;

	/**
	 * @param valueObject
	 * @param continueOnRecordError
	 * @param version
	 * @param fields
	 * @param minRecordLength
	 * @param maxRecordLength
	 * @param fillWith
	 */
	VariableLengthParserConfiguration(String valueObject,
			boolean continueOnRecordError, String version, List<Field<?>> fields,
			BigInteger minRecordLength, BigInteger maxRecordLength, String fillWith,
			List<String> skipRecordsIdents) {
		super(ParserType.VARIABLE_LENGTH, valueObject, continueOnRecordError, version, fields, skipRecordsIdents);
		this.minRecordLength = minRecordLength == null ? null : minRecordLength.intValue();
		this.maxRecordLength = maxRecordLength == null ? null : maxRecordLength.intValue();
		this.fillWith = fillWith;
	}

	/**
	 * @return
	 * 		Records min. length
	 */
	public Integer getMinRecordLength() {
		return minRecordLength;
	}

	/**
	 * @return
	 * 		Records max. length.
	 */
	public Integer getMaxRecordLength() {
		return maxRecordLength;
	}

	/**
	 * @return
	 * 		Filler to reach max. length.
	 */
	public String getFillWith() {
		return fillWith;
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.ParserConfiguration#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder()
			.append(getClass()).append('[')
			.append("fillWith=").append(fillWith)
			.append(", maxRecordLength=").append(maxRecordLength)
			.append(", minRecordLength=").append(minRecordLength)
			.append(", ").append(super.toString())
			.append("]").toString();
	}


}
