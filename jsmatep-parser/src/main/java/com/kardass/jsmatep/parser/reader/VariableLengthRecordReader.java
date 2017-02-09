package com.kardass.jsmatep.parser.reader;

import com.kardass.jsmatep.parser.config.VariableLengthParserConfiguration;
import com.kardass.jsmatep.parser.config.field.Field;

/**
 * Reader that supports reading variable length records.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class VariableLengthRecordReader extends AbstractRecordReader<VariableLengthParserConfiguration> {

	VariableLengthRecordReader(VariableLengthParserConfiguration importConfig) {
		super(importConfig);
	}

	public String readField(Field<?> fieldConfig, String record) {
		String result = null;
		final int beginIdx = fieldConfig.getPosition();
		if (beginIdx < record.length()) {
			int endIdx = fieldConfig.getPosition() + fieldConfig.getLength();
			if (endIdx > record.length()) {
				endIdx = record.length();
			}
			result = record.substring(beginIdx, endIdx);
		}
		return result;
	}

}
