package com.kardass.jsmatep.parser.config.field;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date field configuration.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class DateField extends Field<Date> {

	/** The used date formatter */
	private final SimpleDateFormat dateFormat;

	/**
	 * @param name
	 * @param beanPropertyName
	 * @param position
	 * @param length
	 * @param type
	 * @param format
	 * @param nullValue
	 */
	public DateField(String name, String beanPropertyName, Integer position,
			Integer length, String type, String format, String nullValue, String defaultValue) {
		super(name, beanPropertyName, position, length, type, format, nullValue, defaultValue);
		assertFormatExists();
		dateFormat = new SimpleDateFormat(format);
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#toTypedValue(java.lang.String)
	 */
	Date toTypedValue(String stringValue) throws ParseException {
		return dateFormat.parse(stringValue);
	}

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.config.field.Field#getTypeClass()
	 */
	public Class<Date> getTypeClass() {
		return Date.class;
	}

}
