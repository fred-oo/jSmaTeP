package com.kardass.jsmatep.parser.adjust;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import com.kardass.jsmatep.common.UrlUtil;
import com.kardass.jsmatep.parser.BufferedRecordMapperListener;
import com.kardass.jsmatep.parser.DateUtil;
import com.kardass.jsmatep.parser.ImportProcessor;
import com.kardass.jsmatep.parser.ImportResult;

public class AdjustTest {

	private static final List<String> importStrings;
	
	static {
		importStrings = new ArrayList<String>();
		importStrings.add("ABZY2008112399  "); // 
		importStrings.add("CD  19740723"); //missing field
		importStrings.add("EF"); //missing field
		importStrings.add(""); //empty record => skipp
	}

	@Test
	public void testContinueOnError() {
        final String xmlConfigFile = UrlUtil.packageToRealPath(getClass()) + "/Adjust-Config.xml";
		final BufferedRecordMapperListener<AdjustTestValueObject> listener = 
			new BufferedRecordMapperListener<AdjustTestValueObject>();
		final ImportProcessor<AdjustTestValueObject> ip = new ImportProcessor<AdjustTestValueObject>(xmlConfigFile, listener);
		try {
			final ImportResult importResult = ip.importContent(importStrings);
			assertEquals("Wrong quantity imported records", 3, listener.size());
            assertEquals("Wrong quantity skipped records", 1, importResult.getCountSkippedRecords());
            assertEquals("Wrong quantity error records", 0, importResult.getCountErrorRecords());
			assertEquals("Mismatch quantity imported records vs. mapped value objects", 
					importResult.getCountMappedValueObjects(), listener.size());

			AdjustTestValueObject vo = listener.getMappedValueObjects().get(0);
			assertEquals("Wrong mapping A", "AB", vo.getA());
			assertEquals("Wrong mapping B", "ZY", vo.getB());
            assertEquals("Wrong mapping C", DateUtil.getDate(2008, Calendar.NOVEMBER, 23), vo.getC());
			assertEquals("Wrong mapping D", 99, vo.getD().intValue());
			
			vo = listener.getMappedValueObjects().get(1);
			assertEquals("Wrong mapping A", "CD", vo.getA());
			assertEquals("Wrong mapping B", "  ", vo.getB());
            assertEquals("Wrong mapping C", DateUtil.getDate(1974, Calendar.JULY, 23), vo.getC());
			assertNull("Wrong mapping D", vo.getD());
			
			vo = listener.getMappedValueObjects().get(2);
			assertEquals("Wrong mapping A", "EF", vo.getA());
			assertEquals("Wrong mapping B", "  ", vo.getB());
			assertNull("Wrong mapping C", vo.getC());
			assertNull("Wrong mapping D", vo.getD());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Never come here - expected an import result with valid infomation");
		}
	}

}
