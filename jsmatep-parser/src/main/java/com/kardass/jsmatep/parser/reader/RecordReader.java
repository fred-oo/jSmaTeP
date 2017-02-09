package com.kardass.jsmatep.parser.reader;

import com.kardass.jsmatep.parser.config.ParserConfiguration;
import com.kardass.jsmatep.parser.config.field.Field;

/**
 * Class for reading string data depending on a given {@link ParserConfiguration}.
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public interface RecordReader {

	/**
	 * Reads a string in theRecord depending on the given field's configuration.
	 * @param fieldConfig
	 * @param theRecord
	 * @return
	 * 		The read string data.
	 */
	String readField(Field<?> fieldConfig, String theRecord);

}