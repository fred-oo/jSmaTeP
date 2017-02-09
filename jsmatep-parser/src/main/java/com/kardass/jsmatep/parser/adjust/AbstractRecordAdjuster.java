package com.kardass.jsmatep.parser.adjust;

import com.kardass.jsmatep.parser.config.ParserConfiguration;

/**
 * Abstract class for record agjustment(s).
 * @author Manfred Kardass (manfred@kardass.de)
 *
 * @param <T>
 * 			The related parser configuration
 */
public abstract class AbstractRecordAdjuster<T extends ParserConfiguration> implements RecordAdjuster {

	protected final T importConfig;

	AbstractRecordAdjuster(T importConfig) {
		super();
		this.importConfig = importConfig;
	}

	protected T getImportConfig() {
		return importConfig;
	}

}
