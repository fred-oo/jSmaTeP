package com.kardass.jsmatep.parser.reader;

import com.kardass.jsmatep.parser.config.CsvParserConfiguration;
import com.kardass.jsmatep.parser.config.FixedLengthParserConfiguration;
import com.kardass.jsmatep.parser.config.ParserConfiguration;
import com.kardass.jsmatep.parser.config.ParserType;
import com.kardass.jsmatep.parser.config.VariableLengthParserConfiguration;

/**
 * Factory for instantiation of {@link RecordReader}s.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class RecordReaderFactory {

	/**
	 * Returns a {@link RecordReader} based on a given {@link ParserConfiguration}. 
	 * @param parserConfig
	 * @return
	 */
	public static final RecordReader getRecordReader(ParserConfiguration parserConfig) {
		if (parserConfig.getType() == ParserType.CSV) {
			final CsvParserConfiguration csvConfig = (CsvParserConfiguration) parserConfig;
			return new CsvRecordReader(csvConfig);
		} else if (parserConfig.getType() == ParserType.FIXED_LENGTH) {
			final FixedLengthParserConfiguration flConfig = (FixedLengthParserConfiguration) parserConfig;
			return new FixedLengthRecordReader(flConfig);
		} else if (parserConfig.getType() == ParserType.VARIABLE_LENGTH) {
			final VariableLengthParserConfiguration varConfig = (VariableLengthParserConfiguration) parserConfig;
			return new VariableLengthRecordReader(varConfig);
		} else {
			throw new IllegalArgumentException("Unknown record type: " + parserConfig.getType());
		}
	}
	
}
