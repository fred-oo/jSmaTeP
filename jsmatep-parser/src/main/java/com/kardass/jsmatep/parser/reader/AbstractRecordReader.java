package com.kardass.jsmatep.parser.reader;

import com.kardass.jsmatep.parser.config.ParserConfiguration;

/**
 * @author Manfred Kardass (manfred@kardass.de)
 *
 * @param <T>
 * 			The related {@link ParserConfiguration}
 */
public abstract class AbstractRecordReader<T extends ParserConfiguration> implements RecordReader {

	protected final T configuration;

	AbstractRecordReader(T importConfig) {
		super();
		this.configuration = importConfig;
	}

	public T getConfiguration() {
		return configuration;
	}
	
}
