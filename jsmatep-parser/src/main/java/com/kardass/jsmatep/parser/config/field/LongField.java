package com.kardass.jsmatep.parser.config.field;

/**
 * Long field configuration.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class LongField extends Field<Long> {

	/**
	 * @param name
	 * @param beanPropertyName
	 * @param position
	 * @param length
	 * @param type
	 * @param format
	 * @param nullValue
	 */
	public LongField(String name, String beanPropertyName, Integer position,
			Integer length, String type, String format, String nullValue, String defaultValue) {
		super(name, beanPropertyName, position, length, type, format, nullValue, defaultValue);
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#toTypedValue(java.lang.String)
	 */
	Long toTypedValue(String stringValue) {
		return Long.parseLong(stringValue.trim());
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#getTypeClass()
	 */
	public Class<Long> getTypeClass() {
		return Long.class;
	}

}
