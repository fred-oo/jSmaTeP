package com.kardass.jsmatep.common.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper for reading file content.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class ContentReader {

	/**
	 * Returns the list as a string representation divided by the system's line separator.
	 * 
	 * @param contentList
	 * @return
	 */
	private static String asLineSeparatedString(List<String> contentList) {
		final StringBuilder contents = new StringBuilder();
		final String lineSeparator = System.getProperty("line.separator");
		for (String aLine : contentList) {
			contents.append(aLine).append(lineSeparator);
		}
		return contents.toString();
	}
	
	/**
	 * Reads the file's content and returns it as one string.
	 * 
	 * @param aFile
	 * @return
	 * @throws IOException
	 */
	public static String readContentsAsString(File aFile) throws IOException {
		return asLineSeparatedString(readContents(aFile));
	}
	
	/**
	 * Reads the file's content and returns it as one string.
	 * 
	 * @param aFile
	 * @return
	 * @throws IOException
	 */
	public static String readContentsAsString(String aFile) throws IOException {
		return asLineSeparatedString(readContents(aFile));
	}
	
	/**
	 * Reads the file's content and returns it as a list of strings.
	 * 
	 * @param aFile
	 * @return
	 * @throws IOException
	 */
	public static List<String> readContents(File aFile) throws IOException {
		final BufferedReader input = new BufferedReader(new FileReader(aFile));
		try {
			return readLines(input);
		} finally {
			input.close();
		}
	}

	/**
	 * Reads the file's content and returns it as a list of strings.
	 * 
	 * @param aFileName
	 * @return
	 * @throws IOException
	 */
	public static List<String> readContents(String aFileName) throws IOException {
		final InputStream in = IOUtil.getAsInputStream(aFileName);
		final BufferedReader input = new BufferedReader(new InputStreamReader(in));
		try {
			return readLines(input);
		} finally {
			input.close();
		}
	}

	/**
	 * Reads all lines (records) of the input reader.
	 * @param input
	 * @return
	 * @throws IOException
	 */
	private static List<String> readLines(final BufferedReader input) throws IOException {
		final List<String> result = new ArrayList<String>();
		String line = null;
		while ((line = input.readLine()) != null) {
			result.add(line);
		}
		return result;
	}

}
