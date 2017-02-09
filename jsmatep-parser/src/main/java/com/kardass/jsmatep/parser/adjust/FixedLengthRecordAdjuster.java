package com.kardass.jsmatep.parser.adjust;

import com.kardass.jsmatep.parser.config.FixedLengthParserConfiguration;

/**
 * Aduster implementation für fixed length data records.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class FixedLengthRecordAdjuster extends AbstractRecordAdjuster<FixedLengthParserConfiguration> {

	/**
	 * @param importConfig
	 */
	FixedLengthRecordAdjuster(FixedLengthParserConfiguration importConfig) {
		super(importConfig);
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.adjust.RecordAdjuster#adjustRecordVsConfig(java.lang.String)
	 */
	public String adjustRecordVsConfig(String record) {
		// check whether adjustment required
		final String fillWith = getImportConfig().getFillWith();
		if (fillWith != null && fillWith.length() > 0) {
			final int requiredLength = getImportConfig().getRecordLength();
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
