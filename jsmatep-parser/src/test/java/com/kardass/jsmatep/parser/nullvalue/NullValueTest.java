package com.kardass.jsmatep.parser.nullvalue;

import static com.kardass.jsmatep.common.UrlUtil.packageToRealPath;
import static com.kardass.jsmatep.parser.DateUtil.getDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import com.kardass.jsmatep.parser.BufferedRecordMapperListener;
import com.kardass.jsmatep.parser.ImportProcessor;
import com.kardass.jsmatep.parser.ImportResult;

public class NullValueTest {

	private static final List<String> importStrings;
	
	static {
		importStrings = new ArrayList<String>();
		importStrings.add("AB2008112399"); // 
		importStrings.add("CD00000000"); //missing field
		importStrings.add("EF00000000-1"); //missing field
		importStrings.add(""); //empty record => skipp
	}

	@Test
	public void testContinueOnError() {
        final String xmlConfigFile = packageToRealPath(getClass()) + "/Null-Value-Test-Config.xml";
		final BufferedRecordMapperListener<NullValueTestValueObject> listener =
			new BufferedRecordMapperListener<NullValueTestValueObject>();
		final ImportProcessor<NullValueTestValueObject> ip = 
			new ImportProcessor<NullValueTestValueObject>(xmlConfigFile, listener);
		try {
			final ImportResult importResult = ip.importContent(importStrings);
			assertEquals("Wrong quantity imported records", 3, listener.size());
            assertEquals("Wrong quantity skipped records", 1, importResult.getCountSkippedRecords());
            assertEquals("Wrong quantity error records", 0, importResult.getCountErrorRecords());
			assertEquals("Mismatch quantity imported records vs. mapped value objects", 
					importResult.getCountMappedValueObjects(), listener.size());

			NullValueTestValueObject vo = listener.getMappedValueObjects().get(0);
			assertEquals("Wrong mapping A", "AB", vo.getA());
			assertEquals("Wrong mapping B", getDate(2008, Calendar.NOVEMBER, 23), vo.getB());
			assertEquals("Wrong mapping C", 99, vo.getC().intValue());
			
			vo = listener.getMappedValueObjects().get(1);
			assertEquals("Wrong mapping A", "CD", vo.getA());
			assertNull("Wrong mapping B", vo.getB());
			assertNull("Wrong mapping C", vo.getC());
			
			vo = listener.getMappedValueObjects().get(2);
			assertEquals("Wrong mapping A", "EF", vo.getA());
			assertNull("Wrong mapping B", vo.getB());
			assertNull("Wrong mapping C", vo.getC());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Never come here - expected an import result with valid infomation");
		}
	}

}
