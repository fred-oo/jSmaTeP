package com.kardass.jsmatep.codegenerator.java;

import static com.kardass.jsmatep.codegenerator.java.JavaGeneratorConstants.*;

import java.util.List;

/**
 * Class for generating java value objects.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 * 
 */
public abstract class JavaGenerator {

	/**
	 * Generates a member, the setter and the getter for the given name and
	 * type.
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	public static String generatePropertyWithSetterAndGetter(final String name, final String type) {
		final String trimmedName = name.trim();
		return new StringBuilder().append(generateProperty(trimmedName, type))
		        .append(generateSetter(trimmedName, type)).append(generateGetter(trimmedName, type)).toString();
	}

	/**
	 * Generates a setter for the given name and type.
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	public static Object generateSetter(final String name, final String type) {
		final String propertyName = MethodNameGenerator.generateJavePropertyName(name).toString();
		return new StringBuilder().append(PUBLIC).append(VOID).append(MethodNameGenerator.generateSetterName(name))
		        .append(ROUND_BRACKET_OPEN).append(getFullQualifiedTypeAsString(type)).append(SPACE).append(
		                propertyName).append(ROUND_BRACKET_CLOSE).append(CURLY_BRACE_OPEN).append(THIS).append(
		                propertyName).append(EQUALS).append(propertyName).append(SEMICOLON).append(CURLY_BRACE_CLOSE)
		        .append(LINE_FEED).toString();
	}

	/**
	 * Generates a getter for the given name and type.
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	public static Object generateGetter(final String name, final String type) {
		return new StringBuilder().append(PUBLIC).append(getFullQualifiedTypeAsString(type)).append(SPACE).append(
		        MethodNameGenerator.generateGetterName(name)).append(ROUND_BRACKET_OPEN).append(ROUND_BRACKET_CLOSE)
		        .append(CURLY_BRACE_OPEN).append(RETURN).append(SPACE).append(THIS).append(
		                MethodNameGenerator.generateJavePropertyName(name)).append(SEMICOLON).append(CURLY_BRACE_CLOSE)
		        .append(LINE_FEED).toString();
	}

	/**
	 * Generates the attribute (member) for the name of the given type.
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	public static String generateProperty(final String name, final String type) {
		return new StringBuilder().append(PRIVATE).append(getFullQualifiedTypeAsString(type)).append(SPACE).append(
		        MethodNameGenerator.generateJavePropertyName(name)).append(SEMICOLON).append(LINE_FEED).toString();
	}

	/**
	 * Generates a toString method for the given property names.
	 * 
	 * @param propertyNames
	 * @return
	 */
	public static String generateToStringMethod(final List<String> propertyNames) {
		final StringBuilder result = new StringBuilder().append(PUBLIC).append("String toString() ").append(
		        CURLY_BRACE_OPEN).append(LINE_FEED).append(RETURN).append(SPACE).append("\"[\" + ");
		boolean isFirstAdded = false;
		for (String aPropertyName : propertyNames) {
			if (isFirstAdded) {
				result.append(LINE_FEED).append("+ \", \" + ");
			}
			result.append('"').append(aPropertyName).append('=').append('"').append('+').append(
			        MethodNameGenerator.generateGetterName(aPropertyName)).append(ROUND_BRACKET_OPEN_CLOSE);
			isFirstAdded = true;
		}
		result.append(" + \"]\"").append(SEMICOLON).append(LINE_FEED).append(CURLY_BRACE_CLOSE).append(LINE_FEED);
		return result.toString();
	}

	/**
	 * Generates the package for the given destination e.g. de.kardass.jsmatep.
	 * 
	 * @param generatorDestinationPackage
	 * @return
	 */
	public static String generatePackage(final String generatorDestinationPackage) {
		final int lastIndex = generatorDestinationPackage.lastIndexOf('.');
		if (lastIndex > 0) {
			return PACKAGE + " " + generatorDestinationPackage.substring(0, lastIndex) + SEMICOLON + LINE_FEED;
		} else {
			return "";
		}
	}

	/**
	 * Generates the class header.
	 * 
	 * @param className
	 * @return
	 */
	public static String generateClassHeader(final String className) {
		final StringBuilder builder = new StringBuilder();
		builder.append(PUBLIC).append(CLASS).append(
		        className.substring(className.lastIndexOf('.') + 1, className.length())).append(SPACE).append(
		        CURLY_BRACE_OPEN).append(LINE_FEED);
		return builder.toString();
	}

	/**
	 * Generates the trailing '}' for a class.
	 * 
	 * @return
	 */
	public static String generateClassFooter() {
		return Character.toString(CURLY_BRACE_CLOSE);
	}

	/**
	 * Returns the full qualified class name for a given type e.g. Date =>
	 * java.util.Date
	 * 
	 * @param type
	 * @return
	 */
	public static String getFullQualifiedTypeAsString(final String type) {
		String result = null;
		if (type.equals("Date")) {
			result = java.util.Date.class.getName();
		} else if (type.equals("Decimal") || type.equals(java.math.BigDecimal.class.getSimpleName())) {
			result = java.math.BigDecimal.class.getName();
		} else {
			result = type;
		}
		return result;
	}

}
