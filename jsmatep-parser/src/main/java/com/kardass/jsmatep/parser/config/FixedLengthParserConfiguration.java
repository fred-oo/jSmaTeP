package com.kardass.jsmatep.parser.config;

import java.util.List;

import com.kardass.jsmatep.parser.config.field.Field;

/**
 * Parser configuration for fixed length data records.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class FixedLengthParserConfiguration extends ParserConfiguration {

	/** Expected record length */
	private final Integer recordLength;
	
	/** If a record is too short, fill with 'fillWith' to reach {@link #recordLength} */
	private final String fillWith;

	/**
	 * @param valueObject
	 * @param continueOnRecordError
	 * @param version
	 * @param fields
	 * @param recordLength
	 * @param fillWith
	 */
	FixedLengthParserConfiguration(String valueObject,
			boolean continueOnRecordError, String version, List<Field<?>> fields,
			Integer recordLength, String fillWith, List<String> skipRecordsIdents) {
		super(ParserType.FIXED_LENGTH, valueObject, continueOnRecordError, version, fields, skipRecordsIdents);
		this.recordLength = recordLength;
		this.fillWith = fillWith;
	}

	/**
	 * @return
	 */
	public Integer getRecordLength() {
		return recordLength;
	}

	/**
	 * @return
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
		.append(", recordLength=").append(recordLength)
		.append(", ").append(super.toString())
		.append("]").toString();
	}

}
