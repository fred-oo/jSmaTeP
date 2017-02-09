package com.kardass.jsmatep.parser.reader;

import com.kardass.jsmatep.parser.config.CsvParserConfiguration;
import com.kardass.jsmatep.parser.config.field.Field;

/**
 * Reader that supports reading comma separated record.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class CsvRecordReader extends AbstractRecordReader<CsvParserConfiguration> {
	
	private CsvBufferedRecordSplitter recordSplitter = new CsvBufferedRecordSplitter();
	
	CsvRecordReader(CsvParserConfiguration importConfig) {
		super(importConfig);
	}

	public String readField(Field<?> fieldConfig, String record) {
		return recordSplitter.readField(record, getConfiguration().getDelimiter(), fieldConfig.getPosition());
	}

}
