package com.kardass.jsmatep.common.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Helper for writing text content into a file.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class ContentWriter {

	/**
	 * Writes the content into the file.
	 * 
	 * @param aFile
	 * @param aContents
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void saveContents(File aFile, String aContents) throws FileNotFoundException, IOException {
		if (aFile == null) {
			throw new IllegalArgumentException("File should not be null.");
		}
		if (!aFile.exists()) {
			throw new FileNotFoundException("File does not exist: " + aFile);
		}
		if (!aFile.isFile()) {
			throw new IllegalArgumentException("Should not be a directory: " + aFile);
		}
		if (!aFile.canWrite()) {
			throw new IllegalArgumentException("File cannot be written: " + aFile);
		}
		Writer output = new BufferedWriter(new FileWriter(aFile));
		try {
			output.write(aContents);
		} finally {
			output.close();
		}
	}

}
