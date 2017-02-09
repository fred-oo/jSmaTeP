package com.kardass.jsmatep.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kardass.jsmatep.common.io.ContentReader;
import com.kardass.jsmatep.common.io.IOUtil;
import com.kardass.jsmatep.parser.config.ParserConfiguration;
import com.kardass.jsmatep.parser.config.ParserConfigurationFactory;
import com.kardass.jsmatep.parser.validation.IllegalRecordLengthException;

/**
 * Importer for processing text data.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 * @param <T>   "Destination" (value object content record will be mapped into this value object)
 */
public class ImportProcessor<T> {

	/**  Data configuration file. */
	protected final String configFileName;

	/** Registered listeners for data processing */
	protected List<RecordMapperListener<T>> listeners = new ArrayList<RecordMapperListener<T>>();

	/**
	 * @param configFileName
	 */
	public ImportProcessor(String configFileName) {
		this.configFileName = configFileName;
	}
	
	/**
	 * @param configFileName
	 * @param listener
	 */
	public ImportProcessor(String configFileName, RecordMapperListener<T> listener) {
		this(configFileName);
		registerListener(listener);
	}

	/**
	 * Imports the file 'importFileName' with the given config (configFileName) and notifies the 
	 * listener for each processed record.
	 * 
	 * @param configFileName
	 * @param listener
	 * @param importFileName
	 * @return 
     *		The import result.
	 */
	public static final <T> ImportResult importFile(String configFileName, RecordMapperListener<T> listener, String importFileName) {
		ImportProcessor<T> importer = new ImportProcessor<T>(configFileName, listener);
		return importer.importFile(importFileName);
	}

	/**
	 * Imports the string content (each element a data record) with the given config (configFileName) 
	 * and notifies the listener for each processed record.
	 * 
	 * @param configFileName
	 * @param listener
	 * @param content
	 * @return 
     *		The import result.
	 */
	public static final <T> ImportResult importContent(String configFileName, RecordMapperListener<T> listener, String... content) {
		List<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(content));
		return importContent(configFileName, listener, list);
	}

	/**
	 * Imports the string content (each element a data record) with the given config (configFileName) 
	 * and notifies the listener for each processed record.
	 * 
	 * @param configFileName
	 * @param listener
	 * @param content
	 * @return 
     *		The import result.
	 */
	public static final <T> ImportResult importContent(String configFileName, RecordMapperListener<T> listener, List<String> content) {
		ImportProcessor<T> importer = new ImportProcessor<T>(configFileName, listener);
		return importer.importContent(content);
	}

	/**
	 * Reads the content of the 'importFileName' and start processing.
	 * @see {@link #importContent(List)}
	 * 
	 * @param importFileName
	 * @return 
     *		The import result.
	 */
	public ImportResult importFile(String importFileName) {
		try {
			final List<String> content = ContentReader.readContents(importFileName);
			return importContent(content);
		} catch (IOException e) {
			throw new JsmatepInconsistencyException(e);
		}
	}

	/**
	 * @see #importContent(List)
	 * @param content
	 * @return
	 */
	public ImportResult importContent(String... content) {
		return importContent(Arrays.asList(content));
	}
	
	/**
	 * Creates a NEW value object (specified in the xml configuration) for each content record 
	 * and notifies all registered listeners for further data processing. 
	 * (Special record will be skipped @see {@link #skipContent(String)})
	 *  
	 * @param content
	 * @return 
     *		The import result.
	 */
	public ImportResult importContent(List<String> content) {
		InputStream in = null;
		try {
			final ParserConfiguration parserConfiguration = 
				ParserConfigurationFactory.createConfiguration(configFileName);
			final RecordToValueObjectMapper<T> mapper = new RecordToValueObjectMapper<T>(parserConfiguration);
			long recordCounter = 0;
			final ImportResult importResult = new ImportResult();
			T valueObject;
			for (String aRecord : content) {
				try {
					if (skipContent(parserConfiguration, aRecord)) {
						importResult.addSkippedRecord(aRecord);
					} else {
						++recordCounter;
						valueObject = mapper.mapRecordToValueObject(aRecord);
						notifyListeners(new RecordMappedEvent<T>(valueObject, recordCounter));
						importResult.incrementCountMappedValueObject();
					}
				} catch (FieldMappingException e) {
					if (parserConfiguration.continueOnRecordError()) {
						importResult.addErrorRecord(aRecord);
					} else {
						throw e;
					}
				}
			}
			return importResult;
		} catch (IllegalRecordLengthException e) {
			throw e;
		} catch (JsmatepInconsistencyException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtil.closeStream(in);
		}
	}

	/**
	 * A record will be skipped within data processing if a record starts with an  '#' or a 
	 * record is empty.
	 * @param parserConfiguration 
	 * @param record
	 * @return
	 */
	public static boolean skipContent(ParserConfiguration parserConfiguration, String record) {
		boolean doSkip = (record.trim().length() == 0);
		if (!parserConfiguration.skipRecordIdentifiersExist()) {
			return doSkip;
		}
		final List<String> skipIdents = parserConfiguration.getSkipRecordIdentifiers();
		for (int i = 0; !doSkip && i < skipIdents.size(); i++) {
			doSkip |= record.startsWith(skipIdents.get(i));
		}
		return doSkip;
	}

	/**
	 * Notifies all registered listeners.
	 * 
	 * @param recordMappedEvent
	 */
	protected void notifyListeners(RecordMappedEvent<T> recordMappedEvent) {
		for (RecordMapperListener<T> aListener : listeners) {
			aListener.afterRecordMapped(recordMappedEvent);
		}
	}

	/**
	 * Registers a new listener for data processing notification.
	 * 
	 * @param listener
	 */
	public void registerListener(RecordMapperListener<T> listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a listener from data processing notification.
	 * 
	 * @param listener
	 */
	public void removeListener(RecordMapperListener<T> listener) {
		listeners.remove(listener);
	}

	public String getConfigFileName() {
		return configFileName;
	}

}
