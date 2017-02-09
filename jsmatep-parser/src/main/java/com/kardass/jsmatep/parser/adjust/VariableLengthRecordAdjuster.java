package com.kardass.jsmatep.parser.adjust;

import com.kardass.jsmatep.parser.config.VariableLengthParserConfiguration;

/**
 * Class for record adjustment(s) for variable length data records.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class VariableLengthRecordAdjuster extends AbstractRecordAdjuster<VariableLengthParserConfiguration> {

	/**
	 * @param parserConfiguration
	 */
	VariableLengthRecordAdjuster(VariableLengthParserConfiguration parserConfiguration) {
		super(parserConfiguration);
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.adjust.RecordAdjuster#adjustRecordVsConfig(java.lang.String)
	 */
	public String adjustRecordVsConfig(String record) {
		// check whether adjustment required
		final String fillWith = getImportConfig().getFillWith();
		if (fillWith != null && fillWith.length() > 0) {
			final int requiredLength = getImportConfig().getMaxRecordLength();
			if (record.length() < requiredLength) {
				final StringBuilder builder = new StringBuilder(record);
				while(builder.length() < requiredLength) {
					builder.append(fillWith);
				}
				record = builder.toString();
			}
		}
		return record;
	}

}
