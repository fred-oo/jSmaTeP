package com.kardass.jsmatep.parser;

import static com.kardass.jsmatep.common.UrlUtil.packageToRealPath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;

import org.junit.Test;

public class CsvParserDelimiterTest {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private final String xmlConfigFile = packageToRealPath(getClass()) + "/Import-Config-CSV-DelimiterTest.xml";

	/** Test a successful data import . */
	@Test
	public void testSuccessful() throws Exception {
		String[] contents = new String[1];
		contents[0] = "20081123, abcde";
		
		RecordMapperListener<ImporterTestValueObject> listener = new RecordMapperListener<ImporterTestValueObject>() {
			@Override
			public void afterRecordMapped(RecordMappedEvent<ImporterTestValueObject> e) {
				try {
					final ImporterTestValueObject vo = e.getMappedValueObject();
					assertEquals(null, vo.getSomeOtherName());
					assertEquals(dateFormat.parseObject("20081123"), vo.getB());
					assertEquals(null, vo.getC());
					assertTrue(0.0f == vo.getD());
					assertEquals(null, vo.getE());
					assertEquals(" abcde", vo.getF());
					assertEquals(null, vo.getG());
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		};
		
		ImportProcessor.importContent(xmlConfigFile, listener, contents);
	}

	/** Test a successful data import . */
	@Test
	public void testSuccessfulWithTrailingAt() throws Exception {
		String[] contents = new String[1];
		contents[0] = "20081123, abcde@";
		
		RecordMapperListener<ImporterTestValueObject> listener = new RecordMapperListener<ImporterTestValueObject>() {
			@Override
			public void afterRecordMapped(RecordMappedEvent<ImporterTestValueObject> e) {
				try {
					final ImporterTestValueObject vo = e.getMappedValueObject();
					assertEquals(null, vo.getSomeOtherName());
					assertEquals(dateFormat.parseObject("20081123"), vo.getB());
					assertEquals(null, vo.getC());
					assertTrue(0.0f == vo.getD());
					assertEquals(null, vo.getE());
					assertEquals(" abcde@", vo.getF());
					assertEquals(null, vo.getG());
				} catch (Exception e2) {
					throw new RuntimeException(e2);
				}
			}
		};
		
		ImportProcessor.importContent(xmlConfigFile, listener, contents);
	}

}
