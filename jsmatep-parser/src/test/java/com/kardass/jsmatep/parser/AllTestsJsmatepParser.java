package com.kardass.jsmatep.parser;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.kardass.jsmatep.parser.adjust.AdjustTest;
import com.kardass.jsmatep.parser.continueonerror.ContinueOnErrorTest;
import com.kardass.jsmatep.parser.defaultvalue.DefaultValueTest;
import com.kardass.jsmatep.parser.nullvalue.NullValueTest;
import com.kardass.jsmatep.parser.setterlogic.SetterLogicTest;
import com.kardass.jsmatep.parser.subproperty.SubpropertyWithDefaultConstructorTest;

/**
 * All jSmaTeP parser tests.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 */
@RunWith(Suite.class)
@SuiteClasses({
	FixedLengthFileParserTest.class,
	VariableLengthFileParserTest.class,
	CsvFileParserTest.class,
	CsvParserDelimiterTest.class,
	ContinueOnErrorTest.class,
	AdjustTest.class,
	SubpropertyWithDefaultConstructorTest.class,
	NullValueTest.class,
	DefaultValueTest.class,
	SetterLogicTest.class
	})
public class AllTestsJsmatepParser {

}
