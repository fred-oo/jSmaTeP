package com.kardass.jsmatep.parser.validation;

import com.kardass.jsmatep.parser.config.FixedLengthParserConfiguration;

/**
 * Validator for records against their xml configuration.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class FixedLengthRecordValidator extends AbstractRecordValidator<FixedLengthParserConfiguration> {

	FixedLengthRecordValidator(FixedLengthParserConfiguration importConfig) {
		super(importConfig);
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.validation.AbstractRecordValidator#validateRecordVsConfig(java.lang.String)
	 */
	public void validateRecordVsConfig(String record) {
		validateRecordLength(record);
	}

	/**
	 * Validates the record length.
	 * 
	 * @param record
	 * @param configuration
	 * @throws IllegalRecordLengthException
	 */
	private void validateRecordLength(String record) 
			throws IllegalRecordLengthException {
		if (record == null || record.length() != importConfig.getRecordLength()) {
			final int recordLength = (record == null ? 0: record.length());
			throw new IllegalRecordLengthException(
					"Record length violation, expected record length " 
						+ importConfig.getRecordLength() 
						+ ", got " + recordLength, record); 
		}
	}
	
}
