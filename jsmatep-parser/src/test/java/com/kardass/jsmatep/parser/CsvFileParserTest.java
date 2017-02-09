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

public class CsvFileParserTest {
    private final String xmlConfigFile = packageToRealPath(getClass()) + "/CsvFileParserTest.xml";
    private final String importFile = packageToRealPath(getClass()) + "/CsvFileParserTest-Data.txt";
	
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
	public void testSuccessfulImportWithCommentAndBlankLine() throws Exception {
		String[] contents = new String[3];
		contents[0] = "#some comment";
		contents[1] = "  "; // test blank line
		// see test data in 'ExampleImportFile-OK.txt'
		contents[2] = "AB;20081123; 32; 2.23;11.22; ;12345";
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
		String content = "AB;20081123; 32; 2.23;11.22; ;12345;tooManyFields";
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
		private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
		
		public void afterRecordMapped(RecordMappedEvent<ImporterTestValueObject> e) {
			try {
				if (e.getCurrentRecordNumber() == 1) {
					assertFirstRecord(e);
				} else if (e.getCurrentRecordNumber() == 2) {
					assertSecondRecord(e);
				} else if (e.getCurrentRecordNumber() == 3) {
					assertThirdRecord(e);
				} else if (e.getCurrentRecordNumber() == 4) {
					assertForthRecord(e);
				} else {
					fail("Only 4 data records expected");
				}
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}
		}

		private void assertFirstRecord(RecordMappedEvent<ImporterTestValueObject> e) throws ParseException {
			final ImporterTestValueObject vo = e.getMappedValueObject();
			assertEquals("AB", vo.getSomeOtherName());
			assertEquals(dateFormat.parseObject("20081123"), vo.getB());
			assertEquals(Integer.valueOf(32), vo.getC());
			assertTrue(2.23f == vo.getD());
			assertEquals(new BigDecimal(11.22).setScale(2, ROUNDING_MODE), vo.getE());
			assertEquals(" ", vo.getF());
			assertEquals(new BigDecimal(123.45).setScale(2, ROUNDING_MODE), vo.getG());
		}

		private void assertSecondRecord(RecordMappedEvent<ImporterTestValueObject> e) throws ParseException {
			final ImporterTestValueObject vo = e.getMappedValueObject();
			assertEquals("AB", vo.getSomeOtherName());
			assertEquals(dateFormat.parseObject("20081123"), vo.getB());
			assertEquals(Integer.valueOf(32), vo.getC());
			assertTrue(2.23f == vo.getD());
			assertEquals(new BigDecimal(11.22).setScale(2, ROUNDING_MODE), vo.getE());
			assertEquals("", vo.getF());
			assertEquals(new BigDecimal(123.45).setScale(2, ROUNDING_MODE), vo.getG());
		}

		private void assertThirdRecord(RecordMappedEvent<ImporterTestValueObject> e) throws ParseException {
			final ImporterTestValueObject vo = e.getMappedValueObject();
			assertEquals("Z ", vo.getSomeOtherName());
			assertEquals(dateFormat.parseObject("20012804"), vo.getB());
			assertEquals(Integer.valueOf(7), vo.getC());
			assertTrue(0.11f == vo.getD());
			assertEquals(new BigDecimal(3), vo.getE());
			assertEquals("x", vo.getF());
			assertEquals(new BigDecimal(0.99).setScale(2, ROUNDING_MODE), vo.getG());
		}

		private void assertForthRecord(RecordMappedEvent<ImporterTestValueObject> e) throws ParseException {
			final ImporterTestValueObject vo = e.getMappedValueObject();
			assertEquals("", vo.getSomeOtherName());
			assertEquals(null, vo.getB());
			assertEquals(null, vo.getC());
			assertTrue(0.0f == vo.getD());
			assertEquals(null, vo.getE());
			assertEquals("", vo.getF());
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
