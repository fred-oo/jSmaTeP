package com.kardass.jsmatep.codegenerator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for all test cases concerning the JAVA code generator.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	MethodNameGeneratorTest.class,
	ImportValueObjectGeneratorTest.class,
	ExportValueObjectGeneratorTest.class
	})
public class AllTestsCodeGenerator {

}
