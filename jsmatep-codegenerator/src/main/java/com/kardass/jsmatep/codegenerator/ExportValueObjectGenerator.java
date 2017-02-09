package com.kardass.jsmatep.codegenerator;

import java.io.File;
import java.io.InputStream;

import com.kardass.jsmatep.codegenerator.java.PropertyConfiguration;
import com.kardass.jsmatep.codegenerator.java.ValueObjectConfiguration;
import com.kardass.jsmatep.common.io.IOUtil;
import com.kardass.jsmatep.schema.jSmaTePComposerConfig.FieldType;
import com.kardass.jsmatep.schema.jSmaTePComposerConfig.JSmaTePComposerConfigDocument;
import com.kardass.jsmatep.schema.jSmaTePComposerConfig.RecordComposerConfigType;


public class ExportValueObjectGenerator extends ValueObjectGenerator {

	/**
	 * @param config
	 */
	public ExportValueObjectGenerator(CodeGeneratorConfiguration config) {
		super(config);
	}

	/**
	 * @param configFileName   XML data config (description) file
	 * @param generatorDestinationPath   Destination directory
	 */
	public ExportValueObjectGenerator(String configFileName,
			String generatorDestinationPath) {
		super(configFileName, generatorDestinationPath);
	}

	private File generateExportValueObject() {
		return super.generateValueObject(readExportValueObjectConfiguration());
	}

	/**
	 * Generates a value object for a given configuration.
	 * 
	 * @param config
	 * @return
	 */
	public static final File generateValueObject(CodeGeneratorConfiguration config) {
		final ExportValueObjectGenerator generator = new ExportValueObjectGenerator(config);
		return generator.generateExportValueObject();
	}

	/**Generates a value object for a given configuration and into a given destination directory.
	 * 
	 * @param configFileName
	 * @param genDestPath
	 * @return
	 */
	public static final File generateValueObject(String configFileName, String genDestPath) {
		final ExportValueObjectGenerator generator = new ExportValueObjectGenerator(configFileName, genDestPath);
		return generator.generateExportValueObject();
	}
	
	private ValueObjectConfiguration readExportValueObjectConfiguration() {
		InputStream in = null;
		try {
			in = IOUtil.getAsInputStream(configuration.getXmlDataFile());
            final JSmaTePComposerConfigDocument xmlDoc = JSmaTePComposerConfigDocument.Factory.parse(in);
            final RecordComposerConfigType xmlConfig = xmlDoc.getJSmaTePComposerConfig();
	        final FieldType[] fields = xmlConfig.getFields().getFieldArray();
			final ValueObjectConfiguration voConfig = new ValueObjectConfiguration(xmlConfig.getValueObject());
			for (FieldType aField : fields) {
				voConfig.addProperty(
						new PropertyConfiguration(aField.getName(), aField.getType().toString()));
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
