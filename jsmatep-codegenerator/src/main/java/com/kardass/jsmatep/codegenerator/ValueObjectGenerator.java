package com.kardass.jsmatep.codegenerator;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.kardass.jsmatep.codegenerator.java.JavaGenerator;
import com.kardass.jsmatep.codegenerator.java.JavaGeneratorConstants;
import com.kardass.jsmatep.codegenerator.java.PropertyConfiguration;
import com.kardass.jsmatep.codegenerator.java.ValueObjectConfiguration;
import com.kardass.jsmatep.common.io.ContentWriter;

/**
 * Value object generator for reading data - based on a given XML (data) configuration file.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public abstract class ValueObjectGenerator {
	
	/**
	 * The code generator configuration.
	 */
	protected CodeGeneratorConfiguration configuration;

	/**
	 * @param config
	 */
	public ValueObjectGenerator(CodeGeneratorConfiguration config) {
		this.configuration = config;
	}

	/**
	 * @param configFileName   XML data config (description) file
	 * @param generatorDestinationPath   Destination directory
	 */
	public ValueObjectGenerator(String configFileName, String generatorDestinationPath) {
		this(new CodeGeneratorConfiguration(generatorDestinationPath, configFileName));
	}
	
	/**
	 * @return
	 */
	public CodeGeneratorConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * @param configuration
	 */
	public void setConfiguration(CodeGeneratorConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * The 'worker' for generating the value object.
	 * @return
	 */
	protected File generateValueObject(ValueObjectConfiguration voConfiguration) {
		final StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("/*").append(JavaGeneratorConstants.LINE_FEED);
		strBuilder.append(" * Created: ").append(new Date()).append(" by ").append(System.getProperty("user.name")).append(JavaGeneratorConstants.LINE_FEED);
		strBuilder.append(" */").append(JavaGeneratorConstants.LINE_FEED);
		strBuilder.append(
				JavaGenerator.generatePackage(voConfiguration.getClassName()));
		strBuilder.append(
				JavaGenerator.generateClassHeader(voConfiguration.getClassName()));
		for (PropertyConfiguration aProperty : voConfiguration.getProperties()) {
			strBuilder.append(
					JavaGenerator.generatePropertyWithSetterAndGetter(aProperty.getName(), aProperty.getType()));
		}
		strBuilder.append(
				JavaGenerator.generateToStringMethod(voConfiguration.getAllPropertyNames()));
		strBuilder.append(
				JavaGenerator.generateClassFooter());
		return saveAsJavaFile(voConfiguration, strBuilder.toString());
	}

	/**
	 * Saves the generated class for a given config as a file.
	 * 
	 * @param valueObjectConfig
	 * @param generatedClass
	 * @return
	 */
	private File saveAsJavaFile(ValueObjectConfiguration valueObjectConfig, String generatedClass) {
		File destFile;
		try {
			destFile = 
				PackageToRealFile.createPackageStructureAndFile(
						configuration.getSourceDestinationPath(), valueObjectConfig.getClassName());
			ContentWriter.saveContents(destFile, generatedClass);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return destFile;
	}

	/**
	 * Creates the generator configuration from the given args.
	 * 
	 * @param args
	 * @return
	 */
	protected static CodeGeneratorConfiguration createGeneratorConfiguration(String[] args) {
		if (args == null || args.length != 2) {
			throw new RuntimeException("Wrong number of submitted parameters: java " 
					+ ValueObjectGenerator.class.getName() + "<XML-Config-File> <destination dir>");
		}
		CodeGeneratorConfiguration config = new CodeGeneratorConfiguration();
		config.setXmlDataFile(args[0]);
		config.setSourceDestinationPath(args[1]);
		System.out.println("XML-File:    " + config.getXmlDataFile());
		System.out.println("Destination: " + config.getSourceDestinationPath());
		return config;
	}

}
