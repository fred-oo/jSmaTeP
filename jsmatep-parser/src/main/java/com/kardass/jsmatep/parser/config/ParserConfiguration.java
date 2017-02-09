package com.kardass.jsmatep.parser.config;

import java.util.List;

import com.kardass.jsmatep.parser.config.field.Field;

/**
 * Base class for parser configuration(s).
 *  
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public abstract class ParserConfiguration {
	
	/** Configuration's parser type */
	protected final ParserType type;
	
	/** The value object which belongs to this configuration */
	protected final String valueObject;
	
	/** Continue record processing, if an error occurs */
	protected final boolean continueOnRecordError;
	
	/** Skip records starting with on one of these identifiers */
	protected final List<String> skipRecordIdentifiers;
	
	protected final boolean skipRecordIdentifiersExist;

	/** Configuration's version */
	protected final String version;

	/** Configuration's fields */
	protected final List<Field<?>> fields;

	/**
	 * @param type
	 * @param valueObject
	 * @param continueOnRecordError
	 * @param version
	 * @param fields
	 */
	ParserConfiguration(ParserType type, String valueObject,
			boolean continueOnRecordError, String version, List<Field<?>> fields,
			List<String> skipRecordIdentifiers) {
		super();
		this.type = type;
		this.valueObject = valueObject;
		this.continueOnRecordError = continueOnRecordError;
		this.version = version;
		this.fields = fields;
		this.skipRecordIdentifiers = skipRecordIdentifiers;
		this.skipRecordIdentifiersExist = skipRecordIdentifiers != null && skipRecordIdentifiers.size() > 0;
	}

	/**
	 * @return
	 */
	public String getValueObject() {
		return valueObject;
	}

	/**
	 * @return
	 */
	public boolean continueOnRecordError() {
		return continueOnRecordError;
	}

	/**
	 * @return
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return
	 */
	public List<Field<?>> getFields() {
		return fields;
	}

	/**
	 * @return
	 */
	public ParserType getType() {
		return type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass()).append('[');
		builder.append("valueObject=").append(valueObject);
		builder.append(", continueOnRecordError=").append(continueOnRecordError);
		builder.append(", type=").append(type);
		builder.append(", version=").append(version);
		builder.append(", fields=").append(fields);
		builder.append("]");
		return builder.toString();
	}

	public List<String> getSkipRecordIdentifiers() {
		return skipRecordIdentifiers;
	}

	public boolean skipRecordIdentifiersExist() {
		return skipRecordIdentifiersExist;
	}
	
}
