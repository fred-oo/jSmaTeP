package com.kardass.jsmatep.codegenerator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.kardass.jsmatep.codegenerator.java.MethodNameGenerator;

/**
 * Generator's test case for setter and getter name creation. 
 *
 * @author Manfred Kardass (manfred@kardass.de)
 */
public class MethodNameGeneratorTest {

	@Test
	public void testMethodNameGenerator() throws Exception {
		final String fieldName = "some-Very strange.property_name";
		final String expectedPropertyName = "someVeryStrangePropertyName";
		final String expectedSetterName = "setSomeVeryStrangePropertyName";
		final String expectedGetterName = "getSomeVeryStrangePropertyName";
		assertEquals("Generated wrong property name", expectedPropertyName, MethodNameGenerator.generateJavePropertyNameAsString(fieldName));
		assertEquals("Generated wrong getter name", expectedGetterName, MethodNameGenerator.generateGetterName(fieldName));
		assertEquals("Generated wrong setter name", expectedSetterName, MethodNameGenerator.generateSetterName(fieldName));
	}
	
	@Test
	public void testMethodNameGeneratorAllUpperChars() throws Exception {
		final String fieldName = "SOME-VERY STRANGE.PROPERTY_NAME";
		final String expectedPropertyName = "SOMEVERYSTRANGEPROPERTYNAME";
		final String expectedSetterName = "setSOMEVERYSTRANGEPROPERTYNAME";
		final String expectedGetterName = "getSOMEVERYSTRANGEPROPERTYNAME";
		assertEquals("Generated wrong property name", expectedPropertyName, MethodNameGenerator.generateJavePropertyNameAsString(fieldName));
		assertEquals("Generated wrong getter name", expectedGetterName, MethodNameGenerator.generateGetterName(fieldName));
		assertEquals("Generated wrong setter name", expectedSetterName, MethodNameGenerator.generateSetterName(fieldName));
	}

}
