package com.kardass.jsmatep.codegenerator.java;

import static com.kardass.jsmatep.codegenerator.java.JavaGeneratorConstants.*;

/**
 * Helper class for generating method and property names.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public final class MethodNameGenerator {

    /** Singleton "constructor". */
    private MethodNameGenerator() {
    }

    /**
     * Generates the getter name.
     * 
     * @param fieldName
     * @return
     */
    public static String generateGetterName(final String fieldName) {
        return GET + upperCharAt(generateJavePropertyName(fieldName), 0);
    }

    /**
     * Generates the setter name.
     * 
     * @param fieldName
     * @return
     */
    public static String generateSetterName(final String fieldName) {
        return SET + upperCharAt(generateJavePropertyName(fieldName), 0);
    }

	/**
	 * Generates the property name.
	 * 
	 * @param fieldName
	 * @return
	 */
	public static StringBuilder generateJavePropertyName(final String fieldName) {
		StringBuilder result = new StringBuilder(fieldName);
		for (String toBeSubstituted : SUBSTITUTABLE_CHARACTERS) {
			result = javaSpecificSubstitution(result, toBeSubstituted);
		}
		return result;
	}
	
	/**
	 * Generates the property name.
	 * 
	 * @param fieldName
	 * @return
	 */
	public static String generateJavePropertyNameAsString(final String fieldName) {
		return generateJavePropertyName(fieldName).toString();
	}

	/**
	 * Substitutes in the field name the 'toBeSubstituded' string and returns a java like 
	 * 'camel case' result.
	 * 
	 * @param fieldName
	 * @param toBeSubstituted
	 * @return
	 */
	private static StringBuilder javaSpecificSubstitution(StringBuilder fieldName, final String toBeSubstituted)	{
		int idx = fieldName.indexOf(toBeSubstituted, 0);
		while(idx > -1) {
			fieldName.deleteCharAt(idx);
			fieldName.replace(idx, idx, EMPTY);
			fieldName = upperCharAt(fieldName, idx);
			idx = fieldName.indexOf(toBeSubstituted, idx);
		}
		return fieldName;
	}
	
	/**
	 * 'Uppers' a character at the given position.
	 * 
	 * @param theString
	 * @param position
	 * @return
	 */
	private static StringBuilder upperCharAt(final StringBuilder theString, final int position) {
		theString.setCharAt(position, Character.toUpperCase(theString.charAt(position)));
		return theString;
	}

}
