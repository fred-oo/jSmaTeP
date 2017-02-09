package com.kardass.jsmatep.parser.validation;

import com.kardass.jsmatep.parser.config.CsvParserConfiguration;
import com.kardass.jsmatep.parser.config.FixedLengthParserConfiguration;
import com.kardass.jsmatep.parser.config.ParserConfiguration;
import com.kardass.jsmatep.parser.config.ParserType;
import com.kardass.jsmatep.parser.config.VariableLengthParserConfiguration;

/**
 * Factory for instantiation of {@link RecordValidator}s.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class RecordValidatorFactory {

	/**
	 * Returns a {@link RecordValidator} based on a given {@link ParserConfiguration}. 
	 * @param parserConfig
	 * @return
	 */
	public static final RecordValidator getRecordValidator(ParserConfiguration parserConfig) {
		if (parserConfig.getType() == ParserType.CSV) {
			final CsvParserConfiguration csvConfig = (CsvParserConfiguration) parserConfig;
			return new CsvRecordValidator(csvConfig);
		} else if (parserConfig.getType() == ParserType.FIXED_LENGTH) {
			final FixedLengthParserConfiguration flConfig = (FixedLengthParserConfiguration) parserConfig;
			return new FixedLengthRecordValidator(flConfig);
		} else if (parserConfig.getType() == ParserType.VARIABLE_LENGTH) {
			final VariableLengthParserConfiguration varConfig = (VariableLengthParserConfiguration) parserConfig;
			return new VariableLengthRecordValidator(varConfig);
		} else {
			throw new IllegalArgumentException("Unknown record type: " + parserConfig.getType());
		}
	}

}
