package com.kardass.jsmatep.parser.config.field;

import com.kardass.jsmatep.common.StringUtil;

/**
 * Basic field configuration.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 * @param <T>
 * 			Which JAVA type is bound to this field.
 */
public abstract class Field<T> {

	/** Field's name */
	protected final String name;
	
	/** Value object's property name to use while setting the parsed value */
	protected final String beanPropertyName;
	
	/** {@link #beanPropertyName} exists */
	protected final boolean beanPropertyNameExists;
	
	/** Field's position inside a string data record */
	protected final Integer position; 
	
	/** Field's length inside a string data record */
	protected final Integer length;
	
	/** Field's configured type in xml configuration - NOT java type! */
	protected final String type;
	
	/** Field's format for parsind a value */
	protected final String format;
	
	/** {@link #format} exists */
	protected final boolean formatExists;

	/** 
	 * Field's null value recognition reference value, means if a string value 
	 * equals this {@link #nullValue}, this field's value will be set to null
	 */
	protected final String nullValue; 

	/** {@link #nullValue} exists */
	protected final boolean nullValueExists; 

	/** 
	 * Field's default value if this field is not in parsed file or the given 
	 * value equals {@link #nullValue}, means: this field's value will be set 
	 * to the default value.
	 */
	protected final String defaultValue; 

	/** {@link #defaultValue} exists */
	protected final boolean defaultValueExists; 

	/**
	 * @param name
	 * @param beanPropertyName
	 * @param position
	 * @param length
	 * @param type
	 * @param format
	 * @param nullValue
	 */
	public Field(String name, String beanPropertyName, Integer position, 
			Integer length, String type, String format, 
			String nullValue, String defaultValue) {
		super();
		this.name = name;
		this.beanPropertyName = beanPropertyName;
		this.beanPropertyNameExists = StringUtil.isNotEmptyWithTrim(beanPropertyName);
		this.position = position;
		this.length = length;
		this.type = type;
		this.format = format;
		this.formatExists = StringUtil.isNotEmpty(format);
		this.nullValue = nullValue;
		this.nullValueExists = StringUtil.isNotEmpty(nullValue);
		this.defaultValue = defaultValue;
		this.defaultValueExists = StringUtil.isNotEmpty(defaultValue);
	}
	
	/**
	 * @param stringValue
	 * @return
	 * @throws Exception
	 */
	abstract T toTypedValue(String stringValue) throws Exception;

	/**
	 * Returns the string value as typed value {@link T}
	 * @param stringValue
	 * @return
	 */
	public T getAsTypedValue(String stringValue) {
		try {
			if (isStringNullOrNullValueOrZeroLength(stringValue)) {
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

	/**
	 * @return
	 * 		This field type as java class
	 */
	public abstract Class<T> getTypeClass();

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getBeanPropertyName() {
		return beanPropertyName;
	}

	/**
	 * @return
	 */
	public boolean beanPropertyNameExists() {
		return beanPropertyNameExists;
	}

	/**
	 * Returns the value object's property name to use while setting this field's value.
	 * @return
	 */
	public final String getValueObjectPropertyName() {
		return beanPropertyNameExists() ? getBeanPropertyName() : getName();

	}

	/**
	 * @return
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @return
	 */
	public Integer getLength() {
		return length;
	}

	/**
	 * @return
	 */
	public String getNullValue() {
		return nullValue;
	}

	/**
	 * @return
	 */
	public boolean nullValueExists() {
		return nullValueExists;
	}
	
	/**
	 * @return
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @return
	 */
	public boolean defaultValueExists() {
		return defaultValueExists;
	}	

	/**
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @return
	 */
	public boolean formatExists() {
		return formatExists;
	}

	/**
	 * Checks if a string is null or equal to a given null-value.
	 * 
	 * @param str
	 * @return
	 */
	protected boolean isStringNullOrNullValue(String str) {
		boolean isEmpty = (str == null);
		if (nullValueExists) {
			isEmpty |= nullValue.equals(str);
		}
		return isEmpty;
	}

	/**
	 * Checks if a string is null or equal to a given null-value or it's length is 0.
	 * 
	 * @param str
	 * @return
	 */
	protected boolean isStringNullOrNullValueOrZeroLength(String str) {
		return isStringNullOrNullValue(str) || str.trim().length() == 0;
	}

	/**
	 * Ensures that the format is specified in the configuration.
	 * 
	 * @param fieldConfig
	 */
	protected void assertFormatExists() {
		if (!formatExists()) {
			throw new IllegalArgumentException("Missing format for field " + getName());
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass()).append('[');
		builder.append("name=").append(name);
		builder.append(", beanPropertyName=").append(beanPropertyName);
		builder.append(", beanPropertyNameExists=").append(beanPropertyNameExists);
		builder.append(", type=").append(type);
		builder.append(", position=").append(position);
		builder.append(", length=").append(length);
		builder.append(", format=").append(format);
		builder.append(", formatExists=").append(formatExists);
		builder.append(", nullValue=").append(nullValue);
		builder.append(", nullValueExists=").append(nullValueExists);
		builder.append("]");
		return builder.toString();
	}

}
