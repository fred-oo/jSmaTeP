package com.kardass.jsmatep.parser.setterlogic;

import static com.kardass.jsmatep.common.UrlUtil.packageToRealPath;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.kardass.jsmatep.parser.BufferedRecordMapperListener;
import com.kardass.jsmatep.parser.ImportProcessor;
import com.kardass.jsmatep.parser.JsmatepInconsistencyException;

public class SetterLogicTest {

	private static final List<String> importStrings;
	
	static {
		importStrings = new ArrayList<String>();
		importStrings.add("AB2008112399"); // 
		importStrings.add("CD00000000"); //missing field
		importStrings.add("EF        12"); //missing field
		importStrings.add(""); //empty record => skipp
	}

    private final String xmlConfigFile = packageToRealPath(getClass()) + "/Setter-Logic-Test-Config.xml";

	@Test
	public void testContinueOnError() {
		final BufferedRecordMapperListener<SetterLogicTestValueObject> listener =
			new BufferedRecordMapperListener<SetterLogicTestValueObject>();
		final ImportProcessor<SetterLogicTestValueObject> ip = 
			new ImportProcessor<SetterLogicTestValueObject>(xmlConfigFile, listener);
		try {
			ip.importContent(importStrings);
			fail("Never come here, as the method setb(java.util.Date) is missing in value object");
		} catch (JsmatepInconsistencyException e) {
			// everything is ok, just testing an error
		} catch (Exception e) {
			fail("Never come here");
		}
	}

}
