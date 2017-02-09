package com.kardass.jsmatep.parser.config.field;

/**
 * Integer field configuration.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class IntegerField extends Field<Integer> {

	/**
	 * @param name
	 * @param beanPropertyName
	 * @param position
	 * @param length
	 * @param type
	 * @param format
	 * @param nullValue
	 */
	public IntegerField(String name, String beanPropertyName, Integer position,
			Integer length, String type, String format, String nullValue, String defaultValue) {
		super(name, beanPropertyName, position, length, type, format, nullValue, defaultValue);
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#toTypedValue(java.lang.String)
	 */
	Integer toTypedValue(String stringValue) {
		return Integer.parseInt(stringValue.trim());
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#getTypeClass()
	 */
	public Class<Integer> getTypeClass() {
		return Integer.class;
	}

}
