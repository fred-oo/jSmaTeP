package com.kardass.jsmatep.common.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Helper class for dealing with 'java.io.*' related operations.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class IOUtil {

	/**
	 * Adds a leading '/' to a file name if it doesn't already exist.
	 * 
	 * @param fileName
	 * @return
	 */
	private static String adjust(String fileName) {
		return (fileName.startsWith("/")) ? fileName : ("/" + fileName);
	}

	/**
	 * Return an input stream for a file name.
	 * 
	 * @param aFileName
	 * @return
	 */
	public static InputStream getAsInputStream(String aFileName) {
		InputStream is = IOUtil.class.getResourceAsStream(adjust(aFileName));
		return is == null ? getAsFileInputStream(aFileName) : is;
	}
	
	public static InputStream getAsFileInputStream(String aFileName) {
		final File aFile = new File(aFileName);
		try {
			return new FileInputStream(aFile);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Closes the given stream.
	 * 
	 * @param in
	 */
	public static void closeStream(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
}
