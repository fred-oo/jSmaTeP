package com.kardass.jsmatep.parser.validation;

import com.kardass.jsmatep.parser.config.ParserConfiguration;

public abstract class AbstractRecordValidator<T extends ParserConfiguration> implements RecordValidator {

	protected final T importConfig;

	AbstractRecordValidator(T importConfig) {
		super();
		this.importConfig = importConfig;
	}

	protected T getImportConfig() {
		return importConfig;
	}

}
