package com.kardass.jsmatep.parser.reader;

import com.kardass.jsmatep.parser.config.FixedLengthParserConfiguration;
import com.kardass.jsmatep.parser.config.field.Field;

/**
 * Reader that supports reading fixed length record.
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class FixedLengthRecordReader extends AbstractRecordReader<FixedLengthParserConfiguration> {

	FixedLengthRecordReader(FixedLengthParserConfiguration importConfig) {
		super(importConfig);
	}

	public String readField(Field<?> fieldConfig, String theRecord) {
		return theRecord.substring(fieldConfig.getPosition(), fieldConfig.getPosition() + fieldConfig.getLength());
	}

}
