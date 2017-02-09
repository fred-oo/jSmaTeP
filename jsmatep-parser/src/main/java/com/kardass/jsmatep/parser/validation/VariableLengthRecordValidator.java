package com.kardass.jsmatep.parser.validation;

import com.kardass.jsmatep.parser.config.VariableLengthParserConfiguration;

public class VariableLengthRecordValidator extends AbstractRecordValidator<VariableLengthParserConfiguration> {
	
	private final boolean doValidateMinLength;
	private final boolean doValidateMaxLength;

	VariableLengthRecordValidator(VariableLengthParserConfiguration importConfig) {
		super(importConfig);
		doValidateMinLength = importConfig.getMinRecordLength() != null;
		doValidateMaxLength = importConfig.getMaxRecordLength() != null;
	}

	public void validateRecordVsConfig(String record) {
		validateMinRecordLength(record);
		validateMaxRecordLength(record);
	}

	/**
	 * Validates the max. record length.
	 * 
	 * @param record
	 * @param configuration
	 * @throws IllegalRecordLengthException
	 */
	private void validateMaxRecordLength(String record) throws IllegalRecordLengthException {
		if (doValidateMaxLength) {
			if (record != null && record.length() > getImportConfig().getMaxRecordLength()) {
				throw new IllegalRecordLengthException(
						"Max. record length violation, expected max. record length " 
							+ getImportConfig().getMaxRecordLength() + ", got " + record.length(), record); 
			}
		}
	}

	/**
	 * Validates the min. record length.
	 * 
	 * @param record
	 * @param configuration
	 * @throws IllegalRecordLengthException
	 */
	private void validateMinRecordLength(String record) throws IllegalRecordLengthException {
		if (doValidateMinLength) {
			if (record == null || record.length() < getImportConfig().getMinRecordLength()) {
				final int recordLength = (record == null ? 0: record.length());
				throw new IllegalRecordLengthException(
						"Min. record length violation, expected min. record length " 
							+ getImportConfig().getMinRecordLength() + ", got " + recordLength, record); 
			}
		}
	}

}
