package com.kardass.jsmatep.parser.config;

import java.util.List;

import com.kardass.jsmatep.parser.config.field.Field;

/**
 * Parser configuration for comma separated data records.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class CsvParserConfiguration extends ParserConfiguration {

	/** Delimiter between two values */
	private final String delimiter;
	
	/** Field quantity */
	private final Integer fieldCount;
	
	/**
	 * @param valueObject
	 * @param continueOnRecordError
	 * @param version
	 * @param fields
	 * @param delimiter
	 * @param fieldCount
	 */
	CsvParserConfiguration(String valueObject,
			boolean continueOnRecordError, String version, List<Field<?>> fields,
			String delimiter, Integer fieldCount, List<String> skipRecordsIdents) {
		super(ParserType.CSV, valueObject, continueOnRecordError, version, fields, skipRecordsIdents);
		this.delimiter = delimiter;
		this.fieldCount = fieldCount;
	}

	/**
	 * @return
	 * 		The field delimiter.
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * @return
	 * 		Quantity of fields.
	 */
	public Integer getFieldCount() {
		return fieldCount;
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.ParserConfiguration#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder()
			.append(getClass()).append('[')
			.append("delimiter=").append(delimiter)
			.append(", fieldCount=").append(fieldCount)
			.append(", ").append(super.toString())
			.append("]").toString();
	}

}
