package com.kardass.jsmatep.parser;

import static com.kardass.jsmatep.common.UrlUtil.packageToRealPath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.kardass.jsmatep.parser.validation.IllegalRecordLengthException;

/**
 * Test case for record import validation.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class VariableLengthFileParserTest {

    private final String xmlConfigFile = packageToRealPath(getClass()) + "/VariableLengthFileParserTest.xml";
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
	private static final List<String> importStrings;
	
	static {
		importStrings = new ArrayList<String>();
		importStrings.add("AB20081123 32 2.2311.22 12345"); 
		importStrings.add("Z 20012804  7");
		importStrings.add("DD");
		importStrings.add(""); //empty record => skipp
	}


	/** Test a successful file import */
	@Test
	public void testSuccessfulImportFromFile() throws Exception {
		SuccessfulRecordMapperTestListener listener = new SuccessfulRecordMapperTestListener();
		final ImportProcessor<ImporterTestValueObject> importProcessor = 
			new ImportProcessor<ImporterTestValueObject>(xmlConfigFile, listener);
		final ImportResult result = importProcessor.importContent(importStrings);
		assertEquals("Wrong number of imported records", 3, listener.size());
        assertEquals("Wrong number of error records", 0, result.getCountErrorRecords());
        assertEquals("Wrong number of skipped records", 1, result.getCountSkippedRecords());
	}

	/** Validates a failure if an record is too long. */
	@Test
	public void testImportTooLongRecord() throws Exception {
		String content = "AB20081123 32 2.2311.22 toooooooooooLong";
		try {
			ImportProcessor.importContent(
					xmlConfigFile,
					new FailRecordMapperTestListener(),
					content);
			fail("Never get to this point");
		} catch (IllegalRecordLengthException e) {
			// expected exception
		}
	}
	
	

	/**
	 * Local test class for successful record mapping validation 
	 * ({@link FixedLengthFileParserTest#testSuccessfulImportFromFile()} 
	 * and {@link FixedLengthFileParserTest#testSuccessfulImportWithComment()})
	 *
	 * @author Manfred Kardass (manfred@kardass.de)
	 *
	 */
	private static class SuccessfulRecordMapperTestListener extends BufferedRecordMapperListener<ImporterTestValueObject> {
		public void afterRecordMapped(RecordMappedEvent<ImporterTestValueObject> e) {
			super.afterRecordMapped(e);
			try {
				if (e.getCurrentRecordNumber() == 1) {
					assertFirstRecord(e);
				} else if (e.getCurrentRecordNumber() == 2) {
					assertSecondRecord(e);
				} else if (e.getCurrentRecordNumber() == 3) {
					assertThirdRecord(e);
				} else {
					fail("Only 3 data records expected");
				}
			} catch (Exception e2) {
				fail(e2.getMessage());
			}
		}

		private void assertFirstRecord(RecordMappedEvent<ImporterTestValueObject> e) throws ParseException {
			ImporterTestValueObject vo = e.getMappedValueObject();
			assertEquals("AB", vo.getSomeOtherName());
			assertEquals("AB20081123", vo.getAaa());
			assertEquals(dateFormat.parseObject("20081123"), vo.getB());
			assertEquals(Integer.valueOf(32), vo.getC());
			assertTrue(2.23f == vo.getD());
			assertEquals(new BigDecimal("11.22"), vo.getE());
			assertEquals(" ", vo.getF());
			assertEquals(new BigDecimal("123.45"), vo.getG());
		}

		private void assertSecondRecord(RecordMappedEvent<ImporterTestValueObject> e) throws ParseException {
			ImporterTestValueObject vo = e.getMappedValueObject();
			assertEquals("Z ", vo.getSomeOtherName());
			assertEquals("Z 20012804", vo.getAaa());
			assertEquals(dateFormat.parseObject("20012804"), vo.getB());
			assertEquals(Integer.valueOf(7), vo.getC());
			assertTrue(0.0f == vo.getD());
			assertNull(vo.getE());
			assertNull(vo.getF());
			assertNull(vo.getG());
		}

		private void assertThirdRecord(RecordMappedEvent<ImporterTestValueObject> e) throws ParseException {
			ImporterTestValueObject vo = e.getMappedValueObject();
			assertEquals("DD", vo.getSomeOtherName());
			assertEquals("DD", vo.getAaa());
			assertNull(vo.getB());
			assertNull(vo.getC());
			assertTrue(0.0f == vo.getD());
			assertNull(vo.getE());
			assertNull(vo.getF());
			assertNull(vo.getG());
		}
	}


	
	/**
	 * Local listener class for failure validation 
	 * ({@link FixedLengthFileParserTest#testImportTooShortRecord()} 
	 * and {@link FixedLengthFileParserTest#testImportTooLongRecord()}).
	 *
	 * @author Manfred Kardass (manfred@kardass.de)
	 *
	 */
	private static class FailRecordMapperTestListener implements RecordMapperListener<ImporterTestValueObject> {
		public void afterRecordMapped(RecordMappedEvent<ImporterTestValueObject> e) {
			fail("Never get to this point");
		}
	}
}
