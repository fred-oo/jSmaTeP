package com.kardass.jsmatep.parser.validation;

import java.util.List;

import com.kardass.jsmatep.parser.config.ParserConfiguration;
import com.kardass.jsmatep.parser.config.field.Field;

public class ConfigValidator {

	public static final void validateConfig(ParserConfiguration parserConfig) {
		final List<Field<?>> fields = parserConfig.getFields();
		String fieldName;
		for (Field<?> aField : fields) {
			fieldName = aField.getValueObjectPropertyName();
			
			// check wether max. just on sub property is used
			if (fieldName.indexOf('.', 0) != fieldName.lastIndexOf('.')) {
				throw new IllegalArgumentException(
						"Max just one sub-property supported, e.g. valueObject.primaryKey!\n"
						+ "Current property name [" + fieldName + "] for config: " + aField
						+ "\n Complete XML configuration:\n" + parserConfig);
			}
		}
	}

}
