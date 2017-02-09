package com.kardass.jsmatep.parser.validation;

/**
 * Supports record validation vs. it's configuration.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public interface RecordValidator {

	/**
	 * Validates a record vs. it's configuration.
	 * 
	 * @param record
	 */
	void validateRecordVsConfig(String record);

}