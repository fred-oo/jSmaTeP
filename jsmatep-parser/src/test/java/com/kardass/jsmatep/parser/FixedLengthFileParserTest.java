package com.kardass.jsmatep.parser;

import static com.kardass.jsmatep.common.UrlUtil.packageToRealPath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import com.kardass.jsmatep.parser.validation.IllegalRecordLengthException;

/**
 * Test case for record import validation.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class FixedLengthFileParserTest {

    private final String xmlConfigFile = packageToRealPath(getClass()) + "/FixedLengthFileParserTest.xml";
    private final String importFile = packageToRealPath(getClass()) + "/FixedLengthFileParserTest-Data.txt";
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	/** Test a successful file import */
	@Test
	public void testSuccessfulImportFromFile() throws Exception {
		ImportProcessor.importFile(
				xmlConfigFile,
				new SuccessfulRecordMapperTestListener(),
				importFile);
	}

	/** Test a successful data import with an comment in it. */
	@Test
	public void testSuccessfulImportWithComment() throws Exception {
		String[] contents = new String[4];
		contents[0] = "#some comment";
		contents[1] = "  "; // test blank line
		// see test data in 'ExampleImportFile-OK.txt'
		contents[2] = "AB20081123 32 2.2311.22 12345";
		contents[3] = "Z 20012804  7 0.11    3x   99";
		ImportProcessor.importContent(
				xmlConfigFile,
				new SuccessfulRecordMapperTestListener(),
				contents);
	}

	/** Validates a failure if an record is too short. */
	@Test
	public void testImportTooShortRecord() throws Exception {
		String content = "too short";
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
	private static class SuccessfulRecordMapperTestListener implements RecordMapperListener<ImporterTestValueObject> {
		public void afterRecordMapped(RecordMappedEvent<ImporterTestValueObject> e) {
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
			}
		}

		private void assertFirstRecord(RecordMappedEvent<ImporterTestValueObject> e) throws ParseException {
			ImporterTestValueObject vo = e.getMappedValueObject();
			assertEquals("AB", vo.getSomeOtherName());
			assertEquals("AB20081123", vo.getAaa());
			assertEquals(dateFormat.parseObject("20081123"), vo.getB());
			assertEquals(Integer.valueOf(32), vo.getC());
			assertTrue(2.23f == vo.getD());
			BigDecimal aBigDecimal = new BigDecimal("11.22").setScale(2);
			assertEquals(aBigDecimal, vo.getE());
			assertEquals(" ", vo.getF());
			assertEquals(new BigDecimal(123.45).setScale(2), vo.getG());
		}

		private void assertSecondRecord(RecordMappedEvent<ImporterTestValueObject> e) throws ParseException {
			ImporterTestValueObject vo = e.getMappedValueObject();
			assertEquals("Z ", vo.getSomeOtherName());
			assertEquals("Z 20012804", vo.getAaa());
			assertEquals(dateFormat.parseObject("20012804"), vo.getB());
			assertEquals(Integer.valueOf(7), vo.getC());
			assertTrue(0.11f == vo.getD());
            assertEquals(new BigDecimal("3"), vo.getE());
			assertEquals("x", vo.getF());
            assertEquals(new BigDecimal("0.99"), vo.getG());
		}

		private void assertThirdRecord(RecordMappedEvent<ImporterTestValueObject> e) throws ParseException {
			ImporterTestValueObject vo = e.getMappedValueObject();
			assertEquals("DD", vo.getSomeOtherName());
			assertEquals("DD        ", vo.getAaa());
			assertEquals(null, vo.getB());
			assertEquals(null, vo.getC());
			assertTrue(0.0f == vo.getD());
			assertEquals(null, vo.getE());
			assertEquals(" ", vo.getF());
			assertEquals(null, vo.getG());
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
