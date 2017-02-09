package com.kardass.jsmatep.parser.config.field;

/**
 * Float field configuration.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class FloatField extends Field<Float> {

	/**
	 * @param name
	 * @param beanPropertyName
	 * @param position
	 * @param length
	 * @param type
	 * @param format
	 * @param nullValue
	 */
	public FloatField(String name, String beanPropertyName, Integer position,
			Integer length, String type, String format, String nullValue, String defaultValue) {
		super(name, beanPropertyName, position, length, type, format, nullValue, defaultValue);
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#toTypedValue(java.lang.String)
	 */
	Float toTypedValue(String stringValue) {
		return Float.parseFloat(stringValue.trim());
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#getTypeClass()
	 */
	public Class<Float> getTypeClass() {
		return Float.class;
	}

}
