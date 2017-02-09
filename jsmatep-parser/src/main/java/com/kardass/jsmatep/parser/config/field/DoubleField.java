package com.kardass.jsmatep.parser.config.field;

/**
 * Double field configuration.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class DoubleField extends Field<Double> {

	/**
	 * @param name
	 * @param beanPropertyName
	 * @param position
	 * @param length
	 * @param type
	 * @param format
	 * @param nullValue
	 */
	public DoubleField(String name, String beanPropertyName, Integer position,
			Integer length, String type, String format, String nullValue, String defaultValue) {
		super(name, beanPropertyName, position, length, type, format, nullValue, defaultValue);
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#toTypedValue(java.lang.String)
	 */
	Double toTypedValue(String stringValue) {
		return Double.parseDouble(stringValue.trim());
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#getTypeClass()
	 */
	public Class<Double> getTypeClass() {
		return Double.class;
	}

}
