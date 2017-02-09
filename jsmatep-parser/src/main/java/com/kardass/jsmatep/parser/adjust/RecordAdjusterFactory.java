package com.kardass.jsmatep.parser.adjust;

import com.kardass.jsmatep.parser.config.CsvParserConfiguration;
import com.kardass.jsmatep.parser.config.FixedLengthParserConfiguration;
import com.kardass.jsmatep.parser.config.ParserConfiguration;
import com.kardass.jsmatep.parser.config.ParserType;
import com.kardass.jsmatep.parser.config.VariableLengthParserConfiguration;
import com.kardass.jsmatep.parser.validation.RecordValidator;


/**
 * Factory for instantiation of {@link RecordAdjuster}s.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class RecordAdjusterFactory {

	/**
	 * Returns a {@link RecordValidator} based on a given {@link ParserConfiguration}. 
	 * @param parserConfig
	 * @return
	 */
	public static final RecordAdjuster getRecordAdjuster(ParserConfiguration parserConfig) {
		if (parserConfig.getType() == ParserType.CSV) {
			final CsvParserConfiguration csvConfig = (CsvParserConfiguration) parserConfig;
			return new CsvRecordAdjuster(csvConfig);
		} else if (parserConfig.getType() == ParserType.FIXED_LENGTH) {
			final FixedLengthParserConfiguration flConfig = (FixedLengthParserConfiguration) parserConfig;
			return new FixedLengthRecordAdjuster(flConfig);
		} else if (parserConfig.getType() == ParserType.VARIABLE_LENGTH) {
			final VariableLengthParserConfiguration varConfig = (VariableLengthParserConfiguration) parserConfig;
			return new VariableLengthRecordAdjuster(varConfig);
		} else {
			throw new IllegalArgumentException("Unknown record type: " + parserConfig.getType());
		}
	}

}
