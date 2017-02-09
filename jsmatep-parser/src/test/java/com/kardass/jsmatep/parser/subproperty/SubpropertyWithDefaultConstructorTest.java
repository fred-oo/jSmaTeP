package com.kardass.jsmatep.parser.subproperty;

import static com.kardass.jsmatep.common.UrlUtil.packageToRealPath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.kardass.jsmatep.parser.ImportProcessor;
import com.kardass.jsmatep.parser.ImportResult;
import com.kardass.jsmatep.parser.RecordMappedEvent;
import com.kardass.jsmatep.parser.RecordMapperListener;

public class SubpropertyWithDefaultConstructorTest 
		implements RecordMapperListener<SubpropertyTestValueObjectWithDefaultConstrutor> {

	private static final List<String> importStrings;
	
	static {
		importStrings = new ArrayList<String>();
		importStrings.add("#some comment"); // => skip
		importStrings.add("  "); // test blank line => skip
		importStrings.add("ABA2");
		importStrings.add("zyxw");
	}
	
	@Test
	public void testContinueOnError() {
        final String xmlConfigFile = packageToRealPath(getClass()) + "/Subproperty-With-Default-Constructor.xml";
		final ImportProcessor<SubpropertyTestValueObjectWithDefaultConstrutor> ip = 
			new ImportProcessor<SubpropertyTestValueObjectWithDefaultConstrutor>(xmlConfigFile, this);
		try {
			final ImportResult importResult = ip.importContent(importStrings);
            assertEquals("Wrong quantity skipped records", 2, importResult.getCountSkippedRecords());
            assertEquals("Wrong quantity error records", 0, importResult.getCountErrorRecords());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Never come here - expected an import result with valid infomation");
		}
	}

	@Override
	public void afterRecordMapped(RecordMappedEvent<SubpropertyTestValueObjectWithDefaultConstrutor> e) {
		SubpropertyTestValueObjectWithDefaultConstrutor vo = e.getMappedValueObject();
		if (e.getCurrentRecordNumber() == 1) {
			assertEquals("Wrong record mapping", "AB", vo.getPk().getA());
			assertEquals("Wrong record mapping", "A2", vo.getA());
		} else if (e.getCurrentRecordNumber() == 2) {
			assertEquals("Wrong record mapping", "zy", vo.getPk().getA());
			assertEquals("Wrong record mapping", "xw", vo.getA());
		} else {
			fail("Never come to this point as there are only to valid import records");
		}
	}
	
}
