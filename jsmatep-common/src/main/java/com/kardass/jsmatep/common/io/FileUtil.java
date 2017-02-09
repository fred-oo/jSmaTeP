package com.kardass.jsmatep.common.io;

import java.io.File;


/**
 * Helper class for dealing with file related operations.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class FileUtil {

	/**
	 * This function will recursively delete directories and files.
	 * 
	 * @param path File or Directory to be deleted
	 * @return true indicates success.
	 */
	public static boolean deleteDirRecursivly(String path) {
		return deleteDirRecursivly(new File(path));
	}

	/**
	 * This function will recursively delete directories and files.
	 * 
	 * @param path File or Directory to be deleted
	 * @return true indicates success.
	 */
	public static boolean deleteDirRecursivly(File path) {
		boolean isCompletelyDeleted = true;
		if (path.exists()) {
			if (path.isDirectory()) {
				File[] files = path.listFiles();
				for (File aFile : files) {
					if (aFile.isDirectory()) {
						isCompletelyDeleted &= deleteDirRecursivly(aFile);
					} else {
						isCompletelyDeleted &= aFile.delete();
					}
				}
			}
		}
		return (isCompletelyDeleted && path.delete());
	}

}
