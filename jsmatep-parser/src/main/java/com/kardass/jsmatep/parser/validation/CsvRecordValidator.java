package com.kardass.jsmatep.parser.validation;

import java.util.List;

import com.kardass.jsmatep.parser.config.CsvParserConfiguration;
import com.kardass.jsmatep.parser.reader.CsvBufferedRecordSplitter;

public class CsvRecordValidator extends AbstractRecordValidator<CsvParserConfiguration> {
	
	private CsvBufferedRecordSplitter recordSplitter = new CsvBufferedRecordSplitter();

	CsvRecordValidator(CsvParserConfiguration importConfig) {
		super(importConfig);
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.validation.AbstractRecordValidator#validateRecordVsConfig(java.lang.String)
	 */
	public void validateRecordVsConfig(String record) {
		final List<String> values = recordSplitter.splittStringToRecord(record, getImportConfig().getDelimiter());
		validateRecordLength(record, values);
	}

	/**
	 * Validates the record length.
	 * 
	 * @param unsplittedRecord
	 * @param values
	 * @throws IllegalRecordLengthException
	 */
	private void validateRecordLength(String unsplittedRecord, List<String> values) 
			throws IllegalRecordLengthException {
		if (values == null || values.size() != importConfig.getFieldCount()) {
			final int valueSize = (values == null ? 0: values.size());
			throw new IllegalRecordLengthException(
					"Invalid record quantity, expected " + importConfig.getFieldCount() 
						+ ", got " + valueSize, unsplittedRecord); 
		}
	}

}
