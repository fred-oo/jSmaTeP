package com.kardass.jsmatep.parser.continueonerror;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.kardass.jsmatep.common.UrlUtil;
import com.kardass.jsmatep.parser.BufferedRecordMapperListener;
import com.kardass.jsmatep.parser.ImportProcessor;
import com.kardass.jsmatep.parser.ImportResult;

public class ContinueOnErrorTest {

	private static final List<String> importStrings;
	
	static {
		importStrings = new ArrayList<String>();
		importStrings.add("#some comment"); // => skip
		importStrings.add("  "); // test blank line => skip
		importStrings.add("AB20081123 A2"); // instead 32, use A2 to provoke error;
		importStrings.add("XY20101123 11"); // => success
	}
	
	@Test
	public void testContinueOnError() {
        final String xmlConfigFile = UrlUtil.packageToRealPath(getClass())
                + "/Continue-On-Error-Config-Fixed-Length.xml";
		final BufferedRecordMapperListener<ContinueOnErrorTestValueObject> listener = 
			new BufferedRecordMapperListener<ContinueOnErrorTestValueObject>();
		final ImportProcessor<ContinueOnErrorTestValueObject> ip = 
			new ImportProcessor<ContinueOnErrorTestValueObject>(xmlConfigFile, listener);
		try {
			final ImportResult importResult = ip.importContent(importStrings);
			assertEquals("Wrong quantity imported records", 1, listener.size());
            assertEquals("Wrong quantity skipped records", 2, importResult.getCountSkippedRecords());
            assertEquals("Wrong quantity error records", 1, importResult.getCountErrorRecords());
			assertEquals("Mismatch quantity imported records vs. mapped value objects", 
					importResult.getCountMappedValueObjects(), listener.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Never come here - expected an import result with valid infomation");
		}
	}
	
	@Test
	public void testHaltOnError() {
        final String xmlConfigFile = UrlUtil.packageToRealPath(getClass()) + "/Halt-On-Error-Config-Fixed-Length.xml";
		final ImportProcessor<ContinueOnErrorTestValueObject> ip = 
			new ImportProcessor<ContinueOnErrorTestValueObject>(xmlConfigFile);
		try {
			ip.importContent(importStrings);
			fail("Never come here - expected an abort while parsing the import data");
		} catch (Exception e) {
			// everythins is fine! ;-)
		}
	}
	
}
