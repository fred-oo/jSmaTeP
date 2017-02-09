package com.kardass.jsmatep.parser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import com.kardass.jsmatep.codegenerator.java.JavaGeneratorConstants;
import com.kardass.jsmatep.codegenerator.java.MethodNameGenerator;
import com.kardass.jsmatep.parser.adjust.RecordAdjuster;
import com.kardass.jsmatep.parser.adjust.RecordAdjusterFactory;
import com.kardass.jsmatep.parser.config.ParserConfiguration;
import com.kardass.jsmatep.parser.config.field.Field;
import com.kardass.jsmatep.parser.reader.RecordReader;
import com.kardass.jsmatep.parser.reader.RecordReaderFactory;
import com.kardass.jsmatep.parser.validation.RecordValidator;
import com.kardass.jsmatep.parser.validation.RecordValidatorFactory;

/**
 * Mapper to 'translate' a text data record into a value object.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 * @param <T>
 * 			The value object, this mapper operates on/with.
 */
public class RecordToValueObjectMapper<T> {
	
	/** Cache for all methods in a given value object */
	private final HashMap<String, Method> methodNameMappings;
	
	/**	The import configuration used by this parser */
	private final ParserConfiguration parserConfig;
	
	/**	The record reader used by this mapper depending on a given configuration */
	private final RecordReader recordReader;
	
	/**	The record validator used by this mapper depending on a given configuration */
	private final RecordValidator recordValidator;

	/**	The record validator used by this mapper depending on a given configuration */
	private final RecordAdjuster recordAdjuster;

	/**
	 * @param parserConfig  The configuration to use for the mapper
	 */
	public RecordToValueObjectMapper(ParserConfiguration parserConfig) {
		this.parserConfig = parserConfig;
		this.methodNameMappings = new HashMap<String, Method>();
		this.recordReader = RecordReaderFactory.getRecordReader(parserConfig);
		this.recordValidator = RecordValidatorFactory.getRecordValidator(parserConfig);
		this.recordAdjuster = RecordAdjusterFactory.getRecordAdjuster(parserConfig);
	}

	/**
	 * Maps a record to a value object using a given configuration.
	 * 
	 * @param adjustedRecord
	 * @param valueObject
	 * @param configuration
	 * @return
	 * @throws FieldMappingException 
	 */
	public T mapRecordToValueObject(final String record) throws FieldMappingException {
		final String adjustedRecord = getRecordAdjuster().adjustRecordVsConfig(record);
		getRecordValidator().validateRecordVsConfig(adjustedRecord);
		try {
			String aValueString;
			T valueObject = instantiateNewValueObject();
			final List<Field<?>> fields = parserConfig.getFields();
			for (Field<?> aField : fields) {
				aValueString = getRecordReader().readField(aField, adjustedRecord);
				valueObject = setValue(aValueString, valueObject, aField);
			}
			return valueObject;
		} catch (FieldMappingException e) {
			throw e;
		} catch (Exception e) {
			throw new JsmatepInconsistencyException(adjustedRecord, e.getMessage());
		}
	}

	/**
	 * Set the value into the field using a given config.
	 * 
	 * @param valueString
	 * @param valueObject
	 * @param field
	 * @return
	 * @throws FieldMappingException 
	 */
	private T setValue(String valueString, T valueObject, Field<?> field) throws FieldMappingException {
		try {
			final String fieldName = field.getValueObjectPropertyName();
			Method setMethod = null;
			Object settableObject = null;
			
            // standard (direkt) settable property
			if (fieldName.indexOf('.') == -1) {
				String setterMethodName = MethodNameGenerator.generateSetterName(fieldName);
				setMethod = getMethod(valueObject.getClass(), setterMethodName, field.getTypeClass());
				settableObject = valueObject;
				
			// sub-property (indirekt) settable property 
			} else {
				final int lastIndex = fieldName.lastIndexOf('.');
				final String getterPart = fieldName.substring(0, lastIndex); // getter of the sub-property
				final String setterPart = fieldName.substring(lastIndex+1); // sub-property's setter for the value
				final String getterMethodName = MethodNameGenerator.generateGetterName(getterPart);
				final Method getMethod = valueObject.getClass().getMethod(getterMethodName, (Class[]) null);
				// get the object that will hold the value
				settableObject = getMethod.invoke(valueObject, (Object[]) null);
				// if the value holder object is null, try to instantiate the object and set in the value object
				if (settableObject == null) {
					final Constructor<?> constr = getMethod.getReturnType().getConstructor((Class[]) null);
					// find the default constructor
					settableObject = constr.newInstance((Object[]) null);
					final String setterNameSubProperty = MethodNameGenerator.generateSetterName(getterPart);
					// get the value object's setter to set the new instantiated sub-property
					final Method setterSubProperty = valueObject.getClass().getMethod(setterNameSubProperty, settableObject.getClass());
					// set the sub property in the value object
					setterSubProperty.invoke(valueObject, settableObject);
				}
				// remember the set method to set the value
				setMethod = getMethod(settableObject.getClass(), MethodNameGenerator.generateSetterName(setterPart), field.getTypeClass());
			}
			if (setMethod == null) {
				throw new IllegalStateException("No method setter method for field [" + field 
						+ "] found in class [" + valueObject.getClass().getName() + "]");
			}
			final Object value = field.getAsTypedValue(valueString);
			if (value != null) {
				setMethod.invoke(settableObject, value);
			}
		} catch (IllegalStateException e) {
			throw e;
		} catch (Exception e) {
			throw new FieldMappingException(valueString, e.getMessage());
		}
		return valueObject;
	}

	
	/**
	 * Return the method by it's name of the class.
	 *  
	 * @param methodName
	 * @param theClass
	 * @return
	 */
	private Method getMethod(Class<?> theClass, String methodName, Class<?> attributeType) {
		final String key = generateMethodKey(theClass, methodName, attributeType);
		if (!methodNameMappings.containsKey(key)) {
			addMethodNameMapping(theClass);
		}
		return methodNameMappings.get(key);
	}
	
	/**
	 * Creates the cache for all setter in the class.
	 * 
	 * @param valueObjectClass
	 */
	private void addMethodNameMapping(Class<?> valueObjectClass) {
		Method[] methods = valueObjectClass.getMethods();
		String methodName;
		Class<?> attributeType;
		for (Method aMethod : methods) {
			methodName = aMethod.getName();
			if (methodName.startsWith(JavaGeneratorConstants.SET)) {
				attributeType = aMethod.getParameterTypes()[0];
				methodNameMappings.put(
						generateMethodKey(valueObjectClass, methodName, attributeType), aMethod);
			}
		}
//		verifyAllFieldsHaveMatchingSetter(valueObjectClass);
	}
	
//	/**
//	 * Verifies that each configured field has got a suitable setter method in
//	 * the given value object class.
//	 * @param valueObjectClass
//	 */
//	private void verifyAllFieldsHaveMatchingSetter(Class<?> valueObjectClass) {
//		Method method;
//		String setterMethodName;
//		for (Field<?> aField : parserConfig.getFields()) {
//			setterMethodName = MethodNameGenerator.generateSetterName(aField.getValueObjectPropertyName());
//			method = methodNameMappings.get(generateMethodKey(valueObjectClass, setterMethodName, aField.getTypeClass()));
//			if (method == null) {
//				throw new IllegalStateException("No method [" + setterMethodName 
//						+ "(" + aField.getTypeClass().getName() + ")] found in class [" 
//						+ valueObjectClass.getName() + "]");
//			}
//		}
//	}

	/**
	 * Creates a new instance of the value object specified in the XML data configuration.
	 * 
	 * @param importConfig
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private T instantiateNewValueObject() {
		try {
			final String className = parserConfig.getValueObject();
			final Class<T> valueObjectClass = (Class<T>) getClass().getClassLoader().loadClass(className);
			final Constructor<T> constructor = (Constructor<T>) valueObjectClass.getConstructor((Class[]) null);
			return constructor.newInstance((Object[]) null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String generateMethodKey(Class<?> theClass, String methodName, Class<?> attributeType) {
		String key;
		if (attributeType.getName().startsWith("java.lang.")) {
			final String packageLess = attributeType.getName().substring(10);
			key = theClass.getName() + '#' + methodName + '#' + packageLess.toLowerCase();
		} else {
			key = theClass.getName() + '#' + methodName + '#' + attributeType.getName();
		}
		return key;
	}	

	public RecordReader getRecordReader() {
		return recordReader;
	}

	public RecordValidator getRecordValidator() {
		return recordValidator;
	}

	public RecordAdjuster getRecordAdjuster() {
		return recordAdjuster;
	}

}
