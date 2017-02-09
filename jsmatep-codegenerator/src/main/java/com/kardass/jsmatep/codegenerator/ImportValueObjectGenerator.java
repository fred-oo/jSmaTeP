package com.kardass.jsmatep.codegenerator;

import java.io.File;
import java.io.InputStream;

import com.kardass.jsmatep.codegenerator.java.PropertyConfiguration;
import com.kardass.jsmatep.codegenerator.java.ValueObjectConfiguration;
import com.kardass.jsmatep.common.io.IOUtil;
import com.kardass.jsmatep.schema.jSmaTePParserConfig.FieldType;
import com.kardass.jsmatep.schema.jSmaTePParserConfig.JSmaTePParserConfigDocument;
import com.kardass.jsmatep.schema.jSmaTePParserConfig.RecordParserConfigType;


public class ImportValueObjectGenerator extends ValueObjectGenerator {

	/**
	 * @param config
	 */
	public ImportValueObjectGenerator(CodeGeneratorConfiguration config) {
		super(config);
	}

	/**
	 * @param configFileName   XML data config (description) file
	 * @param generatorDestinationPath   Destination directory
	 */
	public ImportValueObjectGenerator(String configFileName, String generatorDestinationPath) {
		this(new CodeGeneratorConfiguration(generatorDestinationPath, configFileName));
	}
	
	private File generateImportValueObject() {
		return super.generateValueObject(readImportValueObjectConfiguration());
	}

	/**
	 * Generates a value object for a given configuration.
	 * 
	 * @param config
	 * @return
	 */
	public static final File generateValueObject(CodeGeneratorConfiguration config) {
		final ImportValueObjectGenerator generator = new ImportValueObjectGenerator(config);
		return generator.generateImportValueObject();
	}

	/**Generates a value object for a given configuration and into a given destination directory.
	 * 
	 * @param configFileName
	 * @param genDestPath
	 * @return
	 */
	public static final File generateValueObject(String configFileName, String genDestPath) {
		final ImportValueObjectGenerator generator = new ImportValueObjectGenerator(configFileName, genDestPath);
		return generator.generateImportValueObject();
	}
	
	private ValueObjectConfiguration readImportValueObjectConfiguration() {
		InputStream in = null;
		try {
			in = IOUtil.getAsInputStream(configuration.getXmlDataFile());
            final JSmaTePParserConfigDocument xmlDoc = JSmaTePParserConfigDocument.Factory.parse(in);
            final RecordParserConfigType xmlConfig = xmlDoc.getJSmaTePParserConfig();
	        final FieldType[] fields = xmlConfig.getFields().getFieldArray();
			final ValueObjectConfiguration voConfig = new ValueObjectConfiguration(xmlConfig.getValueObject());
			String propertyName;
			for (FieldType aField : fields) {
				propertyName = aField.getBeanPropertyName() == null ? aField.getName() : aField.getBeanPropertyName();
				voConfig.addProperty(
						new PropertyConfiguration(propertyName, aField.getType().toString()));
			}
			return voConfig;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtil.closeStream(in);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			generateValueObject(
					createGeneratorConfiguration(args));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
