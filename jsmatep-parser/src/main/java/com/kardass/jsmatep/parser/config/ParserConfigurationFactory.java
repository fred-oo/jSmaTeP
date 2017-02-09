package com.kardass.jsmatep.parser.config;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.kardass.jsmatep.common.io.IOUtil;
import com.kardass.jsmatep.parser.JsmatepInconsistencyException;
import com.kardass.jsmatep.parser.config.field.CharacterField;
import com.kardass.jsmatep.parser.config.field.DateField;
import com.kardass.jsmatep.parser.config.field.DecimalField;
import com.kardass.jsmatep.parser.config.field.DoubleField;
import com.kardass.jsmatep.parser.config.field.Field;
import com.kardass.jsmatep.parser.config.field.FloatField;
import com.kardass.jsmatep.parser.config.field.IntegerField;
import com.kardass.jsmatep.parser.config.field.LongField;
import com.kardass.jsmatep.parser.config.field.StringField;
import com.kardass.jsmatep.parser.validation.ConfigValidator;
import com.kardass.jsmatep.schema.jSmaTePParserConfig.FieldType;
import com.kardass.jsmatep.schema.jSmaTePParserConfig.JSmaTePParserConfigDocument;
import com.kardass.jsmatep.schema.jSmaTePParserConfig.RecordParserConfigType;
import com.kardass.jsmatep.schema.jSmaTePParserConfig.RecordParserConfigType.SkipRecord;

/**
 * Factory to create a {@link ParserConfiguration} based on a given XML configuration.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public abstract class ParserConfigurationFactory {

	/**
	 * @param configFileName
	 * 					XML configuration file
	 * @return
	 * 		Created {@link ParserConfiguration}
	 */
	public static ParserConfiguration createConfiguration(String configFileName) {
		InputStream in = null;
		try {
			in = IOUtil.getAsInputStream(configFileName);
            final JSmaTePParserConfigDocument theDoc = JSmaTePParserConfigDocument.Factory.parse(in);
            final RecordParserConfigType xmlImportConfig = theDoc.getJSmaTePParserConfig();
	        final ParserConfiguration parserConfig = createConfigByType(xmlImportConfig);
	        ConfigValidator.validateConfig(parserConfig);
	        return parserConfig;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtil.closeStream(in);
		}
	}

	/**
	 * Creates a {@link CsvParserConfiguration}, a {@link FixedLengthParserConfiguration}
	 * or a {@link VariableLengthParserConfiguration} depending the given XML configuration.
	 * 
	 * @param xmlImportConfig
	 * @return
	 */
    private static ParserConfiguration createConfigByType(RecordParserConfigType xmlImportConfig) {
		// create fields' configuration
		final FieldType[] fieldTypes = xmlImportConfig.getFields().getFieldArray();
		final List<Field<?>> fieldConfigs = new ArrayList<Field<?>>();
		for (FieldType aFieldConfig : fieldTypes) {
			fieldConfigs.add(
					createFieldConfig(aFieldConfig));
		}
		final List<String> skipRecordIdents = getSkipRecordIdentifiers(xmlImportConfig);
		// create final configuration
		if (xmlImportConfig.getType().getCsv() != null) {
			return new CsvParserConfiguration(xmlImportConfig.getValueObject().trim(), 
						xmlImportConfig.getContinueOnRecordError(),
						xmlImportConfig.getVersion(), 
						fieldConfigs, 
						xmlImportConfig.getType().getCsv().getDelimiter(), 
						xmlImportConfig.getType().getCsv().getFieldCount(),
						skipRecordIdents);
		} else if (xmlImportConfig.getType().getFixedLength() != null) {
			return new FixedLengthParserConfiguration(xmlImportConfig.getValueObject().trim(), 
						xmlImportConfig.getContinueOnRecordError(), 
						xmlImportConfig.getVersion(), 
						fieldConfigs,
						xmlImportConfig.getType().getFixedLength().getRecordLength(), 
						xmlImportConfig.getType().getFixedLength().getFillWith(),
						skipRecordIdents);
		} else if (xmlImportConfig.getType().getVariableLength() != null) {
			return new VariableLengthParserConfiguration(xmlImportConfig.getValueObject().trim(), 
						xmlImportConfig.getContinueOnRecordError(), 
						xmlImportConfig.getVersion(), 
						fieldConfigs,
						xmlImportConfig.getType().getVariableLength().getMinRecordLength(),
						xmlImportConfig.getType().getVariableLength().getMaxRecordLength(),
						xmlImportConfig.getType().getVariableLength().getFillWith(),
						skipRecordIdents);
		} else {
			throw new IllegalArgumentException("Unknown record type: " + xmlImportConfig.getType());
		}
	}

	/**
	 * Returns a list of identifiers for skipping records which start with one 
	 * of these identifiers.
	 *  
	 * @param xmlImportConfig
	 * @return
	 * 		The configured identifiers
	 */
    private static List<String> getSkipRecordIdentifiers(RecordParserConfigType xmlImportConfig) {
		final SkipRecord skipRecord = xmlImportConfig.getSkipRecord();
		if (skipRecord == null) {
			return Collections.emptyList();
		} else {
			return Arrays.asList(skipRecord.getStartsWithArray());
		}
	}
	
	/**
	 * Creates the fields for the configuration.
	 * 
	 * @param xmlFieldConfig
	 * @return
	 */
	private static Field<?> createFieldConfig(FieldType xmlFieldConfig) {
		final String fieldType = xmlFieldConfig.getType().toString();
		if (fieldType.equals(String.class.getSimpleName())) {
			return new StringField(xmlFieldConfig.getName(), xmlFieldConfig.getBeanPropertyName(), 
					xmlFieldConfig.getPosition(), xmlFieldConfig.getLength(), 
					fieldType, xmlFieldConfig.getFormat(), 
					xmlFieldConfig.getNullValue(), xmlFieldConfig.getDefaultValue());
		} else if (fieldType.equals(int.class.getSimpleName()) || fieldType.equals(Integer.class.getSimpleName())) {
			return new IntegerField(xmlFieldConfig.getName(), xmlFieldConfig.getBeanPropertyName(), 
					xmlFieldConfig.getPosition(), xmlFieldConfig.getLength(), 
					fieldType, xmlFieldConfig.getFormat(), 
					xmlFieldConfig.getNullValue(), xmlFieldConfig.getDefaultValue());
		} else if (fieldType.equals(long.class.getSimpleName()) || fieldType.equals(Long.class.getSimpleName())) {
			return new LongField(xmlFieldConfig.getName(), xmlFieldConfig.getBeanPropertyName(), 
					xmlFieldConfig.getPosition(), xmlFieldConfig.getLength(), 
					fieldType, xmlFieldConfig.getFormat(), 
					xmlFieldConfig.getNullValue(), xmlFieldConfig.getDefaultValue());
		} else if (fieldType.equals(float.class.getSimpleName()) || fieldType.equals(Float.class.getSimpleName())) {
			return new FloatField(xmlFieldConfig.getName(), xmlFieldConfig.getBeanPropertyName(), 
					xmlFieldConfig.getPosition(), xmlFieldConfig.getLength(), 
					fieldType, xmlFieldConfig.getFormat(), 
					xmlFieldConfig.getNullValue(), xmlFieldConfig.getDefaultValue());
		} else if (fieldType.equals(double.class.getSimpleName()) || fieldType.equals(Double.class.getSimpleName())) {
			return new DoubleField(xmlFieldConfig.getName(), xmlFieldConfig.getBeanPropertyName(), 
					xmlFieldConfig.getPosition(), xmlFieldConfig.getLength(), 
					fieldType, xmlFieldConfig.getFormat(), 
					xmlFieldConfig.getNullValue(), xmlFieldConfig.getDefaultValue());
		} else if (fieldType.equals(char.class.getSimpleName()) || fieldType.equals(Character.class.getSimpleName())) {
			return new CharacterField(xmlFieldConfig.getName(), xmlFieldConfig.getBeanPropertyName(), 
					xmlFieldConfig.getPosition(), xmlFieldConfig.getLength(), 
					fieldType, xmlFieldConfig.getFormat(), 
					xmlFieldConfig.getNullValue(), xmlFieldConfig.getDefaultValue());
		} else if (fieldType.equals(Date.class.getSimpleName())) {
			return new DateField(xmlFieldConfig.getName(), xmlFieldConfig.getBeanPropertyName(), 
					xmlFieldConfig.getPosition(), xmlFieldConfig.getLength(), 
					fieldType, xmlFieldConfig.getFormat(), 
					xmlFieldConfig.getNullValue(), xmlFieldConfig.getDefaultValue());
		} else if (fieldType.equals(BigDecimal.class.getSimpleName()) || fieldType.equals("Decimal")) {
			return new DecimalField(xmlFieldConfig.getName(), xmlFieldConfig.getBeanPropertyName(), 
					xmlFieldConfig.getPosition(), xmlFieldConfig.getLength(), 
					fieldType, xmlFieldConfig.getFormat(), 
					xmlFieldConfig.getNullValue(), xmlFieldConfig.getDefaultValue());
		} else {
			throw new JsmatepInconsistencyException("Field type [" + fieldType + "] is not supported! " + xmlFieldConfig);
		}
	}

}
