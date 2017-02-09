package com.kardass.jsmatep.parser.config.field;


/**
 * String field configuration.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class StringField extends Field<String> {

	/**
	 * @param name
	 * @param beanPropertyName
	 * @param position
	 * @param length
	 * @param type
	 * @param format
	 * @param nullValue
	 */
	public StringField(String name, String beanPropertyName, Integer position,
			Integer length, String type, String format, String nullValue, String defaultValue) {
		super(name, beanPropertyName, position, length, type, format, nullValue, defaultValue);
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#toTypedValue(java.lang.String)
	 */
	protected String toTypedValue(String stringValue) {
		return stringValue;
	}
	
	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#getAsTypedValue(java.lang.String)
	 */
	public String getAsTypedValue(String stringValue) {
		try {
			if (isStringNullOrNullValue(stringValue)) {
				if (defaultValueExists) {
					return toTypedValue(defaultValue);
				} else {
					return null;
				}
			} else {
				return toTypedValue(stringValue);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot parse [" + stringValue 
					+ "] to " + getTypeClass() + " with format [" + getFormat() 
					+ "]", e.getCause());
		}
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#getTypeClass()
	 */
	public Class<String> getTypeClass() {
		return String.class;
	}

}
