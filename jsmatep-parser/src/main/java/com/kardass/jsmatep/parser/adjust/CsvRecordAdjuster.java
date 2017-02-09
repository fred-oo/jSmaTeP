package com.kardass.jsmatep.parser.adjust;

import com.kardass.jsmatep.parser.config.CsvParserConfiguration;

/**
 * Aduster implementation für CSV data records.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class CsvRecordAdjuster extends AbstractRecordAdjuster<CsvParserConfiguration> {

	CsvRecordAdjuster(CsvParserConfiguration importConfig) {
		super(importConfig);
	}

	public String adjustRecordVsConfig(String record) {
		// nothing to do
		return record;
	}

}
