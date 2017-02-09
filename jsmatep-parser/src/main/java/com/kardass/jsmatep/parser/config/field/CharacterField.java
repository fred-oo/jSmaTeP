package com.kardass.jsmatep.parser.config.field;

/**
 * Character field configuration.
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class CharacterField extends Field<Character> {

	/**
	 * @param name
	 * @param beanPropertyName
	 * @param position
	 * @param length
	 * @param type
	 * @param format
	 * @param nullValue
	 */
	public CharacterField(String name, String beanPropertyName,
			Integer position, Integer length, String type, String format,
			String nullValue, String defaultValue) {
		super(name, beanPropertyName, position, length, type, format, nullValue, defaultValue);
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#toTypedValue(java.lang.String)
	 */
	Character toTypedValue(String stringValue) {
		return stringValue.charAt(0);
	}
	
	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#getAsTypedValue(java.lang.String)
	 */
	public Character getAsTypedValue(String stringValue) {
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
	public Class<Character> getTypeClass() {
		return Character.class;
	}

}
