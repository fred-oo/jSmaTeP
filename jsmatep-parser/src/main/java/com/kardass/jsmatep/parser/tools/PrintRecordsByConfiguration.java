package com.kardass.jsmatep.parser.tools;

import java.io.IOException;
import java.util.List;

import com.kardass.jsmatep.common.io.ContentReader;
import com.kardass.jsmatep.parser.ImportProcessor;
import com.kardass.jsmatep.parser.JsmatepInconsistencyException;
import com.kardass.jsmatep.parser.config.ParserConfiguration;
import com.kardass.jsmatep.parser.config.ParserConfigurationFactory;
import com.kardass.jsmatep.parser.config.field.Field;
import com.kardass.jsmatep.parser.reader.RecordReader;
import com.kardass.jsmatep.parser.reader.RecordReaderFactory;
import com.kardass.jsmatep.parser.validation.ConfigValidator;

/**
 * Prints an import file and possible errors based to a given configuration.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 * @since 03.03.2010
 *
 */
public class PrintRecordsByConfiguration extends ImportProcessor<Object> {

	/**	The record reader used by this mapper depending on a given configuration */
	private final RecordReader recordReader;

	private final ParserConfiguration parserConfiguration;
	
	public PrintRecordsByConfiguration(String configFileName) {
		super(configFileName);
		parserConfiguration = ParserConfigurationFactory.createConfiguration(configFileName);
		recordReader = RecordReaderFactory.getRecordReader(parserConfiguration);
	}

	public void printFile(String importFileName) {
		try {
			final List<String> content = ContentReader.readContents(importFileName);
			printContent(content);
		} catch (IOException e) {
			throw new JsmatepInconsistencyException(e);
		}
	}

	public void printContent(List<String> content) {
		ConfigValidator.validateConfig(parserConfiguration);
		long lineCounter = 0;
//		long recordCounter = 0;
		final List<Field<?>> fields = parserConfiguration.getFields();
		final StringBuffer msg = new StringBuffer();
		String aValueString;
		for (String aRecord : content) {
			++lineCounter;
			System.out.println();
			System.out.println("---");
			System.out.println();
			System.out.println("Line no. " + lineCounter + ": [" + aRecord + "]");
			try {
				if (ImportProcessor.skipContent(parserConfiguration, aRecord)) {
					// do nothing
				} else {
//					++recordCounter;
					for (Field<?> aField : fields) {
						aValueString = recordReader.readField(aField, aRecord);
						msg.append(aField.getName()).append(": [")
							.append(aValueString == null ? "" : aValueString).append("], type: ").append(aField.getType());
						if (aField.formatExists()) {
							msg.append(", format: ").append(aField.getFormat());
						}
						if (aField.nullValueExists()) {
							msg.append(", null-value: ").append(aField.getNullValue());
						}
						// Print value
						try {
							// try to parse string into target type
							aField.getAsTypedValue(aValueString);
							// everything's ok
							System.out.println(msg.toString());
						} catch (Exception e) {
							msg.append(" ### ERROR: " + e.getMessage());
							System.err.println(msg.toString());
						}
						msg.setLength(0);
					}
				}
			} catch (Exception e) {
				System.out.println("Error in line " + lineCounter);
				e.printStackTrace(System.out);
				System.out.println("##########");
			}
		}
	}

	public static void main(String... args) {
		if (args.length != 2) {
			usage();
			System.exit(-1);
		}
		final String configFile = args[0];
		final String importFile = args[1];
		System.out.println("Validate import file [" + importFile + "] with configuration [" + configFile + "]");
		try {
			new PrintRecordsByConfiguration(configFile).printFile(importFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println();
		System.out.println("---");
		System.out.println("Finished!");
	}

	private static void usage() {
		System.err.println("Illegal arguments calling " + PrintRecordsByConfiguration.class.getName());
		System.err.println(" call: java " + PrintRecordsByConfiguration.class.getName() + " <xml-config-file> <data-import-file>");
	}


}
