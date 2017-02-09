package com.kardass.jsmatep.parser.config.field;

import java.math.BigDecimal;

/**
 * Decimal field configuration.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class DecimalField extends Field<BigDecimal> {

	/**
	 * @param name
	 * @param beanPropertyName
	 * @param position
	 * @param length
	 * @param type
	 * @param format
	 * @param nullValue
	 */
	public DecimalField(String name, String beanPropertyName, Integer position,
			Integer length, String type, String format, String nullValue, String defaultValue) {
		super(name, beanPropertyName, position, length, type, format, nullValue, defaultValue);
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#toTypedValue(java.lang.String)
	 */
	BigDecimal toTypedValue(String stringValue) {
		BigDecimal result = new BigDecimal(stringValue.trim());
		if (formatExists()) {
			int positions = getPositionAfterDecimalPoint(format);
			result = result.movePointLeft(positions);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#getTypeClass()
	 */
	public Class<BigDecimal> getTypeClass() {
		return BigDecimal.class;
	}

	/**
	 * Return e.g 3 if a format string for a BigDecimal is '4,3'.
	 * 
	 * @param format
	 * @return
	 */
	private static int getPositionAfterDecimalPoint(String format) {
		int result = 0;
		int lastIdx = format.lastIndexOf(',');
		if (lastIdx >= 0) {
			result = Integer.parseInt(format.substring(lastIdx + 1));
		}
		return result;
	}

}
